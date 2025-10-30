public class InvestmentAccount extends Account implements InterestBearing, Withdraw {
    private double minimumBalance = 500.0;

    public InvestmentAccount(long accountNumber, String branch, Customer owner) {
        super(accountNumber, branch, owner);
    }

    // Default constructor for file loading
    public InvestmentAccount() {
        super();
        this.minimumBalance = 500.0;
    }

    @Override
    public void payInterest() {
        double interest = balance * 0.05;
        balance += interest;
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance - amount >= minimumBalance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(double minimumBalance) {
        this.minimumBalance = minimumBalance;
    }
}