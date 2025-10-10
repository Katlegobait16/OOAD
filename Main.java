public class Main
{
    public static void main(String[] args){
            IndividualCustomer cust = new IndividualCustomer("C001", "Gaborone", "Katlego", "Baitirile", "12345678");
            IndividualCustomer cust2 = new IndividualCustomer("C002", "Francistown", "Kabo", "Baitirile", "90123458", "Zenith Tuning", "Plot 125, Airport Road");

            SavingsAccount acc = new SavingsAccount(1234567890123L, "Main Branch", cust);
            ChequeAccount acc2 = new ChequeAccount(987654321098L, "Main Branch", cust2);

            //savings account
            acc.deposit(2000);
            acc.payInterest();

            //cheque account 
            acc2.deposit(5000);

            //cheque account
            System.out.println("Cheque Account Owner: " + cust2.getName());
            System.out.println("Employer: " + cust2.getCompanyName());
            System.out.println("Employer Address: " + cust2.getCompanyAddress());
            System.out.println("Balance:" + acc2.getBalance());

            //savings account
            System.out.println("Customer:" + cust.getName());
            System.out.println("Account owner:" + acc.getOwner().getName());
            System.out.println("Balance after interest:" + acc.getBalance());
    }
}