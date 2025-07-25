import java.util.Random;

public class PerformanceTest {

    public static void main(String[] args) {

        ArrayListADT<Order> orders = new ArrayListADT<>();
        LinkedQueueADT<Order> orderQueue = new LinkedQueueADT<>();
        LinkedStackADT<Order> orderHistory = new LinkedStackADT<>();
        ArrayListADT<Book> inventory = new ArrayListADT<>();

        // Initialize Inventory with 1000 books for realistic sorting test
        for (int i = 0; i < 1000; i++) {
            inventory.add(new Book("Author " + i, "Title " + i, (double) (i % 100 + 1), 0, 10));
        }

        // CASE 1: Place 1000 Orders
        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            Customer customer = new Customer("Customer " + i, "Address " + i);
            ArrayListADT<Book> bookList = new ArrayListADT<>();
            bookList.add(new Book("Author " + i, "Title " + i, (double) (i % 100 + 1), 1, 0));

            Order order = new Order(customer, bookList);
            orders.add(order);
            orderQueue.offer(order);
            order.setStatus("Processing");
        }
        long endTime = System.nanoTime();
        System.out.println("Time to place 1000 orders: " + (endTime - startTime) + " ns");

        // CASE 2: Process 500 Orders
        startTime = System.nanoTime();
        for (int i = 0; i < 500; i++) {
            if (!orderQueue.isEmpty()) {
                Order processedOrder = orderQueue.poll();
                processedOrder.setStatus("Delivering");
                orderHistory.push(processedOrder);
            }
        }
        endTime = System.nanoTime();
        System.out.println("Time to process 500 orders: " + (endTime - startTime) + " ns");

        // CASE 3: Search for Random Order IDs (Linear Search)
        Random rand = new Random();
        int searchCount = 500;
        startTime = System.nanoTime();
        for (int i = 0; i < searchCount; i++) {
            int searchId = rand.nextInt(1000) + 1; // Random ID from 1 to 1000
            for (int j = 0; j < orders.size(); j++) {
                if (orders.get(j).getOrderID() == searchId) {
                    break; // Found
                }
            }
        }
        endTime = System.nanoTime();
        System.out.println("Time to search " + searchCount + " orders (Linear Search): " + (endTime - startTime) + " ns");

        // CASE 4: Bubble Sort Inventory by Price
        startTime = System.nanoTime();
        BubbleSort.sortByPrice(inventory);
        endTime = System.nanoTime();
        System.out.println("Time to sort 1000 books by price (Bubble Sort): " + (endTime - startTime) + " ns");
    }
}
