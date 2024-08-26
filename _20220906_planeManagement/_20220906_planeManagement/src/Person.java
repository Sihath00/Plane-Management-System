// Class representing a Person with name, surname, and email
public class Person {
    // Private fields for name, surname, and email
    private final String name;
    private final String surname;
    private final String email;

    // Constructor to initialize the Person with provided name, surname, and email
    public Person(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    // Getter method to retrieve the name of the person
    public String getName() {
        return name;
    }

    // Getter method to retrieve the surname of the person
    public String getSurname() {
        return surname;
    }

    // Getter method to retrieve the email of the person
    public String getEmail() {
        return email;
    }
}
