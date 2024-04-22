import java.util.Locale;

public class AmbiguousPersonException extends Exception
{
    public AmbiguousPersonException(Person person)
    {
        super(String.format(Locale.ENGLISH, "%s pojawia sie wielokrotnie", person.getName()));
    }
}
