abstract class Account{
    protected String accountName;
    protected double balance;
    protected String branch;
    protected Customer owner;

    public Account(String accountName, String branch, Customer owner){
        this.accountName = accountName;
        this.balance = 0.0;
        this.branch = branch;
        this.owner = owner;
    }

    public Customer getOwner(){
        return owner;
    }

    public void deposit(double amount){
        balance += amount;
    }

    public double getBalance(){
        return balance;
    }

    public String getAccountName(){
        return accountName;
    }

    public String getBranch(){
        return branch;
    }
    
}