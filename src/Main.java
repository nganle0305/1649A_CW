import java.util.Scanner; // Import Scanner for user input

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in); // Create Scanner object for reading user input

        // Create data structures for orders, queue, and history stack
        ArrayListADT<Order> orders = new ArrayListADT<>();
        LinkedQueueADT<Order> orderQueue = new LinkedQueueADT<>();
        LinkedStackADT<Order> orderHistory = new LinkedStackADT<>();

        // Create book inventory with 6 books
        ArrayListADT<Book> inventory = new ArrayListADT<>();
        inventory.add(new Book("J.K. Rowling", "Harry Potter", 12.99, 0, 10));
        inventory.add(new Book("George Orwell", "1984", 9.99, 0, 10));
        inventory.add(new Book("Jane Austen", "Pride and Prejudice", 14.50, 0, 10));
        inventory.add(new Book("Matt Haig", "The Midnight Library", 13.75, 0, 10));
        inventory.add(new Book("R.J. Palacio", "Wonder", 10.50, 0, 10));
        inventory.add(new Book("Rachel Ignotofsky", "Women in Science", 16.00, 0, 10));


        boolean running = true;

        do {
            System.out.println("--------------------------------------");
            System.out.println("Online Bookstore main menu\n");
            System.out.println("1. Place a New Order");
            System.out.println("2. Process an Order");
            System.out.println("3. Search for an Order");
            System.out.println("4. View Book Inventory");
            System.out.println("5. View Orders in Queue");
            System.out.println("6. View Order History"); //history add or delete
            System.out.println("7. Exit");

            System.out.print("Enter your choice (1-7): ");
            String choice = sc.nextLine().trim(); // remove blank in user input

            switch (choice) {

                case "1": {
                    System.out.println("\n--- Place New Order ---");
                    // Get customer information
                    System.out.print("\nEnter customer name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter customer address: ");
                    String address = sc.nextLine();

                    Customer customer = new Customer(name, address);
                    ArrayListADT<Book> bookList = new ArrayListADT<>(); // List of books in the order

                    boolean adding = true;
                    int input;

                    // Loop to add multiple books to the order
                    while (adding) {

                        System.out.println("\nAvailable books:");
                        for (int i = 0; i < inventory.size(); i++) {
                            System.out.println((i + 1) + ". " + inventory.get(i).toStockString());
                        }
                        System.out.print("Select book number (1 to " + inventory.size() + "): ");
                        String bookIndex = sc.nextLine().trim();

                        // Validate book index input
                        try {
                            input = Integer.parseInt(bookIndex);
                            if (input <= 0 || input > inventory.size()) {
                                System.out.println("Invalid book number. Try again.");
                                continue;
                            }
                        } catch (NumberFormatException e) {
                                System.out.println("Invalid book number. Try again.");
                                continue;
                        }

                        Book selected = inventory.get(input - 1); // index start with 0

                        int quantity;
                        while (true) { // Loop to validate quantity input
                            System.out.print("Enter quantity to order (available: " + selected.getStock() + "): ");
                            String quantityInput = sc.nextLine().trim();
                            try {
                                quantity = Integer.parseInt(quantityInput);
                                if (quantity <= 0 || quantity > selected.getStock()) {
                                    System.out.println("Invalid quantity.");
                                } else { // Create a book object with quantity for the order
                                    Book orderedBook = new Book(
                                            selected.getAuthor(), selected.getTitle(), selected.getPrice(), quantity, 0);
                                    bookList.add(orderedBook);
                                    selected.setStock(selected.getStock() - quantity);
                                    System.out.println("Book added to order");
                                    break;
                                }

                            } catch (NumberFormatException e) {
                                System.out.println("Invalid quantity number. Try again.");
                            }

                        }
                        // Ask if user wants to add more books
                        System.out.println("Add another book? (y/n): ");
                        adding = sc.nextLine().trim().equalsIgnoreCase("y");
                    }
                    // If no book was added, cancel the order
                    if (bookList.isEmpty()) {
                        System.out.println("No books selected. Order not created.");
                        break;
                    }
                    // Create order, add to lists, and push to history
                    Order order = new Order(customer, bookList);
                    orders.add(order);
                    orderQueue.offer(order);
                    order.setStatus("Processing");//store order in queue

                    System.out.println("Order placed successfully. Order ID: " + order.getOrderID());

                    break;
                }

                case "2": {
                    if (orderQueue.isEmpty()) {
                        System.out.println("The order queue is currently empty.");
                        break;
                    }

                    Order processedOrder = orderQueue.peek();  // The first in queue (FIFO)

                    System.out.println("\n--- Processing Order Preview ---");
                    System.out.println(processedOrder);  // show info of order

                    System.out.print("Do you want to process this order? (y/n): ");
                    String confirm = sc.nextLine().trim();

                    if (confirm.equalsIgnoreCase("y")) {
                        processedOrder.setStatus("Delivering");       // Update status
                        orderQueue.poll();                            // Remove in queue
                        orderHistory.push(processedOrder);            // Save in history (Stack)

                        System.out.println("Order " + processedOrder.getOrderID() + " is now Delivering.");
                    } else {
                        System.out.println("Order remains in the queue.");
                    }

                    break;
                }

                case "3": {
                    if (orders.isEmpty()) {
                        System.out.println("There are no orders to search.");
                        break;
                    }

                    // Show list of order ID
                    System.out.println("Available Order IDs:");
                    for (int i = 0; i < orders.size(); i++) {
                        System.out.println("- Order ID: " + orders.get(i).getOrderID());
                    }
                    while (true) {
                        System.out.print("Enter Order ID: ");
                        String inputId = sc.nextLine().trim();
                        int searchId;

                        try { // Try to parse Order ID
                            searchId = Integer.parseInt(inputId);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Order ID: '" + inputId + "' is not valid. Please try again.");
                            continue; // Comeback to enter ID
                        }

                        // Linear search in orders list
                        Order targetOrder = null;
                        for (int i = 0; i < orders.size(); i++) {
                            if (orders.get(i).getOrderID() == searchId) {
                                targetOrder = orders.get(i);
                                break;
                            }
                        }

                        if (targetOrder == null) {
                            //If it doesn't find order, get error and try again
                            System.out.println("Order ID " + searchId + " not found. Please try again.");
                        } else {
                            // If find order, print and exit
                            System.out.println("\nOrder found:");
                            System.out.println(targetOrder);
                            break; // exit case 3 loop when found
                        }
                    }

                    break;
                }

                case "4": {
                    if (inventory.isEmpty()) {
                        System.out.println("Inventory is empty.");
                        break;
                    }

                    System.out.println("\nCurrent Book Inventory:");
                    for (int i = 0; i < inventory.size(); i++) {
                        System.out.println((i + 1) + ". " + inventory.get(i).toStockString());
                    }

                    // Ask user if they want to sort
                    System.out.print("\nDo you want to sort the inventory? (y/n): ");
                    String sortChoice = sc.nextLine();

                    if (sortChoice.equalsIgnoreCase("y")) {
                        System.out.println("Sort inventory by:");
                        System.out.println("1. Price");
                        System.out.println("2. Title");
                        System.out.println("3. Author");

                        System.out.print("Enter your choice: ");

                        String inputSort = sc.nextLine().trim();
                        int sortOption;

                        try {
                            sortOption = Integer.parseInt(inputSort);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid sort option. Please enter a number.");
                            break;
                        }

                        // Sort using BubbleSort
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
                case "5": {
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

                    while (!tempQueue.isEmpty()) { // Restore the original orderQueue
                        orderQueue.offer(tempQueue.poll());
                    }
                    break;
                }
                case "6": {
                    System.out.println("\n--- Order History ---");

                    LinkedStackADT<Order> tempStack = new LinkedStackADT<>();
                    boolean foundDelivering = false;

                    while (!orderHistory.isEmpty()) {
                        Order pastOrder = orderHistory.pop();

                        if (pastOrder.getStatus().equalsIgnoreCase("Delivering")) {
                            pastOrder.printHistoryDetails();
                            foundDelivering = true;
                        }

                        tempStack.push(pastOrder);
                    }

                    // Restore original orderHistory stack
                    while (!tempStack.isEmpty()) {
                        orderHistory.push(tempStack.pop());
                    }

                    // Print message only once if nothing found
                    if (!foundDelivering) {
                        System.out.println("No processed orders in history.");
                    }
                    break;
                }

                case "7":
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please select a valid option (1-7).");
                    break;
            }
        } while (running);
    }
}

