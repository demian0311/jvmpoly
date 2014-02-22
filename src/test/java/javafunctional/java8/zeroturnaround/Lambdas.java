package javafunctional.java8.zeroturnaround;

import org.junit.Test;

import java.util.Comparator;

/**
 * Created by demian on 1/4/14.
 */
public class Lambdas {

    @Test public void runnable() {
        Runnable r = () -> System.out.print("hello lambda");

        System.out.println("don't run it yet");

        r.run();
    }

    @Test public void comparator() {
        //Comparator cmp = (Number x, Number y) -> (x.intValue() < y.intValue()) ? -1 : ((x.intValue() > y.intValue()) ? 1 : 0); // compiler doesn't like what ZT shows
    }

    public void execute(Action action){
        action.run("Hello from execute function");
        //          ^-- this is the param sent into the
    }

    @Test public void testExecute() {

        execute(new Action() {
            @Override public void run(String param) {
                System.out.println(">> Newing up an action: " + param);
            }
        });
    }

    @Test public void testExecuteLambda() {
        execute((String param) -> System.out.println(">> Wordy lambda: " + param));
        execute(param -> System.out.println(">> Terse lambda: " + param));
        execute(param -> System.out.println(">> Method Reference Lambda: " + param));
    }


}

interface Action {
    void run(String param);
}


