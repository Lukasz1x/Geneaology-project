import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Person implements Serializable
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

    public void addParent(Person parent)
    {
        parents.add(parent);
    }

    public static List<Person> fromCsv(String path)
    {
        BufferedReader reader = null;
        List<Person> people = new ArrayList<>();
        Map<String, PersonWithParentsNames> map = new HashMap<>();
        String line;
        try {
            reader = new BufferedReader(new FileReader(path));
            reader.readLine();

            while ((line=reader.readLine())!=null)
            {
                //Person person=fromCsvLine(line);
                PersonWithParentsNames pwpn= PersonWithParentsNames.fromCsvLine(line);
                Person person= pwpn.person;
                try
                {
                    person.validateLifespan();
                    person.validateAmbiguity(people);

                    people.add(person);
                    map.put(person.getName(), pwpn);

                } catch (NegativeLifespanException e) {
                    System.err.println(e.getMessage());
                } catch (AmbiguousPersonException e) {
                    System.err.println(e.getMessage());
                }


            }


            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PersonWithParentsNames.linkRelatives(map);
        try
        {
            for(Person p : people)
            {
                p.validateParenting();
            }
        } catch (ParentingAgeException e) {
            System.err.println(e.getMessage());
            Scanner scanner = new Scanner(System.in);
            System.out.println("Dodac osobe? Potwierdz za pomoca [Y/N]");
            String odp = scanner.nextLine();
            if(!odp.toLowerCase(Locale.ENGLISH).equals("y"))
            {
                people.remove(e.person);
            }
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

    private void validateAmbiguity(List<Person> people) throws AmbiguousPersonException
    {
        for(Person p : people)
        {
            if(this.getName().equals(p.getName()))
            {
                throw new AmbiguousPersonException(this);
            }
        }
    }

    private void validateParenting() throws ParentingAgeException
    {
        for (Person parent : this.parents)
        {
            if(parent.birthDate.plusYears(15).isAfter(this.birthDate) || (parent.deathDate != null && parent.deathDate.isBefore(this.birthDate)))
            {
                throw new ParentingAgeException(this, parent);
            }
        }
    }




    public static void toBinaryFile(List<Person> people, String filename)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(people);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<Person> fromBinaryFile(String filename)
    {
        try
        {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (List<Person>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
