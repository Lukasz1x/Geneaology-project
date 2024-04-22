//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Person a = Person.fromCsvLine("Marek Kowalski,15.05.1899,25.06.1957,,");
        System.out.println(a.toString());
    }
}