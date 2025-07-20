import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayListADT<Order> orders = new ArrayListADT<>();
        LinkedQueueADT<Order> orderQueue = new LinkedQueueADT<>();
        LinkedStackADT<Order> orderHistory = new LinkedStackADT<>();


        ArrayListADT<Book> inventory = new ArrayListADT<>();
        inventory.add(new Book("J.K. Rowling", "Harry Potter", 12.99, 0, 10));
        inventory.add(new Book("George Orwell", "1984", 9.99, 0, 10));
        inventory.add(new Book("Jane Austen", "Pride and Prejudice", 14.50, 0, 10));
        inventory.add(new Book("Matt Haig", "The Midnight Library", 13.75, 0, 10));
        inventory.add(new Book("R.J. Palacio", "Wonder", 10.50, 0, 5));
        inventory.add(new Book("Rachel Ignotofsky", "Women in Science", 16.00, 0, 10));


        boolean running = true;

        do {
            System.out.println("\n Online Bookstore main menu\n");
            System.out.println("1. Place a New Order");
            System.out.println("2. Delete an Order");
            System.out.println("3. Search for an Order");
            System.out.println("4. View Book Inventory");
            System.out.println("5. View Orders in Queue");
            System.out.println("6. View Order History"); //history add or delete
            System.out.println("7. Exit");

            System.out.print("Enter your choice (1-7): ");
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

                        int quantity = -1;
                        while (true) {
                            System.out.print("Enter quantity to order (available: " + selected.getStock() + "): ");
                            String quantityInput = sc.nextLine().trim();
                            try {
                                quantity = Integer.parseInt(quantityInput);
                                if (quantity <= 0 || quantity > selected.getStock()) {
                                    System.out.println("Invalid quantity.");
                                } else {
                                    Book orderedBook = new Book(
                                            selected.getAuthor(), selected.getTitle(), selected.getPrice(), quantity, 0);
                                    bookList.add(orderedBook);
                                    selected.setStock(selected.getStock() - quantity);
                                    System.out.println("Book added to order");
                                    break;
                                }

                            } catch (NumberFormatException e) {
                                System.out.println("Invalid book number. Try again.");
                            }

                        }
                        System.out.println("Add another book? (y/n): ");
                        adding = sc.nextLine().trim().equalsIgnoreCase("y");
                    }

                    if (bookList.isEmpty()) {
                        System.out.println("No books selected. Order not created.");
                        break;
                    }

                    Order order = new Order(customer, bookList);
                    orders.add(order);
                    orderQueue.offer(order);
                    order.setStatus("Added");//store order in history
                    orderHistory.push(order);

                    System.out.println("Order placed successfully. Order ID: " + order.getOrderID());

                    break;
                }

                case "2": {
                    if (orderQueue.isEmpty()) {
                        System.out.println("The order queue is currently empty.");
                        break;
                    }

                    System.out.println("\nCurrent Orders Queue");
                    LinkedQueueADT<Order> tempDisplayQueue = new LinkedQueueADT<>(); //create a new queue and copy info from old
                    while (!orderQueue.isEmpty()) {
                        Order current = orderQueue.poll();
                        System.out.println((current.getOrderID() + ", "));
                        tempDisplayQueue.offer(current);
                    }
                    System.out.println();
                    while (!tempDisplayQueue.isEmpty()) {
                        orderQueue.offer(tempDisplayQueue.poll());
                    }

                    System.out.print("Enter Order ID to cancel: ");
                    String cancelIdStr = sc.nextLine().trim();
                    int cancelId;
                    try {
                        cancelId = Integer.parseInt(cancelIdStr);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Order ID.");
                        break;
                    }

                    boolean found = false;
                    LinkedQueueADT<Order> tempQueue = new LinkedQueueADT<>();

                    while (!orderQueue.isEmpty()) {
                        Order current = orderQueue.poll();

                        if (current.getOrderID() == cancelId) {

                            current.setStatus("Deleted");
                            orderHistory.push(current);

                            System.out.println("Order " + cancelId + " has been cancelled and removed from the queue.");
                            found = true;

                            // Optional: Restock books
                            for (int i = 0; i < current.getBookList().size(); i++) {
                                Book orderedBook = current.getBookList().get(i);
                                for (int j = 0; j < inventory.size(); j++) {
                                    if (inventory.get(j).getTitle().equals(orderedBook.getTitle())) {
                                        inventory.get(j).setStock(inventory.get(j).getStock() + orderedBook.getQuantity());
                                    }
                                }
                            }

                        } else {
                            tempQueue.offer(current);
                        }
                    }

                    // Restore queue
                    while (!tempQueue.isEmpty()) {
                        orderQueue.offer(tempQueue.poll());
                    }

                    if (!found) {
                        System.out.println("Order ID not found in the queue.");
                    }
                    break;
                }

                case "3": {
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

                case "4": {
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

                    // Restore the original orderQueue
                    while (!tempQueue.isEmpty()) {
                        orderQueue.offer(tempQueue.poll());
                    }
                    break;
                }

                case "6": {
                    System.out.println("\n--- Order History ---");
                    if (orderHistory.isEmpty()) {
                        System.out.println("No order history available.");
                    } else {
                        LinkedStackADT<Order> tempStack = new LinkedStackADT<>();
                        while (!orderHistory.isEmpty()) {
                            Order pastOrder = orderHistory.pop();

                            System.out.println(pastOrder.toHistoryString());

                            tempStack.push(pastOrder); // preserve order
                        }
                        while (!tempStack.isEmpty()) {
                            orderHistory.push(tempStack.pop());
                        }
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

