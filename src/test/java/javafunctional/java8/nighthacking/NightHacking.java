package javafunctional.java8.nighthacking;

import org.junit.Test;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class NightHacking {
    @Test public void test(){
        Function<Object, String> f = (Object o) -> {
            return o.toString();
        };

        assertEquals("123", f.apply(new Integer("123")));
    }

    @Test public void functionalInterfaces(){
        //Predicate<String> isEmpty = s -> s.isEmpty();
        Predicate<String> isEmpty = String::isEmpty;
        Runnable r = () -> {System.out.println("Boo!");};
    }

    @Test public void defaultMethods(){
        IntStream.range(0, 100).forEach(System.out::println);
    }

    /*
    private List<Shape> shapes = Arrays.asList(
            new Shape(Color.RED),
            new Shape(Color.BLUE),
            new Shape(Color.GREEN));

    @Test public void test1(){
        //shapes.forEach(s -> System.out.println("s: " + s));
        shapes.forEach(s ->
            if(s.getColor() == Color.RED){
                s.setColor(Color.BLUE)
            }
        );

        Set<Box> blueBlocks = shapes.stream()
            .filter(s -> s.getColor() == Color.BLUE)
            .map(Shape::getContainingBox) //
            .collect(ReductionsTest.toSet());

        int sumOfWeights = shapes.stream()
            .filter(s -> s.getColor() == Color.BLUE)
            .map(Shape::getWeight) //
            .sum();

        // need an example like this
        txns.foo.stream()
            .filter(s -> s.getBuyer().getAge() >= 65)
            .map(Txn::getSeller)
            .distinct()
            .sort(comparing(Seller::getName))
            .forEach(s -> System.out::println);

    }
    */
}

/*
enum Color {RED, BLUE, GREEN}

class Shape{
    private Color color;

    public Color getColor(){ return color;}
    public void setColor(Color colorIn){ color = colorIn;}

    public Shape(Color colorIn){
        color = colorIn;
    }
}
*/
