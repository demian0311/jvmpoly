package javafunctional.java8.fpij;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class Chapter2 {

    final List<String> friends = Arrays.asList(
            "Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

    @Test public void iteratingAList(){

        System.out.println("== The Old Way ==");
        for(int i = 0; i < friends.size(); i++){
            System.out.println(friends.get(i));
        }

        System.out.println("== Better Iteration ==");
        for(String currName: friends){
            System.out.println(currName);
        }

        System.out.println("== Lambda ==");
        friends.forEach((final String name) -> System.out.println(name));

        System.out.println("== Lambda Without Types on Parameters ==");
        friends.forEach(name -> System.out.println(name));

        System.out.println("== Method Reference ==");
        friends.forEach(System.out::println);
    }

    @Test public void transformation(){
        final List<String> expected = Arrays.asList(
            "BRIAN", "NATE", "NEAL", "RAJU", "SARA", "SCOTT");

        // Old Way
        final List<String> upperCaseNames = new ArrayList<String>();
        for (String name: friends){
            upperCaseNames.add(name.toUpperCase());
        }
        assertEquals(expected, upperCaseNames);

        // Internal Iterator
        final List<String> upperCaseNames1 = new ArrayList<String>();
        friends.forEach(name -> upperCaseNames1.add(name.toUpperCase()));
        assertEquals(expected, upperCaseNames1);

        // ProvidedFunctionalInterfaces
        friends.stream()
                .map(name -> name.toUpperCase())
                .forEach(name -> System.out.println(name));

        friends.stream()
                .map(name -> name.length())
                .forEach(count -> System.out.println(count + " "));

        friends.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);
                //.forEach(name -> System.out.println(name));
    }

    @Test public void findElements() {
        System.out.println("hello world");

        // old way
        final List<String> startsWithN = new ArrayList<String>();
        for (String name : friends) {
            if(name.startsWith("N")){
                startsWithN.add(name);
            }
        }
        final List<String> expected = Arrays.asList("Nate", "Neal");
        assertEquals(expected, startsWithN);

        // lambdas,
        // collect is the magic to get a list back out
        assertEquals(expected, friends.stream()
                .filter(name -> name.startsWith("N"))
                .collect(Collectors.toList()));
    }

    final List<String> comrades = Arrays.asList("Kate", "Ken", "Nick", "Paula", "Zach");
    final List<String> editors = Arrays.asList("Brian", "Jackie", "John", "Mike");

    /**
     * This is the wrong way to do it.
     */
    @Test public void reusingLambdaExpressions1() {

        final long countFriendsStartN = friends.stream().filter(name -> name.startsWith("N")).count();
        final long countComradesStartN = comrades.stream().filter(name -> name.startsWith("N")).count();
        final long countEditorsStartN = editors.stream().filter(name -> name.startsWith("N")).count();

        assertEquals(2, countFriendsStartN);
        assertEquals(1, countComradesStartN);
        assertEquals(0, countEditorsStartN);
    }

    @Test public void reusingLambdaExpressions2() {
        final Predicate<String> startsWithN = name -> name.startsWith("N");

        final long countFriendsStartN = friends.stream().filter(startsWithN).count();
        final long countComradesStartN = comrades.stream().filter(startsWithN).count();
        final long countEditorsStartN = editors.stream().filter(startsWithN).count();

        assertEquals(2, countFriendsStartN);
        assertEquals(1, countComradesStartN);
        assertEquals(0, countEditorsStartN);
    }


    /**
     * This returns a Predicate (or a function)
     */
    public static Predicate<String> checkIfStartsWith(final String letter){
        return name -> name.startsWith(letter);
    }

    @Test public void removingDuplicationWithLexicalScoping(){
        assertEquals(2, friends.stream().filter(checkIfStartsWith("N")).count());
        assertEquals(1, comrades.stream().filter(checkIfStartsWith("N")).count());
        assertEquals(0, editors.stream().filter(checkIfStartsWith("N")).count());
    }

    @Test public void refactoringToNarrowTheScope() {
        final Function<String, Predicate<String>> startsWithLetter = letter -> name -> name.startsWith(letter);

        assertEquals(2, friends.stream().filter(startsWithLetter.apply("N")).count());
        assertEquals(1, comrades.stream().filter(startsWithLetter.apply("N")).count());
        assertEquals(0, editors.stream().filter(startsWithLetter.apply("N")).count());
    }

    /**
     * This is a clumsy way to pick out the first element
     * in a list that satisfies a criteria.
     */
    public static String pickName(
            final List<String> names,
            final String startsWithLetter){
        String foundName = null;
        for (String name : names) {
            if (name.startsWith(startsWithLetter)) {
                foundName = name;
                break;
            }
        }
        return foundName;
    }

    @Test public void pickNameTest() {
        final String nName = pickName(friends, "N");
        assertEquals("Nate", nName);
    }

    public static Optional<String> pickNameBetter(
            final List<String> names,
            final String startsWithLetter) {
        return names.stream().filter(name -> name.startsWith(startsWithLetter)).findFirst();
    }

    @Test public void pickNameBetterTest() {
        assertEquals(Optional.of("Nate"), pickNameBetter(friends, "N"));
        assertEquals(Optional.empty(), pickNameBetter(friends, "Z"));
    }

    @Test public void reducingCollectionToSingleValue() {
        assertEquals(26, friends.stream().mapToInt(name -> name.length()).sum());
    }

    @Test public void pickLongest(){
        final Optional<String> aLongName = friends
                .stream()
                .reduce((name1, name2) -> name1.length() > name2.length() ? name1 : name2);
                // reduce takes the resulting value and sends that onto the next pair
                // this is kind of like a foldRight

        // this is almost like pattern matching, we have the un-wrapping done for us
        aLongName.ifPresent(name -> assertEquals("Scott", name));
    }

    @Test public void testJoiningElements() {
        assertEquals(
                "Brian, Nate, Neal, Raju, Sara, Scott",
                String.join(", ", friends));

        // hmmm, not sure what I'm doing wrong here
        //assertEquals(
        //        "BRIAN: NATE: NEAL: RAJU: SARA: SCOTT",
        //        friends.stream().map(String::toUpperCase).collect(joining(", ")));
    }
}
