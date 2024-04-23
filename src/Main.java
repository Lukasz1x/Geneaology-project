import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        List<Person> people = Person.fromCsv("family.csv");

//        for(Person p : people)
//        {
//            System.out.println(p.toString());
//        }
//        System.out.printf("\n\n\n");
//        Person.toBinaryFile(people, "family.bin");
//        List<Person> loadPeople = Person.fromBinaryFile("family.bin");
//        for(Person person: loadPeople)
//        {
//            System.out.printf(person.toString()+"\n");
//        }
//        List<Person> kowalscy = Person.filterByName(people, "Kowals");
//        String uml = Person.generateUML(kowalscy);
//        PlantUMLRunner.setPlantUmlPath("plantuml-1.2024.4.jar");
//        PlantUMLRunner.generateDiagram(uml, "./", "UmlDiagram");

//        List<Person> sortedBirthdate = Person.sortByBirthdate(people);
//        for(Person person: sortedBirthdate)
//        {
//            System.out.printf(person.toString()+"\n");
//        }

//        List<Person> sortedByLifespan = Person.sortByLifespan(people);
//        for(Person person: sortedByLifespan)
//        {
//            System.out.printf(person.getLifespan()+ " "+ person.toString()+"\n");
//        }

//        System.out.println(Person.oldestLivingPerson(people));

        Function<String, String> change_color = s -> s.contains("Kowals") ? s +" #Yellow" : s ;

//        String uml = Person.generateUML(people, change_color);
//        PlantUMLRunner.setPlantUmlPath("plantuml-1.2024.4.jar");
//        PlantUMLRunner.generateDiagram(uml, "./", "UmlDiagram");

        List<Person>personList = Person.sortByLifespan(people);
        Predicate<Person> condition = person -> personList.contains(person);
        String uml = Person.generateUML(people, change_color, condition);
        PlantUMLRunner.setPlantUmlPath("plantuml-1.2024.4.jar");
        PlantUMLRunner.generateDiagram(uml, "./", "UmlDiagram");
    }
}