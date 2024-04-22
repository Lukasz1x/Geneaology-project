import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Person
{
    private String name;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private List<Person> parents=new ArrayList<>();


    public Person(String name, LocalDate birthDate, LocalDate deathDate)
    {
        this.name=name;
        this.birthDate=birthDate;
        this.deathDate=deathDate;
    }
    public static Person fromCsvLine(String line)
    {
        String[] parts = line.split(",",-1);
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthDate=LocalDate.parse(parts[1], dateTimeFormatter);
        LocalDate deathDate = (parts[2].equals("") ? null : LocalDate.parse(parts[2], dateTimeFormatter));
        return new Person(parts[0], birthDate, deathDate);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", deathDate=" + deathDate +
                ", parents=" + parents +
                '}';
    }
}
