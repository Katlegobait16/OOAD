abstract class Account{
    protected long accountNumber;
    protected double balance;
    protected String branch;
    protected Customer owner;

    public Account(long accountNumber, String branch, Customer owner){
        this.accountNumber = accountNumber;
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

    public long getAccountNumber(){
        return accountNumber;
    }

    public String getBranch(){
        return branch;
    }
    
}