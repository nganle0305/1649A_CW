import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Order[] orders = new Order[5];
        boolean running = true;
        do {
            System.out.println("1. Create and Place a New Order"); //queue
            System.out.println("2. Search for an Order by ID"); //search id to find order
            System.out.println("3. Sort Books in an Order"); //Sort books in order by title or author
            System.out.println("4. View Book Inventory");
            System.out.println("5. View All Orders in Processing Queue");
            System.out.println("6. Exit");

            System.out.print("Enter your choice (1-6): ");
            int choice = sc.nextInt();
            switch ( choice ) {
                case 1: //add book order
                    System.out.println("Enter your name: ");


                    break;

                case 2:
                    System.out.println();

                    break;

                case 3:
                    System.out.println();

                    break;
                case 4:
                    System.out.println();

                    break;
                case 5:
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please select a valid option (1-5).");
                    break;
            }
        } while (running);
    }
}

