package javafunctional.java8.vjug.parallelstreams;

import org.junit.Ignore;
import org.junit.Test;
import org.openjdk.jmh.annotations.GenerateMicroBenchmark;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.*;


/**
 */
@State(Scope.Thread)
public class StreamTest {
    // stream is not a sequence of objects
    // stream is an iterator on steroids
    // stream is an abstraction that represents 0 or more values

    List<String> strings = Arrays.asList("alpha", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel", "india", "juliet");

    @Test
    public void test(){

        //strings.forEach(s -> System.out.println(s));

        strings.replaceAll(s -> s.toUpperCase());
        System.out.println(strings);

        // we can't move things from the list
        // get it into an ArrayList so we can change what is in it
        List<String> list2 = new ArrayList<>(strings);
        list2.removeIf(s -> s.length() < 6);
        System.out.println(list2);
    }

    @Test public void test2() {
        System.out.println(
                strings.stream()                           //
                        .filter(s -> s.length() >= 6)      // intermediate
                        .peek(s -> System.out.printf("**%s**\n", s))
                        .map(s -> s.toUpperCase())         // intermediate like a replaceAll
                        .sorted(Comparator.reverseOrder()) // intermediate
                        .collect(toList()));               // terminal
    }

    @Ignore("takes too long") @Test public void test3() {
        // look at what is allowed on the Stream interface!
        //strings.parallelStream() // 1s
        strings.stream() // 5s
                .peek(s -> sleepUninterrupted(500))
                .collect(toList());
    }

    @Test public void test4() {
        List<Integer> list1 = Collections.synchronizedList(new ArrayList<>());

        List<Integer> list2 = IntStream.range(0, 20)
                .parallel()
                .boxed()
                //.peek(i -> list1.add(i))
                .peek(list1::add)
                .collect(toList());

        // elements are processed out of order, "processing order"
        System.out.println("created from the peek      : " + list1);

        // collect re-assembles them in the same order, "encounter order"
        System.out.println("after all of the processing: " + list2);
    }

    private static final long LIMIT = 100_000_000L;

    @GenerateMicroBenchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Test public void test5(){
        long sum = 0L;

        for(long i = 1; i <= LIMIT; i++){
            sum += 1;
        }

        System.out.println(sum);

        // you can't mutate a local variable
        // the field has to be _effectively_ final
        /*
        long sum1 = 0L;
        LongStream.rangeClosed(1, LIMIT)
                .forEach(i -> sum1 += i);
                */

        // single element array trick
        long[] sum2 = {0L}; // very bad idea

        LongStream.rangeClosed(1, LIMIT)
                .parallel() // you're fine as long as you're not parallel
                .forEach(i -> sum2[0] += 1);
            // we are incrementing a variable in parallel on the heap
            // this is not thread safe
            // += is a read, summation, write
        System.out.println("Single element array: " + sum2[0]);


        // Less Bad Idea
        AtomicLong sum3 = new AtomicLong(0L);

        LongStream.rangeClosed(1, LIMIT)
                .parallel()
                .forEach(i -> sum3.addAndGet(i));
        System.out.println("AtomicLong: " + sum3);

        // this is the best way to do it
        System.out.println("With reduce: " + LongStream.rangeClosed(1, LIMIT)
                .parallel()
                .reduce(0, (i, j) -> i + j)); // this is like a foldLeft

        System.out.println("with sum(): " + LongStream.rangeClosed(1, LIMIT).parallel().sum());
    }



    private void sleepUninterrupted(Integer howLongToSleep){
        try{
            Thread.sleep(howLongToSleep);
        } catch(Throwable t){
            System.out.println(t.getMessage());
        }
    }
}
