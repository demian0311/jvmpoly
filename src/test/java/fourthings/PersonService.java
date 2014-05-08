package fourthings;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.google.common.collect.ImmutableMap;
import fourthings.Person;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.fail;

public class PersonService {

    private MetricRegistry metricRegistry;
    //private Integer numberOfRequests = 0;

    //private Gauge<Integer> numberOfRequestsGauge = () -> {return numberOfRequests;};
    private Counter requestCounter;
    //private final Counter numRequests = metricRegistry


    public PersonService(MetricRegistry metricRegistryIn){
        metricRegistry = metricRegistryIn;
        //metricRegistry.register("PersonService.numberOfRequests", numberOfRequestsGauge);
        requestCounter = metricRegistry.counter("PersonService.requestCounter");
    }

    Map<Integer, Optional<Person>> persons = ImmutableMap.of(
            0, Optional.of(new Person("Demian", "demian0311@gmail.com")),
            1, Optional.empty(),
            2, Optional.of(new Person("Foo", "foo@bar.com")),
            3, Optional.of(new Person("Bar", "bar@bar.com")));

    Random r = new Random();
    public Optional<Person> getPerson(int id){
        requestCounter.inc();

        try {
            Thread.sleep(r.nextInt(150) + 50);
        } catch(InterruptedException ie) {
            fail("ie: " + ie.getMessage());
        }
        return persons.get(id);
    }
}
