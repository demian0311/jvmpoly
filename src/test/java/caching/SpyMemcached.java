package caching;

import net.spy.memcached.MemcachedClient;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.Map;

class Person implements Serializable{
    private final String name;
    private final String email;

    Person(String nameIn, String emailIn){
        name = nameIn;
        email = emailIn;
    }

    String getName(){ return name; }
    String getEmail(){ return email;}

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!email.equals(person.email)) return false;
        if (!name.equals(person.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}

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

    @Test public void simpleExample() throws Exception{
        MemcachedClient c = getClient();
        Person person = new Person("Demian", "demian0311@gmail.com");

        int expirationSeconds = 3600;
        c.set(""+person.hashCode(), expirationSeconds, person);

        Person myObject=(Person)c.get(""+person.hashCode());
        System.out.println("myObject: " + myObject);
        assertEquals(person, myObject);
    }

    @Test public void getStats(){
        MemcachedClient c = getClient();
        Map localhostStats = c.getStats().get(getSocketAddress());
        System.out.println("hits     : " + localhostStats.get("get_hits"));
        System.out.println("misses   : " + localhostStats.get("get_misses"));
        System.out.println("evictions: " + localhostStats.get("evictions"));
    }
}
