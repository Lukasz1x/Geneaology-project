import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public List<Person> getParents() {
        return parents;
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

    public static List<Person> fromCsv(String path)
    {
        BufferedReader reader = null;
        List<Person> people = new ArrayList<>();
        String line;
        try {
            reader = new BufferedReader(new FileReader(path));
            reader.readLine();

            while ((line=reader.readLine())!=null)
            {
                Person person=fromCsvLine(line);
                try
                {
                    person.validateLifespan();
                    people.add(person);

                } catch (NegativeLifespanException e) {
                    System.err.println(e.getMessage());
                }


            }


            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return people;
    }

    private void validateLifespan() throws NegativeLifespanException
    {
        if(deathDate != null && deathDate.isBefore(birthDate))
        {
            throw new NegativeLifespanException(this);
        }
    }
}
