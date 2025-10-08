public class InvestmentAccount extends Account implements InterestBearing, Withdraw
{
    private double minimumBlance = 500.0;

    public InvestmentAccount(String accountNumber, String branch, Customer owner){
        super(accountNumber, branch, owner);
    }

    @Override
    public void payInterest(){
        double interest = balance * 0.05;
        balance += interest;
    }

    @Override
    public boolean withdraw(double amount){
        if(balance - amount >= minimumBlance){
            balance -= amount;
            return true;
        }

        return false;
    }
}