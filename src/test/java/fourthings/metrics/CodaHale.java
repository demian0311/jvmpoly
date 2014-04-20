package fourthings.metrics;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import org.junit.Test;

public class CodaHale {

    final MetricRegistry metricsRegistry = new MetricRegistry();

    @Test
    public void test(){
        Gauge<Integer> gauge = new Gauge<Integer>() {
            @Override
            public Integer getValue() {
                return null;
            }
        };

        MetricRegistry.name(this.getClass(), "foo", "bar");


        System.out.println("hello world");
    }
}
