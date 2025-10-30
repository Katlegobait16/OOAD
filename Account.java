public abstract class Account {
    protected long accountNumber;
    protected double balance;
    protected String branch;
    protected Customer owner;

    public Account(long accountNumber, String branch, Customer owner) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.branch = branch;
        this.owner = owner;
    }

    // Default constructor for file loading
    public Account() {
        this.accountNumber = 0;
        this.balance = 0.0;
        this.branch = "";
        this.owner = null;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}