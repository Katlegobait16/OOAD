import java.util.ArrayList;
import java.util.List;

abstract class Customer{
    protected String customerID;
    protected String address;
    protected List<Account> accounts = new ArrayList<>();

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
    
    public void addAccount(Account account) {
        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}