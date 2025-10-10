public class ChequeAccount extends Account implements Withdraw{
    private String companyName;
    private String companyAddress;

    public ChequeAccount(String accountNumber, String branch, IndividualCustomer owner) {
        super(accountNumber,branch, owner);
        if(!owner.isEmployed()){
            System.out.println("Only employed individuals can open a Cheque Account");
        }
        this.companyName = owner.getCompanyName();
        this.companyAddress = owner.getCompanyAddress();
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

    public String getCompanyAddress(){
        return companyAddress;
    }
}