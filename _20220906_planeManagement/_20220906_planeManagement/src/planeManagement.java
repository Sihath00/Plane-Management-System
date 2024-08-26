import java.util.Scanner;

// Class for managing plane seating and ticket operations
public class planeManagement {
    // 2D array representing the seating plan of the plane
    private static final char[][] seatingPlan = new char[4][14]; // Rows B, C have 12 seats, rows A, D have 14 seats
    // 2D array to keep track of sold tickets
    private static final Ticket[][] ticketsSold = new Ticket[4][14]; // Seats already sold

    // Main method
    public static void main(String[] args) {
        System.out.println("************************************************************************************");
        System.out.print("                 ");
        System.out.println("\"Welcome to the Plane Management application\"");
        initializeSeatingPlan();

        Scanner scanner = new Scanner(System.in);
        int option;

        // Main menu loop
        do {
            // Displaying menu options
            System.out.println("************************************************************************************");
            System.out.print("                                ");
            System.out.println("Menu Options");
            System.out.println("************************************************************************************");
            System.out.print("                    ");
            System.out.println("1. Buy a seat");
            System.out.print("                    ");
            System.out.println("2. Cancel a seat");
            System.out.print("                    ");
            System.out.println("3. Find first available seat");
            System.out.print("                    ");
            System.out.println("4. Show seating plan");
            System.out.print("                    ");
            System.out.println("5. Show tickets sold");
            System.out.print("                    ");
            System.out.println("6. Search for a ticket");
            System.out.print("                    ");
            System.out.println("0. Exit");

            System.out.println("************************************************************************************");
            System.out.println();
            System.out.print("Please enter your choice: ");
            // Handling invalid input
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
                System.out.print("Please enter your choice: ");
            }
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
                option = -1; // Set option to invalid
                continue;
            }
            System.out.println();

            // Switch case for menu options
            switch (option) {
                case 0:
                    System.out.println("Exiting program. Goodbye!");
                    break;
                case 1:
                    buySeat(scanner);
                    System.out.println();
                    break;
                case 2:
                    cancelSeat(scanner);
                    System.out.println();
                    break;
                case 3:
                    findFirstAvailable();
                    System.out.println();
                    break;
                case 4:
                    showSeatingPlan();
                    System.out.println();
                    break;
                case 5:
                    printTicketsInfo();
                    System.out.println();
                    break;
                case 6:
                    searchTicket(scanner);
                    System.out.println();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (option != 0);

        scanner.close();
    }

    // Method to initialize the seating plan
    private static void initializeSeatingPlan() {
        for (char[] chars : seatingPlan) {
            java.util.Arrays.fill(chars, 'O'); // Initialize all seats as available
        }
    }

    // Method to buy a seat
    private static void buySeat(Scanner scanner) {
        System.out.println("You selected Option 1 - Buy a seat.");
        System.out.println();
        System.out.println("************************************************************************************");
        System.out.print("                      ");
        System.out.println("Ticket Prices and Seats Information");
        System.out.println("************************************************************************************");
        System.out.println();
        System.out.print("                    ");
        System.out.println("1. Seat 1-5 £200 ");
        System.out.print("                    ");
        System.out.println("2. Seat 6-9 £150");
        System.out.print("                    ");
        System.out.println("3. Seat 10-14 £180");

        System.out.println("************************************************************************************");

        System.out.println();
        String rowInput;
        char rowLetter;
        // Input validation for row letter
        do {
            System.out.print("Enter the row letter (A-D): ");
            rowInput = scanner.nextLine().toUpperCase();
            if (rowInput.length() != 1 || !rowInput.matches("[ABCD]")) {
                System.out.println("Invalid row letter. Please enter A, B, C, or D.");
            } else {
                rowLetter = rowInput.charAt(0);
                break;
            }
        } while (true);

        int maxSeatNumber;
        // Determine max seat number based on row
        if (rowLetter == 'B' || rowLetter == 'C') {
            maxSeatNumber = 12;
        } else {
            maxSeatNumber = 14;
        }

        int seatNumber;
        // Input validation for seat number
        do {
            System.out.print("Enter the seat number (1-" + maxSeatNumber + "): ");
            if (scanner.hasNextInt()) {
                seatNumber = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (seatNumber < 1 || seatNumber > maxSeatNumber) {
                    System.out.println("Invalid seat number.");
                } else if (seatingPlan[rowLetter - 'A'][seatNumber - 1] == 'X') {
                    System.out.println("Seat " + rowLetter + seatNumber + " is already taken.");
                } else {
                    break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        } while (true);

        // Input for person's information
        System.out.print("Enter person's first name: ");
        String name;
        do {
            name = scanner.nextLine();
            if (!name.matches("[a-zA-Z]+")) {
                System.out.println("Invalid name. Please enter only letters.");
            } else {
                break;
            }
        } while (true);

        System.out.print("Enter person's surname: ");
        String surname;
        do {
            surname = scanner.nextLine();
            if (!surname.matches("[a-zA-Z]+")) {
                System.out.println("Invalid surname. Please enter only letters.");
            } else {
                break;
            }
        } while (true);

        System.out.print("Enter person's email: ");
        String email = scanner.nextLine();

        // Create person object
        Person person = new Person(name, surname, email);
        // Calculate ticket price
        double price = calculatePrice(rowLetter, seatNumber);
        // Mark seat as taken
        seatingPlan[rowLetter - 'A'][seatNumber - 1] = 'X';
        // Create ticket object
        Ticket ticket = new Ticket(rowLetter, seatNumber, price, person);
        // Add ticket to the array
        ticketsSold[rowLetter - 'A'][seatNumber - 1] = ticket;
        // Save ticket information to file
        ticket.save();

        System.out.println();
        System.out.println("Seat " + rowLetter + seatNumber + " has been successfully booked.");
        System.out.println();
        System.out.println("Ticket information:");
        ticket.printTicketInfo();
    }

    // Method to cancel a seat
    private static void cancelSeat(Scanner scanner) {
        System.out.println("You selected Option 2 - Cancel a seat.");
        System.out.println();
        System.out.print("Enter the row letter (A-D): ");
        String rowInput;
        char rowLetter;
        // Input validation for row letter
        do {
            rowInput = scanner.nextLine().toUpperCase();
            if (rowInput.length() != 1 || !rowInput.matches("[ABCD]")) {
                System.out.println("Invalid row letter. Please enter A, B, C, or D.");
            } else {
                rowLetter = rowInput.charAt(0);
                break;
            }
        } while (true);

        int maxSeatNumber;
        // Determine max seat number based on row
        if (rowLetter == 'B' || rowLetter == 'C') {
            maxSeatNumber = 12;
        } else {
            maxSeatNumber = 14;
        }

        int seatNumber;
        // Input validation for seat number
        do {
            System.out.print("Enter the seat number (1-" + maxSeatNumber + "): ");
            if (scanner.hasNextInt()) {
                seatNumber = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (seatNumber < 1 || seatNumber > maxSeatNumber) {
                    System.out.println("Invalid seat number.");
                } else {
                    break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        } while (true);

        int row = rowLetter - 'A';
        // Check if seat is available or not booked
        if (seatingPlan[row][seatNumber - 1] == 'O') {
            System.out.println("Seat " + rowLetter + seatNumber + " is not booked.");
            return;
        }

        // Check if there is a ticket for the seat
        if (ticketsSold[row][seatNumber - 1] == null) {
            System.out.println("No booking found for Seat " + rowLetter + seatNumber);
            System.out.println();
            return;
        }

        // Get the ticket to cancel
        Ticket ticketToCancel = ticketsSold[row][seatNumber - 1];
        // Mark seat as available
        seatingPlan[row][seatNumber - 1] = 'O';
        // Remove ticket from the array
        ticketsSold[row][seatNumber - 1] = null;

        System.out.println();
        System.out.println("Ticket information:");
        ticketToCancel.printTicketInfo();
        System.out.println();
        System.out.println("Canceling ticket for Seat " + rowLetter + seatNumber);
        System.out.println();
        System.out.println("Ticket has been canceled and seat is now available.");
    }

    // Method to find the first available seat
    private static void findFirstAvailable() {
        System.out.println("You selected Option 3 - Find first available seat.");
        System.out.println();
        for (int i = 0; i < seatingPlan.length; i++) {
            for (int j = 0; j < seatingPlan[i].length; j++) {
                // Check if seat is available
                if (seatingPlan[i][j] == 'O') {
                    char rowLetter = (char) ('A' + i);
                    int seatNumber = j + 1;
                    System.out.println("First available seat: " + rowLetter + seatNumber);
                    return;
                }
            }
        }

        System.out.println("Sorry, there are no available seats.");
    }

    // Method to display the seating plan
    private static void showSeatingPlan() {
        System.out.println("Seating Plan:");
        System.out.print("    ");
        for (int j = 0; j < 14; j++) {
            System.out.printf("%3d", j + 1); // Print seat numbers
        }
        System.out.println();

        for (int i = 0; i < seatingPlan.length; i++) {
            System.out.print((char) ('A' + i) + "   "); // Print row letter
            for (int j = 0; j < seatingPlan[i].length; j++) {
                if ((i == 1 || i == 2) && j >= 12) {
                    break; // Skip printing the last 2 seats for rows B and C
                }
                System.out.print(seatingPlan[i][j] + "  "); // Print seat status (O or X)
            }
            System.out.println();
        }
    }

    // Method to print information about tickets sold
    private static void printTicketsInfo() {
        System.out.println("You selected Option 5 - Show tickets sold.");
        System.out.println();
        System.out.println("Ticket Information");
        int totalTickets = 0;
        double totalAmount = 0;
        // Iterate through ticketsSold array
        for (Ticket[] row : ticketsSold) {
            for (Ticket ticket : row) {
                if (ticket != null) {
                    ticket.printTicketInfo(); // Print ticket information
                    totalTickets++;
                    totalAmount += ticket.getPrice(); // Calculate total amount
                }
            }
        }
        System.out.println();
        System.out.println("Total number of tickets sold: " + totalTickets);
        System.out.println("Total amount for all tickets: £" + totalAmount);
    }

    // Method to search for a ticket
    private static void searchTicket(Scanner scanner) {
        System.out.println("You selected Option 6 - Search for a ticket.");
        System.out.println();
        String rowInput;
        char rowLetter;
        // Input validation for row letter
        do {
            System.out.print("Enter the row letter (A-D): ");
            rowInput = scanner.nextLine().toUpperCase();
            if (rowInput.length() != 1 || !rowInput.matches("[ABCD]")) {
                System.out.println("Invalid row letter. Please enter A, B, C, or D.");
            } else {
                rowLetter = rowInput.charAt(0);
                break;
            }
        } while (true);

        int maxSeatNumber;
        // Determine max seat number based on row
        if (rowLetter == 'A' || rowLetter == 'D') {
            maxSeatNumber = 14;
        } else {
            maxSeatNumber = 12;
        }

        int seatNumber;
        // Input validation for seat number
        do {
            System.out.print("Enter the seat number (1-" + maxSeatNumber + "): ");
            if (scanner.hasNextInt()) {
                seatNumber = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (seatNumber < 1 || seatNumber > maxSeatNumber) {
                    System.out.println("Invalid seat number.");
                } else {
                    break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        } while (true);

        int row = rowLetter - 'A';
        // Check if there is a ticket for the seat
        if (ticketsSold[row][seatNumber - 1] != null) {
            System.out.println();
            System.out.println("Ticket Information:");
            ticketsSold[row][seatNumber - 1].printTicketInfo(); // Print ticket information
        } else {
            System.out.println("This seat is available for booking.");
        }
    }

    // Method to calculate the price of a ticket based on row and seat number
    private static double calculatePrice(char rowLetter, int seatNumber) {
        if ((rowLetter == 'A' && seatNumber <= 5) ||
                (rowLetter == 'B' && seatNumber <= 5) ||
                (rowLetter == 'C' && seatNumber <= 5) ||
                (rowLetter == 'D' && seatNumber <= 5)) {
            return 200.0; // £200 for seats A1-D5
        } else if ((rowLetter == 'A' && seatNumber <= 9) ||
                (rowLetter == 'B' && seatNumber <= 9) ||
                (rowLetter == 'C' && seatNumber <= 9) ||
                (rowLetter == 'D' && seatNumber <= 9)) {
            return 150.0; // £150 for seats A6-D9
        } else {
            return 180.0; // £180 for seats A10-D14
        }
    }
}
