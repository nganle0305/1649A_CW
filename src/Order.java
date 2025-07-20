public class Order {

    //attributes
    private int orderID;
    private String status = "Delivering";
    private static int autoIncrement = 1;
    private Customer customer;
    private ArrayListADT<Book> bookList;

    //constructors
    public Order(Customer customer, ArrayListADT<Book> bookList) {
        this.orderID = autoIncrement++;
        this.customer = customer;
        this.bookList = bookList;
    }

    //method

    public void setOrderID() {
        this.orderID = autoIncrement++;
    }

    public int getOrderID() {
        return this.orderID;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public ArrayListADT<Book> getBookList() {
        return bookList;
    }

    public void setBookList(ArrayListADT<Book> bookList) {
        this.bookList = bookList;
    }

    public double totalPrice() {
        double total = 0.0;
        for (int i = 0; i < bookList.size(); i++) {
            Book bookIndex = bookList.get(i);
            total += bookIndex.getPrice() * bookIndex.getQuantity();
        }
        return total;
    }


    public String toString() {
        StringBuilder bookInformation = new StringBuilder();
        bookInformation.append("Order ID: ").append(orderID).append("\n");
        bookInformation.append("Status: ").append(status).append("\n");
        bookInformation.append(customer).append("\n"); // Show name and address
        for (int i = 0; i < bookList.size(); i++) {
            bookInformation.append(bookList.get(i)).append("\n");
        }
        bookInformation.append("Total: ").append(String.format("%.2f", totalPrice()));
        return bookInformation.toString();
    }
    public String toHistoryString() {
        return "Order ID: " + orderID + " | Status: " + status;
    }

}

