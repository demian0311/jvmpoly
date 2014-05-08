package fourthings.caching;

import com.codahale.metrics.MetricRegistry;
import fourthings.Person;
import fourthings.PersonService;
import net.spy.memcached.MemcachedClient;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.Optional;


/**
 * Yeah, this is an integration test.  You need memcache running:
 *
 *  > brew install memcached
 *  > memcached
 */
public class SpyMemcached {
    private MemcachedClient memcachedClient;
    private InetSocketAddress inetSocketAddress;

    private MemcachedClient getClient(){
        if(memcachedClient == null) {
            try{
                memcachedClient = new MemcachedClient(getSocketAddress());
            } catch (IOException ioe) {
                fail(ioe.getMessage());
            }
        }

        return memcachedClient;
    }

    private InetSocketAddress getSocketAddress(){
        if(inetSocketAddress == null) {
            inetSocketAddress = new InetSocketAddress("localhost", 11211);
        }
        return inetSocketAddress;
    }

    // TODO: do it with futures, can we fire off the future hit the
    // back end service and see who wins?  seems inefficient still
    // pro - best experience for customer
    // con - you aren't protecting the back-end

    @Test public void simpleExample() throws Exception{
        MemcachedClient c = getClient();
        Person person = new Person("Demian", "demian0311@gmail.com");

        int expirationSeconds = 60; // this is very short
        c.set(Person.genKey(99), expirationSeconds, person);

        Person myObject=(Person)c.get(Person.genKey(99));
        System.out.println("myObject: " + myObject);
        assertEquals(person, myObject);
    }

    PersonService personService = new PersonService(new MetricRegistry());

    /**
     * This is how the client code would behave.
     */
    private Optional<Person> getPerson(int id) {
        MemcachedClient cache = getClient();

        Object objectFromCache = cache.get(Person.genKey(id));
        if(objectFromCache != null){
            Optional<Person> personFromCache = Optional.of((Person)objectFromCache);
            return personFromCache;
        }

        Optional<Person> personFromService = personService.getPerson(id);
        if(personFromService.isPresent()) {
            cache.set(Person.genKey(id), 3600, personFromService.get());
        }

        return personFromService;
    }

    /**
     * This exercises population of the cache.
     */
    @Test public void testGetPerson(){
        assertEquals("Demian",         getPerson(0).get().getName());
        assertEquals(Optional.empty(), getPerson(1)); // with this we'll keep going to cache
        assertEquals("foo@bar.com",    getPerson(2).get().getEmail());
    }

    public int getIntStat(String statName){
        return Integer.getInteger(getClient().getStats().get(getSocketAddress()).get(statName));
    }

    public int getHits() { return getIntStat("get_hits"); }
    public int getMisses() { return getIntStat("get_misses");}
    public int getBytes() { return getIntStat("bytes");}

    @Test public void getStats(){
        MemcachedClient c = getClient();
        Map localhostStats = c.getStats().get(getSocketAddress());

        System.out.println("hits     : " + localhostStats.get("get_hits"));
        System.out.println("misses   : " + localhostStats.get("get_misses"));
        System.out.println("evictions: " + localhostStats.get("evictions"));

        assertTrue(localhostStats.get("get_hits") != null);
        assertTrue(!localhostStats.get("get_hits").equals("0"));
        assertTrue(!localhostStats.get("get_misses").equals("0"));
        assertTrue(localhostStats.get("evictions").equals("0"));
    }

    @Test public void versions(){
        MemcachedClient c = getClient();

        Map<SocketAddress, String> versions = c.getVersions();
        System.out.println("versions: " + versions);

    }
}
