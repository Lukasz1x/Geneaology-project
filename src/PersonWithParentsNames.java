import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PersonWithParentsNames
{
    final public Person person;
    final public List<String> parentsNames;

    public PersonWithParentsNames(Person person, List<String>parentsNames)
    {
        this.person=person;
        this.parentsNames=parentsNames;
    }

    public static PersonWithParentsNames fromCsvLine(String line)
    {
        String[] parts = line.split(",",-1);
        Person child = Person.fromCsvLine(line);
        List<String> parentsNames=new ArrayList<>();
        if(!parts[3].equals("")) {parentsNames.add(parts[3]);}
        if(!parts[4].equals("")) {parentsNames.add(parts[4]);}
        return new PersonWithParentsNames(child, parentsNames);
    }

    public static void linkRelatives(Map<String, PersonWithParentsNames> map)
    {
        for(var pwpn : map.values())
        {
            for(var parentName : pwpn.parentsNames)
            {
                pwpn.person.addParent(map.get(parentName).person);
            }
        }
    }
}
