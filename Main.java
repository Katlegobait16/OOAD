public class Main
{
    public static void main(String[] args){
            IndividualCustomer cust = new IndividualCustomer("C001", "Gaborone", "Katlego", "Baitirile", "12345678");
            SavingsAccount acc = new SavingsAccount("A200", "Main Branch", cust);

            acc.deposit(2000);
            acc.payInterest();

            System.out.println("Customer:" + cust.getName());
            System.out.println("Account owner:" + acc.getOwner().getName());
            System.out.println("Balance after interest:" + acc.getBalance());
    }
}