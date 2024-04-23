import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Person> people = Person.fromCsv("family.csv");

        for(Person p : people)
        {
            System.out.println(p.toString());
        }
        System.out.printf("\n\n\n");
        Person.toBinaryFile(people, "family.bin");
        List<Person> loadPeople = Person.fromBinaryFile("family.bin");
        for(Person person: loadPeople)
        {
            System.out.printf(person.toString()+"\n");
        }

    }
}