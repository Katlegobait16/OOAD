import java.io.*;
import java.util.*;

public class CustomerRepository {
    private static final String CUSTOMERS_FILE = "customers.txt";
    
    public void saveCustomers(List<Customer> customers) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CUSTOMERS_FILE))) {
            for (Customer customer : customers) {
                if (customer instanceof IndividualCustomer) {
                    IndividualCustomer ind = (IndividualCustomer) customer;
                    String[] nameParts = ind.getName().split(" ");
                    String firstName = nameParts.length > 0 ? nameParts[0] : "";
                    String lastName = nameParts.length > 1 ? nameParts[1] : "";
                    
                    writer.println("INDIVIDUAL," + 
                        customer.getCustomerID() + "," +
                        (customer.getAddress() != null ? customer.getAddress() : "") + "," +
                        firstName + "," +
                        lastName + "," +
                        (ind.getIdNum() != null ? ind.getIdNum() : "") + "," +
                        (ind.getCompanyName() != null ? ind.getCompanyName() : "") + "," +
                        (ind.getCompanyAddress() != null ? ind.getCompanyAddress() : ""));
                } else if (customer instanceof CompanyCustomer) {
                    CompanyCustomer comp = (CompanyCustomer) customer;
                    writer.println("COMPANY," + 
                        customer.getCustomerID() + "," +
                        (customer.getAddress() != null ? customer.getAddress() : "") + "," +
                        comp.getName() + "," +
                        (comp.getRegistrationNum() != null ? comp.getRegistrationNum() : "") + "," +
                        (comp.getContactPerson() != null ? comp.getContactPerson() : ""));
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving customers: " + e.getMessage());
        }
    }
    
    public List<Customer> loadCustomers() {
        List<Customer> customers = new ArrayList<>();
        File file = new File(CUSTOMERS_FILE);
        if (!file.exists()) return customers;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 4) {
                    if ("INDIVIDUAL".equals(parts[0])) {
                        IndividualCustomer customer = new IndividualCustomer();
                        customer.setCustomerID(parts[1]);
                        customer.setAddress(parts[2]);
                        customer.setFirstName(parts[3]);
                        customer.setSurname(parts[4]);
                        customer.setIdNumber(parts[5]);
                        
                        if (!parts[6].isEmpty()) {
                            customer.setCompanyName(parts[6]);
                            customer.setCompanyAddress(parts[7]);
                        }
                        customers.add(customer);
                    } else if ("COMPANY".equals(parts[0])) {
                        CompanyCustomer customer = new CompanyCustomer();
                        customer.setCustomerID(parts[1]);
                        customer.setAddress(parts[2]);
                        customer.setCompanyName(parts[3]);
                        customer.setRegistrationNum(parts[4]);
                        customer.setContactPerson(parts[5]);
                        customers.add(customer);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading customers: " + e.getMessage());
        }
        return customers;
    }
}