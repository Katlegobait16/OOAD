import java.io.*;
import java.util.*;

public class TransactionRepository {
    private static final String TRANSACTIONS_FILE = "transactions.txt";
    
    public void saveTransaction(long accountNumber, String type, double amount, String description) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TRANSACTIONS_FILE, true))) {
            writer.println(accountNumber + "," + 
                type + "," + amount + "," + 
                description + "," + 
                System.currentTimeMillis());
        } catch (IOException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
        }
    }
    
    public List<String> getTransactionHistory(long accountNumber) {
        List<String> transactions = new ArrayList<>();
        File file = new File(TRANSACTIONS_FILE);
        if (!file.exists()) return transactions;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && Long.parseLong(parts[0]) == accountNumber) {
                    transactions.add(parts[1] + ": P" + parts[2] + " - " + parts[3]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
        }
        return transactions;
    }
}