public class Customer {

    //attributes
    //name, (age), address
    private String name;
    private String address;
//    private Book book;


    //constructor
    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
//        this.book = book;
    }

    //method
    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public String toString(){
        return "\nCustomer: " + name + "\nAddress: " + address;
    }

}
