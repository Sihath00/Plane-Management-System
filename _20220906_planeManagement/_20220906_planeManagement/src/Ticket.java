import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private final char row;       // Row of the seat
    private final int seat;       // Seat number
    private final double price;   // Price of the ticket
    private final Person person;  // Person associated with the ticket

    // Constructor to initialize the Ticket with provided row, seat, price, and person
    public Ticket(char row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    // Method to save ticket information to a file
    public void save() {
        String filename = row + String.valueOf(seat) + ".txt";  // File name based on row and seat
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Ticket Information:\n");
            writer.write("\n");
            writer.write("Row: " + row + "\n");
            writer.write("Seat: " + seat + "\n");
            writer.write("Price: £" + price + "\n");
            writer.write("\n");
            writer.write("Person Information:\n");
            writer.write("\n");
            writer.write("Name: " + person.getName() + "\n");
            writer.write("Surname: " + person.getSurname() + "\n");
            writer.write("Email: " + person.getEmail() + "\n");
            System.out.println("Ticket information saved to " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the ticket information.");
            e.printStackTrace();
        }
    }

    // Method to print ticket information
    public void printTicketInfo() {
        System.out.println();
        System.out.println("Row: " + row);
        System.out.println("Seat: " + seat);
        System.out.println("Price: £" + price);
        System.out.println("Passenger Name: " + person.getName() + " " + person.getSurname());
        System.out.println("Passenger Email: " + person.getEmail());
    }

    // Getter method to retrieve the price of the ticket
    public double getPrice() {
        return price;
    }
}
