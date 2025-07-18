public class Book {

    //attributes
    //author, title, price, quantity
    private String author;
    private String title;
    private double price;
    private int quantity;
    private int stock;

    //constructors
    public Book(String author, String title, double price, int quantity, int stock) {
        this.author = author;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.stock = stock;
    }

    // method / functions
    public void setAuthor(String author){
        this.author = author;
    }
    public String getAuthor(){
        return this.author;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public double getPrice() {
        return this.price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getQuantity() {
        return this.quantity;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    public int getStock() {
        return this.stock;
    }


    public String toStockString(){
        return "Book: " + title + ", Author: " + author + ", Price: " + price + ", Stock: " + stock;
    }

    public String toString(){
        return "Book: " + title + ", Author: " + author + ", Price: " + price + ", Quantity: " + quantity;
    }

}

