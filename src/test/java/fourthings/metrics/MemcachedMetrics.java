package fourthings.metrics;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import fourthings.caching.SpyMemcached;

import java.net.InetSocketAddress;

public class MemcachedMetrics {
    private InetSocketAddress inetSocketAddress;
    SpyMemcached spyMemcached = new SpyMemcached();
    private Gauge<Integer> memcachedBytesGauge = () -> {return spyMemcached.getBytes();};
    MetricRegistry metricRegistry = null;

    public MemcachedMetrics(MetricRegistry metricRegistryIn){
        metricRegistry = metricRegistryIn;
        metricRegistry.register("Memcached.bytes", memcachedBytesGauge);
    }
}
