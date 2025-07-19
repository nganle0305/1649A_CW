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
            System.out.println("\n Online Bookstore main menu\n");
            System.out.println("1. Place a New Order");
            System.out.println("2. Search for an Order"); //Search order
            System.out.println("3. View Book Inventory"); //view and sort order
            System.out.println("4. View All Orders in Queue");
            System.out.println("5. Exit");

            System.out.print("Enter your choice (1-5): ");
            String choice = sc.nextLine().trim();


            switch (choice) {

                case "1": {
                    System.out.println("\n--- Place New Order ---");

                    System.out.print("\nEnter customer name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter customer address: ");
                    String address = sc.nextLine();

                    Customer customer = new Customer(name, address);
                    ArrayListADT<Book> bookList = new ArrayListADT<>();

                    boolean adding = true;
                    int input;
                    while (adding) {

                        System.out.println("\nAvailable books:");
                        for (int i = 0; i < inventory.size(); i++) {
                            System.out.println((i + 1) + ". " + inventory.get(i).toStockString());
                        }
                        System.out.print("Select book number (1 to " + inventory.size() + "): ");
                        String bookIndex = sc.nextLine().trim();
                        try {
                            input = Integer.parseInt(bookIndex);
                            if (input < 0 || input >= inventory.size()) {
                                System.out.println("Invalid book number. Try again.");
                                continue;
                            }
                        } catch (NumberFormatException e) {
                                System.out.println("Invalid book number. Try again.");
                                continue;
                        }

                        Book selected = inventory.get(input);
                        System.out.print("Enter quantity to order (available: " + selected.getStock() + "): ");
                        int quantity;
                        String quantityInput = sc.nextLine().trim();
                        try {
                            quantity = Integer.parseInt(quantityInput);
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
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid book number. Try again.");
                        }
                    }
                    if (bookList.isEmpty()) {
                        System.out.println("No books selected. Order not created.");
                        break;
                    }

                    Order order = new Order(customer, bookList);
                    orders.add(order);
                    orderQueue.offer(order);
                    System.out.println("Order placed successfully. Order ID: " + order.getOrderID());

                    break;
                }
                case "2": {
                    System.out.print("Enter Order ID: ");
                    String inputId = sc.nextLine().trim();
                    int searchId;

                    try {
                        searchId = Integer.parseInt(inputId);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Order ID. Please enter a number.");
                        break;
                    }

                    Order targetOrder = null;

                    for (int i = 0; i < orders.size(); i++) {
                        if (orders.get(i).getOrderID() == searchId) {
                            targetOrder = orders.get(i);
                            break;
                        }
                    }
                    if (targetOrder == null) {
                        System.out.println("Order not found.");
                    } else {
                        System.out.println("\nOrder found:");
                        System.out.println(targetOrder);
                    }
                    break;
                }

                case "3": {
                    if (inventory.isEmpty()) {
                        System.out.println("Inventory is empty.");
                        break;
                    }

                    System.out.println("\nCurrent Book Inventory:");
                    for (int i = 0; i < inventory.size(); i++) {
                        System.out.println((i + 1) + ". " + inventory.get(i).toStockString());
                    }

                    System.out.print("\nDo you want to sort the inventory? (y/n): ");
                    String sortChoice = sc.nextLine();

                    if (sortChoice.equalsIgnoreCase("y")) {
                        System.out.println("Sort inventory by:");
                        System.out.println("1. Price");
                        System.out.println("2. Title");
                        System.out.println("3. Author");

                        System.out.print("Enter your choice: ");
                        System.out.print("Enter your choice: ");
                        String inputSort = sc.nextLine().trim();
                        int sortOption;

                        try {
                            sortOption = Integer.parseInt(inputSort);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid sort option. Please enter a number.");
                            break;
                        }

                        switch (sortOption) {
                            case 1:
                                BubbleSort.sortByPrice(inventory);
                                System.out.println("\nInventory sorted by price:");
                                break;
                            case 2:
                                BubbleSort.sortByTitle(inventory);
                                System.out.println("\nInventory sorted by title:");
                                break;
                            case 3:
                                BubbleSort.sortByAuthor(inventory);
                                System.out.println("\nInventory sorted by author:");
                                break;
                            default:
                                System.out.println("Invalid sorting option.");
                                break;
                        }

                        // Display sorted inventory
                        for (int i = 0; i < inventory.size(); i++) {
                            System.out.println((i + 1) + ". " + inventory.get(i).toStockString());
                        }
                    }
                    break;
                }
                case "4": {
                    System.out.println("\n--- All Orders in Queue ---");

                    if (orderQueue.isEmpty()) {
                        System.out.println("The order queue is currently empty.");
                        break;
                    }

                    // Create a copy because use poll to print so will lose all info
                    LinkedQueueADT<Order> tempQueue = new LinkedQueueADT<>();

                    while (!orderQueue.isEmpty()) {
                        Order current = orderQueue.poll();    // Get to the head of the queue
                        System.out.println(current);          // Print order info
                        tempQueue.offer(current);             // Put back in temporary queue
                    }

                    // Restore the original orderQueue
                    while (!tempQueue.isEmpty()) {
                        orderQueue.offer(tempQueue.poll());
                    }
                    break;
                }
                case "5":
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

