abstract class Customer{
    protected String customerID;
    protected String address;

    public Customer(String customerID, String address){
        this.customerID = customerID;
        this.address = address;
    }

    public abstract String getName();

    public void setAddress(String address){
        this.address = address;
    }

    public void setCustomerID(String customerID){
        this.customerID = customerID;
    }

    public String getCustomerID(){
        return customerID;
    }

    public String getAddress(){
        return address;
    }
}