import java.io.*;
import java.util.*;

public class AccountRepository {
    private static final String ACCOUNTS_FILE = "accounts.txt";
    
    public void saveAccounts(List<Account> accounts, List<Customer> customers) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (Account account : accounts) {
                String type = account.getClass().getSimpleName();
                String customerID = account.getOwner().getCustomerID();
                
                writer.println(type + "," + 
                    account.getAccountNumber() + "," +
                    customerID + "," +
                    account.getBranch() + "," +
                    account.getBalance());
            }
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }
    
    public List<Account> loadAccounts(List<Customer> customers) {
        List<Account> accounts = new ArrayList<>();
        File file = new File(ACCOUNTS_FILE);
        if (!file.exists()) return accounts;
        
        Map<String, Customer> customerMap = new HashMap<>();
        for (Customer customer : customers) {
            customerMap.put(customer.getCustomerID(), customer);
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String type = parts[0];
                    long accountNumber = Long.parseLong(parts[1]);
                    String customerID = parts[2];
                    String branch = parts[3];
                    double balance = Double.parseDouble(parts[4]);
                    
                    Customer owner = customerMap.get(customerID);
                    if (owner != null) {
                        Account account = createAccount(type, accountNumber, branch, owner, balance);
                        if (account != null) {
                            accounts.add(account);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading accounts: " + e.getMessage());
        }
        return accounts;
    }
    
    private Account createAccount(String type, long accountNumber, String branch, Customer owner, double balance) {
        switch (type) {
            case "SavingsAccount":
                SavingsAccount savings = new SavingsAccount(accountNumber, branch, owner);
                savings.setBalance(balance);
                return savings;
            case "ChequeAccount":
                if (owner instanceof IndividualCustomer) {
                    ChequeAccount cheque = new ChequeAccount(accountNumber, branch, (IndividualCustomer) owner);
                    cheque.setBalance(balance);
                    return cheque;
                }
                break;
            case "InvestmentAccount":
                InvestmentAccount investment = new InvestmentAccount(accountNumber, branch, owner);
                investment.setBalance(balance);
                return investment;
        }
        return null;
    }
}