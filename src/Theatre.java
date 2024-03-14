import java.io.File; //imports the java class from the java.io.package
import java.io.FileNotFoundException; //imports the FIleNotFoundException from the java.io.package
import java.io.FileWriter; //imports the FileWriter class from the java.io.package
import java.io.IOException; //imports the IOException class from the java.io.package
import java.util.InputMismatchException; //imports the InputMismatchException class from the java.io.package
import java.util.*; //imports the java.util package
public class Theatre {
    public static ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    //array list for the store ticket details
    public static int[][] seat_booking = new int[3][];
    static {
        seat_booking[0] = new int[12];
        seat_booking[1] = new int[16]; //3 arrays (one for each row)
        seat_booking[2] = new int[20];
    }
    public static void main(String[] args) {
        System.out.println("Welcome to the New Theatre");
        while (true) {
            Scanner get = new Scanner(System.in);
            try {
                while (true) {
                    System.out.println("_".repeat(50) + "\nPlease select an option:\n1) Buy a ticket\n2) " +
                            "Print seating area\n3) Cancel ticket\n4) List available seats\n5) Save to file\n6) " +
                            "Load from file\n7) Print ticket information and total price\n8) " +
                            "Sort tickets by price\n    0) Quit\n" + "_".repeat(50) + "\n");
                    System.out.print("Enter option: ");
                    int option = get.nextInt(); //input for the option
                    switch (option) {
                        case 1 -> buy_ticket(get);
                        case 2 -> print_seating_area();
                        case 3 -> cancel_ticket(get);
                        case 4 -> show_available();     //switch case for the select an option
                        case 5 -> save();
                        case 6 -> load();
                        case 7 -> show_tickets_info();
                        case 8 -> sort_tickets();
                        case 0 -> System.exit(0);
                        default -> System.out.println("Invalid selection,please try again");
                    }
                }
            }catch (InputMismatchException e) { // for the handling error
                System.out.println("Integer required ");
            }
        }
    }

    public static void buy_ticket(Scanner get) {
    /*This method enables users to buy a ticket by
     *providing their name, email, and selecting a seat from the seating area.
     *The system verifies seat availability and prompts the user to enter the ticket price based on the selected row.
     *A new Ticket object is created with the given information,dded to the list of tickets, and the seat is marked as sold.
     *If the input is invalid or the seat is already sold,the method displays an error message and returns.*/
        System.out.print("Enter row number: ");
        int row_numb = get.nextInt() - 1;
        if (row_numb < 0 || row_numb >= seat_booking.length) {//// Check if the row number is valid
            System.out.println("Invalid row number..!");
            return;
        }
        System.out.print("Enter seat number: ");
        int seat_numb = get.nextInt() - 1;
        if (seat_numb < 0 || seat_numb >= seat_booking[row_numb].length) {// Check if the seat number is valid
            System.out.println("Invalid seat number..!");
            return;
        }
        if (seat_booking[row_numb][seat_numb] == 1) {// Check if the seat is already sold
            System.out.println("already entered");
            return;
        }
        System.out.print("Enter name: ");
        get.nextLine();
        String name = get.nextLine();
        System.out.print("Enter surname: ");
        String surname = get.nextLine();
        System.out.print("Enter email: ");
        String email = get.nextLine();
        Person person = new Person(name, surname, email);
        double price;
        while (true) {
            System.out.print("(Row 1 = £20 | nRow 2 = £15 | Row 3 = £10)\n Enter price: £");
            price = get.nextDouble();
            if((row_numb==0 && price==20)||(row_numb==1 && price==15)||(row_numb==2 && price==10)){
                break;
            }else{
                System.out.println("Invalid price Input..!");
            }
        }
        seat_booking[row_numb][seat_numb] = 1;// Mark the seat as sold on the seating area
        System.out.println("Seat booked successfully");
        Ticket ticket = new Ticket(row_numb, seat_numb, price, person);// Create a new Ticket object with the given details and add it to the list of tickets
        tickets.add(ticket);
    }
    public static void print_seating_area() {
        System.out.println("     ***********\n     *  STAGE  *\n     ***********");
        for (int i = 0; i < seat_booking.length; i++) {//iterate through the rows of the seating array and prints the seats in each row using the print_seating method.
            if (i == 0) {//to print the first row of seats
                System.out.print("    ");
                print_seating(i);
                System.out.println(); //move to the next row
            } else if (i == 1) {//to print the second row of seats
                System.out.print("  ");
                print_seating(i);
                System.out.println(); //move to the next row
            } else if (i == 2) {//to print the third row of seats
                print_seating(i);
                System.out.println(); //move to the next row
            }
        }
    }
    public static void cancel_ticket(Scanner get) {/*
         *This method permits users to cancel a previously booked seat by entering the row and seat numbers.
         *The system verifies that the entered values are valid and that the seat is already booked.
         *If these conditions are met, the seat booking is cancelled, and the corresponding ticket object is removed from the tickets list, making the seat available again.
         *If the input is invalid or the seat is already available, the method displays an error message.*/
        System.out.print("Enter row number: ");// Get the row number from the user
        int row_numb = get.nextInt() - 1;
        if (row_numb < 0 || row_numb >= seat_booking.length) {
            System.out.println("Invalid row number..!");
            return;
        }
        System.out.print("Enter seat number: ");// Get the seat number from the user
        int seat_numb = get.nextInt() - 1;
        if (seat_numb < 0 || seat_numb >= seat_booking[row_numb].length) {
            System.out.println("Invalid seat number..!");
            return;
        }
        if (seat_booking[row_numb][seat_numb] == 0) {// Check if the seat is already available
            System.out.println("Ticket is already canceled");
            return;
        }
        seat_booking[row_numb][seat_numb] = 0;//Mark the seat as available and remove the corresponding ticket
        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            if (ticket.row == row_numb && ticket.seat == seat_numb) {
                tickets.remove(i);
                System.out.println("Ticket cancelled successfully.");
                return;
            }
        }
    }
    public static void show_available() {//This method displays the available seats for each row in the seating area
        System.out.print("Seats available in row 1: ");// Display available seats for row 1
        print_rows_and_seats(0);
        System.out.println();
        System.out.print("Seats available in row 2: ");// Display available seats for row 2
        print_rows_and_seats(1);
        System.out.println();
        System.out.print("Seats available in row 3: ");// Display available seats for row 3
        print_rows_and_seats(2);
        System.out.println();
    }
    public static void save() {
        try {
            File textfile = new File("Information.txt");
            if (textfile.createNewFile()) {
                System.out.println("File created: " + textfile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter("Information.txt");
            for (int i = 0; i < seat_booking.length; i++) {//Iterate over each row in the seating area
                for (int j = 0; j < seat_booking[i].length; j++) {// Iterate over each seat in the row
                    if (seat_booking[i][j] == 1) {// Write a "1" to the file if the seat is booked, or a "0" if the seat is available
                        myWriter.write("1");
                    } else {
                        myWriter.write("0");
                    }
                }myWriter.write("\n");// After writing all the seats in the row, write a newline character to move to the next line in the file
            }
           ;
            myWriter.close();// Close the FileWriter object to save the changes to the file
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {// If an error occurs while writing to the file, catch the exception and print an error message along with a stack trace to the console
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void load() {/*
    *This method loads seat reservations from a file named "Information.txt" and updates
     the "seats" array with the previous reservations.
    *If the file is not found, the method displays an error message on the console.*/
        try{
            File textfile = new File("Information.txt");// Create a new File object that represents a file with the specified pathname ("Information.txt")
            Scanner reader = new Scanner(textfile);
            for(int i = 0;i< seat_booking.length;i++){// Read the next line of the file
                    String line = reader.nextLine();
                    for(int j = 0; j<seat_booking[i].length;j++){// Convert the character at index j of the line to an integer value (0 or 1)
                        int k =Character.getNumericValue(line.codePointAt(j));
                        if(k==1){
                            seat_booking[i][j]=1;// If the integer value is 1, set the corresponding seat in the "seat_booking" array to 1 (reserved)
                        }else{
                            seat_booking[i][j]=0;// If the integer value is 0, set the corresponding seat in the "seat_booking" array to 0 (available)
                        }
                    }
            }
            reader.close();
            System.out.println("The program was completely restored.");
        }catch (FileNotFoundException e){// If the file is not found, print an error message to the console
            System.out.println("An error occurred. File not found. No data loaded");
        }
    }
    public static void show_tickets_info() {//This method prints information about all tickets sold.
         double totalPrice = 0.0; //variable for the assign total value
        System.out.println("_".repeat(50));
        for (Ticket ticket : tickets) {
            ticket.print();//calling to print method on Ticket,java
            totalPrice += ticket.price;
            System.out.println();
        }
        System.out.println("_".repeat(30));
        System.out.println("Total price:  £"+totalPrice);
    }
    public static void sort_tickets() {//sorting method for get the lowest price tickets for first
        double totalPrice = 0.0;
        System.out.println("_".repeat(50));
        Ticket[] sorted = tickets.toArray(new Ticket[0]);
        int n = sorted.length;
        boolean check;
        do {//A bubble sort algorithm is used to sort the tickets based on their price, from lowest to highest. The sorted array is updated accordingly.
            check = false;
            for (int i = 1; i < n; i++) {
                if (sorted[i-1].getPrice() > sorted[i].getPrice()) {
                    Ticket temporary = sorted[i-1];
                    sorted[i-1] = sorted[i];
                    sorted[i] = temporary;
                    check = true;
                }
            }
            n--;
        } while (check);
        for (Ticket ticket : sorted) {//The method uses a for-each loop to iterate over the sorted array and print out the ticket information using the "print()" method of the Ticket object.
            ticket.print();
            totalPrice += ticket.getPrice();
            System.out.println();
        }
        System.out.println("_".repeat(30));
        System.out.println("Total price: £" + totalPrice);
    }
    public static void print_seating(int i){//User-defined method to remove duplication in print_seating_area method
        for (int j = 0; j < seat_booking[i].length; j++) {// Loop through each seat in the specified row
            if (j == seat_booking[i].length / 2) {
                if (seat_booking[i][j] == 1) {// If the current seat is the middle seat, print "X" if it's sold, "O" if it's available
                    System.out.print(" ");
                    System.out.print("X");
                } else {
                    System.out.print(" ");
                    System.out.print("0");
                }
            } else {// If the current seat is not the middle seat, print "X" if it's sold, "O" if it's available
                if (seat_booking[i][j] == 1) {
                    System.out.print("X");
                } else {
                    System.out.print("0");
                }
            }
        }
    }
    public static void print_rows_and_seats(int i){//User-defined method to remove duplication in show_available method
        for (int j = 0; j < seat_booking[i].length; j++) {
            if (seat_booking[i][j] == 1) {// If the current seat is sold
                System.out.print("");
            } else {
                System.out.print(j + 1);
                if (j == seat_booking[i].length - 1) {
                    System.out.print(".");
                } else {
                    System.out.print(",");
                }
            }
        }
    }
}