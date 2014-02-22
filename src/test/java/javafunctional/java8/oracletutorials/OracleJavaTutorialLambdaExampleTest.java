package javafunctional.java8.oracletutorials;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

class Person {
    public enum Sex { MALE, FEMALE}

    private final String name;
    private final LocalDate birthday;
    private final Sex gender;
    private final String emailAddress;

    public Person(
            String nameIn,
            LocalDate birthdayIn,
            Sex genderIn,
            String emailAddressIn){
       name = nameIn;
       birthday = birthdayIn;
       gender = genderIn;
       emailAddress = emailAddressIn;
    }

    public int getAge() {
        LocalDate age = LocalDate.now()
                .minusYears(birthday.getYear())
                .minusMonths(birthday.getDayOfMonth())
                .minusDays(birthday.getDayOfMonth());
        return age.getYear();
    }

    public String toString(){
        return String.format("Person(%s, %s, %s, %s, %s)", name, birthday, gender, emailAddress, getAge());
    }
}

public class OracleJavaTutorialLambdaExampleTest {

    List<Person> roster = Arrays.asList(
            new Person("Alice",  LocalDate.parse("1976-11-12"), Person.Sex.FEMALE,  "alice@example.com"),
            new Person("Bob",    LocalDate.parse("1970-03-22"), Person.Sex.MALE,    "bob@example.com"),
            new Person("Chad",   LocalDate.parse("1958-09-14"), Person.Sex.MALE,    "chad@example.com"),
            new Person("Demian", LocalDate.parse("1971-12-04"), Person.Sex.MALE,    "demian0311@gmail.com")
    );

    @Test public void printPersonsOlderThan() {
        final int low = 40;
        System.out.println("=== Persons older than " + low);

        for (Person currPerson: roster) {
            if(currPerson.getAge() > low){
                System.out.println(currPerson);
            }
        }
    }

    @Test public void printPersonsWithinRange(){
        final int low = 40;
        final int high = 45;

        System.out.println("=== Persons between " + low + " and " + high);

        for (Person currPerson: roster){
            if((low < currPerson.getAge()) && (currPerson.getAge() < high)){
                System.out.println(currPerson);
            }
        }
    }

    /**
     * This is what you should be doing in Java8.
     */
    @Test public void useStreams(){
        final int low = 40;
        final int high = 45;
        System.out.println("=== FP Persons between " + low + " and " + high);

        List<Person> persons = roster.stream()
                .filter(p -> low < p.getAge() && p.getAge() < high)
                .collect(toList());

        persons.forEach(p -> System.out.println(p.toString()));
    }
}
