public class ParentingAgeException extends Exception
{
    public final Person person;
    public ParentingAgeException(Person child, Person parent)
    {
        super(String.format("Czy %s jest rodzicem dla %s", parent.getName(), child.getName()));
        this.person=child;
    }
}
