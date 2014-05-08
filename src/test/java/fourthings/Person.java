package fourthings;

import java.io.Serializable;

public class Person implements Serializable {
    private final String name;
    private final String email;

    public Person(String nameIn, String emailIn){
        name = nameIn;
        email = emailIn;
    }

    public String getName(){ return name; }
    public String getEmail(){ return email;}

    public static String genKey(int id){
        return "Person(" + id + ")";
    }

    @Override
    public String toString() {
        return "Person{name='" + name + '\'' + ", email='" + email + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!email.equals(person.email)) return false;
        if (!name.equals(person.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
