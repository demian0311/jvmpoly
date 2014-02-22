package javafunctional.java8.fpij;

import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Chapter3 {

    private static void printAsChar(int intValue) {
        System.out.println((char) (intValue));
    }

    @Test public void iterateAString() {
        final String str = "w00t";

        // these print integers
        str.chars().forEach(ch -> System.out.println(ch));
        str.chars().forEach(System.out::println);

        // using a method reference
        str.chars().forEach(Chapter3::printAsChar);

        str.chars().mapToObj(ch -> Character.valueOf((char) ch)).forEach(System.out::println);
        str.chars().filter(ch -> Character.isDigit(ch)).forEach(Chapter3::printAsChar);

        str.chars().filter(Character::isDigit).forEach(Chapter3::printAsChar);
    }

    // Comparators

    private class Person{
        private final String name;
        private final int age;

        public Person(String nameIn, int ageIn){
            name = nameIn;
            age = ageIn;
        }

        public String getName(){ return name; }
        public int getAge(){ return age;}
        public int ageDifference(Person other) { return age - other.getAge(); }
        public String toString(){ return String.format("%s - %d", name, age); }
    }

    final List<Person> people = Arrays.asList(
            new Person("John", 20),
            new Person("Sara", 21),
            new Person("Jane", 21),
            new Person("Greg", 35));

    @Test public void comparison(){

        List<Person> actual = people
                .stream()
                .sorted((p1, p2) -> p1.ageDifference(p2))
                .collect(toList());

        Person firstPerson = actual.stream().findFirst().get();
        assertEquals("John", firstPerson.getName());
    }

    @Test public void betterComparison() {
        System.out.println("=== better comparison");
        List<Person> sortedPeople = people.stream().sorted(Person::ageDifference).collect(toList());
        System.out.println("sortedPeople: " + sortedPeople);
    }

    @Test public void comparators(){
        Comparator<Person> compareAscending = (p1, p2) -> p1.ageDifference(p2);
        Comparator<Person> compareDescending = compareAscending.reversed();

        System.out.println("ascending: " + people.stream().sorted(compareAscending).collect(toList()));
        System.out.println("descending: " + people.stream().sorted(compareDescending).collect(toList()));
    }

    @Test public void sortByName() {
        System.out.println("sorted by name: " +
                people.stream()
                        .sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
                        .collect(toList()));

        // youngest
        people.stream().min(Person::ageDifference).ifPresent(System.out::println);

        // oldest
        people.stream().max(Person::ageDifference).ifPresent(System.out::println);
    }

    @Test public void testComparing() {
        System.out.println("testComparing");
        final Function<Person, String> byName = p -> p.getName();
        List<Person> sortedByName = people.stream()
                .sorted(comparing(byName))
                .collect(toList());
        System.out.println("sortedByName: " + sortedByName);

        final Function<Person, Integer> byAge = p -> p.getAge();
        List<Person> sortedByNameAndAge = people.stream()
                .sorted(comparing(byName).thenComparing(byAge))
                .collect(toList());
        System.out.println("sortedByNameAndAge: " + sortedByNameAndAge);
    }

    @Test public void usingCollectAndCollectors() {
        List<Person> olderThan20 = people.stream()
                .filter(p -> p.getAge() > 20)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        assertTrue(1 == olderThan20.stream().filter(p -> p.getName().equals("Sara")).count());
        assertTrue(olderThan20.stream().anyMatch(p -> p.getName().equals("Jane")));
        assertTrue(olderThan20.stream().anyMatch(p -> p.getName().equals("Greg")));
        assertTrue(olderThan20.stream().allMatch(p -> p.getAge() > 20));
        assertTrue(3 == olderThan20.stream().count());
    }

    @Test public void groupBy() {
        Map<Integer, List<Person>> peopleByAge = people.stream().collect(Collectors.groupingBy(Person::getAge));
        System.out.println("peopleByAge: " + peopleByAge);
        assertEquals("{35=[Greg - 35], 20=[John - 20], 21=[Sara - 21, Jane - 21]}", peopleByAge.toString());
    }


    @Test public void listFilesInDirector() throws Exception {
        Files.list(Paths.get("."))
                .filter(f -> Files.isDirectory(f))
                .forEach(System.out::println);

        Files.newDirectoryStream(
                Paths.get("./src/test/java/javafunctional/java8"),
                path -> path.toString().endsWith(".java"))
                .forEach(System.out::println);

        final File[] files = new File(".").listFiles(f -> f.isHidden());
        new File(".").listFiles(File::isHidden);


    }
}
