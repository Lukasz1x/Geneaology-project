import java.util.Locale;

public class NegativeLifespanException extends Exception
{
    public NegativeLifespanException(Person person)
    {
        super(String.format(Locale.ENGLISH, "%s urodzil sie (%s) wczesniej niz umarl (%s)", person.getName(), person.getBirthDate(), person.getDeathDate()));
    }
}
