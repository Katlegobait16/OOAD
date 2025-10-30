public class SavingsAccount extends Account implements InterestBearing {
    public SavingsAccount(long accountNumber, String branch, Customer owner) {
        super(accountNumber, branch, owner);
    }

    // Default constructor for file loading
    public SavingsAccount() {
        super();
    }

    @Override
    public void payInterest() {
        double interest = balance * 0.02;
        balance += interest;
    }
}