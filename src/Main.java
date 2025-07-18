import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayListADT<Order> orders = new ArrayListADT<>();
        LinkedQueueADT<Order> orderQueue = new LinkedQueueADT<>();

        ArrayListADT<Book> inventory = new ArrayListADT<>();
        inventory.add(new Book("J.K. Rowling", "Harry Potter", 12.99, 0, 10));
        inventory.add(new Book("George Orwell", "1984", 9.99, 0, 5));
        inventory.add(new Book("Jane Austen", "Pride and Prejudice", 14.50, 0, 7));

        boolean running = true;
        do {
            System.out.println("1. Create and Place a New Order");
            System.out.println("2. Search for an Order by ID");
            System.out.println("3. Sort Books in an Order");
            System.out.println("4. View Book Inventory");
            System.out.println("5. View All Orders in Processing Queue");
            System.out.println("6. Exit");

            System.out.print("Enter your choice (1-6): ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch ( choice ) {
                case 1: {
                    System.out.println("\n--- Create and Place New Order ---");

                    System.out.print("\nEnter customer name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter customer address: ");
                    String address = sc.nextLine();

                    Customer customer = new Customer(name, address);
                    ArrayListADT<Book> bookList = new ArrayListADT<>();

                    boolean adding = true;

                    while (adding) {
                        System.out.println("\nAvailable books:");
                        for (int i = 0; i < inventory.size(); i++) {
                            System.out.println((i + 1) + ". " + inventory.get(i).toStockString());
                        }

                        System.out.print("Select book number (1 to " + inventory.size() + "): ");
                        int bookIndex = Integer.parseInt(sc.nextLine()) - 1;

                        if (bookIndex < 0 || bookIndex >= inventory.size()) {
                            System.out.println("Invalid book number. Try again.");
                            continue;
                        }

                        Book selected = inventory.get(bookIndex);
                        System.out.print("Enter quantity to order (available: " + selected.getStock() + "): ");
                        int quantity = Integer.parseInt(sc.nextLine());

                        if (quantity <= 0 || quantity > selected.getStock()) {
                            System.out.println("Invalid quantity.");
                        } else {
                            Book orderedBook = new Book(
                                    selected.getAuthor(), selected.getTitle(), selected.getPrice(), quantity, 0); // ordered books don't have stock
                            bookList.add(orderedBook);
                            selected.setStock(selected.getStock() - quantity);
                            System.out.println("Book added to order.");
                        }

                        System.out.print("Add another book? (y/n): ");
                        adding = sc.nextLine().equalsIgnoreCase("y");
                    }

                    if (bookList.isEmpty()) {
                        System.out.println("No books selected. Order not created.");
                        break;
                    }

                    Order order = new Order(customer, bookList);
                    orders.add(order);           // ArrayListADT<Order>
                    orderQueue.offer(order);     // LinkedQueueADT<Order>
                    System.out.println("✅ Order placed successfully. Order ID: " + order.getOrderID());

                    break;
                }

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

