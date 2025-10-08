public class ChequeAccount extends Account implements Withdraw{
    private String companyName;

    public ChequeAccount(String accountNumber, String branch,String customer, String companyName, Customer owner) {
        super(accountNumber,branch, owner);
        this.companyName = companyName;
    }

    @Override
    public boolean withdraw(double amount){
        if(balance >=amount){
            balance -= amount;
            return true;
        }

        return false;
    }

    public String getCompanyName(){
        return companyName;
    }
}