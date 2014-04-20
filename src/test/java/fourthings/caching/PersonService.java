package fourthings.caching;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.fail;

class PersonService{
    Map<Integer, Optional<Person>> persons = ImmutableMap.of(
            0, Optional.of(new Person("Demian", "demian0311@gmail.com")),
            1, Optional.empty(),
            2, Optional.of(new Person("Foo", "foo@bar.com")),
            3, Optional.of(new Person("Bar", "bar@bar.com")));

    public Optional<Person> getPerson(int id){
        try {
            Thread.sleep(200);
        } catch(InterruptedException ie) {
            fail("ie: " + ie.getMessage());
        }
        return persons.get(id);
    }
}
