import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.*;

public class MainController {
    @FXML private RadioButton individualRadio, companyRadio;
    @FXML private VBox individualForm, companyForm;
    @FXML private TextField firstNameField, lastNameField, addressField, idNumberField;
    @FXML private TextField companyNameField, companyAddressField;
    @FXML private TextField compNameField, regNumberField, contactPersonField, compAddressField;
    @FXML private CheckBox employedCheckbox;
    @FXML private VBox employmentInfo;
    @FXML private Button addIndividualCustomerButton, addCompanyCustomerButton;
    @FXML private ListView<String> customerListView;
    @FXML private Label totalCustomersLabel, individualCountLabel, companyCountLabel;
    @FXML private TextArea customerDetailsArea;
    @FXML private ListView<String> accountCustomerList;
    @FXML private TextArea accountInfoArea;
    @FXML private Button savingsButton, chequeButton, investmentButton;
    @FXML private ListView<String> transCustomerList, transAccountList;
    @FXML private TextField amountField;
    @FXML private Button depositButton, withdrawButton, logoutButton;
    
    private List<Customer> customers = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();
    private BankingGUI mainApp;
    private int customerCounter = 1;
    private long accountCounter = 1000;

    public void setMainApp(BankingGUI mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        setupCustomerTab();
        setupAccountsTab();
        setupTransactionsTab();
    }

    private void setupCustomerTab() {
        individualForm.setVisible(true);
        companyForm.setVisible(false);
        employmentInfo.setVisible(false);
        
        individualRadio.selectedProperty().addListener((obs, oldVal, newVal) -> {
            individualForm.setVisible(newVal);
            companyForm.setVisible(!newVal);
        });
        
        companyRadio.selectedProperty().addListener((obs, oldVal, newVal) -> {
            individualForm.setVisible(!newVal);
            companyForm.setVisible(newVal);
        });
        
        employedCheckbox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            employmentInfo.setVisible(newVal);
        });
        
        addIndividualCustomerButton.setOnAction(e -> addCustomer());
        addCompanyCustomerButton.setOnAction(e -> addCustomer());
        
        customerListView.getSelectionModel().selectedItemProperty()
            .addListener((obs, oldVal, newVal) -> showCustomerDetails(newVal));
    }

    private void setupAccountsTab() {
        savingsButton.setOnAction(e -> createAccount("Savings"));
        chequeButton.setOnAction(e -> createAccount("Cheque"));
        investmentButton.setOnAction(e -> createAccount("Investment"));
        
        accountCustomerList.getSelectionModel().selectedItemProperty()
            .addListener((obs, oldVal, newVal) -> updateAccountInfo());
    }

    private void setupTransactionsTab() {
        depositButton.setOnAction(e -> doTransaction("deposit"));
        withdrawButton.setOnAction(e -> doTransaction("withdraw"));
        logoutButton.setOnAction(e -> handleLogout());
        
        transCustomerList.getSelectionModel().selectedItemProperty()
            .addListener((obs, oldVal, newVal) -> updateTransactionAccounts());
    }

    private void addCustomer() {
        try {
            Customer customer = createCustomerFromForm();
            customers.add(customer);
            updateAllDisplays();
            clearCustomerFields();
            showAlert("Success", "Customer added: " + customer.getName());
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    private Customer createCustomerFromForm() {
        if (individualRadio.isSelected()) {
            return createIndividualCustomer();
        } else {
            return createCompanyCustomer();
        }
    }

    private Customer createIndividualCustomer() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String address = addressField.getText().trim();
        String idNumber = idNumberField.getText().trim();
        
        if (firstName.isEmpty() || lastName.isEmpty()) {
            throw new IllegalArgumentException("First and last name are required");
        }
        
        String customerID = "IND" + customerCounter++;
        
        if (employedCheckbox.isSelected()) {
            String companyName = companyNameField.getText().trim();
            String companyAddress = companyAddressField.getText().trim();
            return new IndividualCustomer(customerID, address, firstName, lastName, idNumber, companyName, companyAddress);
        } else {
            return new IndividualCustomer(customerID, address, firstName, lastName, idNumber);
        }
    }

    private Customer createCompanyCustomer() {
        String companyName = compNameField.getText().trim();
        String regNumber = regNumberField.getText().trim();
        String contactPerson = contactPersonField.getText().trim();
        String address = compAddressField.getText().trim();
        
        if (companyName.isEmpty()) {
            throw new IllegalArgumentException("Company name is required");
        }
        
        String customerID = "COMP" + customerCounter++;
        return new CompanyCustomer(customerID, address, companyName, regNumber, contactPerson);
    }

    private void createAccount(String accountType) {
        try {
            Customer customer = getSelectedCustomerFromAccounts();
            if (customer == null) {
                showAlert("Error", "Please select a customer first");
                return;
            }
            
            Account account = createAccountOfType(accountType, customer);
            accounts.add(account);
            updateAllDisplays();
            showAlert("Success", accountType + " account #" + account.getAccountNumber() + " created for " + customer.getName());
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    private Account createAccountOfType(String type, Customer customer) {
        long accountNumber = accountCounter++;
        
        switch (type) {
            case "Savings":
                SavingsAccount savings = new SavingsAccount(accountNumber, "Main Branch", customer);
                savings.deposit(0);
                return savings;
            case "Cheque":
                if (!(customer instanceof IndividualCustomer)) {
                    throw new IllegalArgumentException("Cheque accounts only for individual customers");
                }
                IndividualCustomer indCustomer = (IndividualCustomer) customer;
                if (!indCustomer.isEmployed()) {
                    throw new IllegalArgumentException("Only employed individuals can open cheque accounts");
                }
                ChequeAccount cheque = new ChequeAccount(accountNumber, "Main Branch", indCustomer);
                cheque.deposit(0);
                return cheque;
            case "Investment":
                InvestmentAccount investment = new InvestmentAccount(accountNumber, "Investment Branch", customer);
                investment.deposit(500.0);
                return investment;
            default:
                throw new IllegalArgumentException("Unknown account type");
        }
    }

    private void doTransaction(String transactionType) {
        try {
            double amount = validateAmount(amountField.getText());
            Account account = getSelectedAccountForTransaction();
            
            if ("deposit".equals(transactionType)) {
                processDeposit(account, amount);
            } else if ("withdraw".equals(transactionType)) {
                processWithdrawal(account, amount);
            }
            
            amountField.clear();
            updateAllDisplays();
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    private double validateAmount(String amountText) {
        if (amountText.isEmpty()) {
            throw new IllegalArgumentException("Please enter an amount");
        }
        
        try {
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be positive");
            }
            return amount;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a valid number");
        }
    }

    private void processDeposit(Account account, double amount) {
        account.deposit(amount);
        showAlert("Success", String.format("Deposited P%.2f to account #%d\nNew Balance: P%.2f", 
            amount, account.getAccountNumber(), account.getBalance()));
    }

    private void processWithdrawal(Account account, double amount) {
        if (!(account instanceof Withdraw)) {
            throw new IllegalArgumentException("This account type does not support withdrawals");
        }
        
        Withdraw withdrawable = (Withdraw) account;
        if (withdrawable.withdraw(amount)) {
            showAlert("Success", String.format("Withdrew P%.2f from account #%d\nNew Balance: P%.2f", 
                amount, account.getAccountNumber(), account.getBalance()));
        } else {
            throw new IllegalArgumentException("Insufficient funds or below minimum balance");
        }
    }

    private void handleLogout() {
        String summary = generateSessionSummary();
        showAlert("Session Summary", summary);
        mainApp.showLoginScreen();
    }

    private String generateSessionSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("=== BANKING SESSION SUMMARY ===\n\n");
        summary.append("Customers Created: ").append(customers.size()).append("\n");
        summary.append("Accounts Created: ").append(accounts.size()).append("\n");
        summary.append("Total Balance: P").append(String.format("%.2f", calculateTotalBalance())).append("\n\n");
        
        summary.append("CUSTOMERS:\n");
        for (Customer customer : customers) {
            summary.append("• ").append(customer.getName()).append(" (").append(customer.getCustomerID()).append(")\n");
        }
        
        summary.append("\nACCOUNTS:\n");
        for (Account account : accounts) {
            summary.append("• #").append(account.getAccountNumber())
                   .append(" - ").append(account.getClass().getSimpleName())
                   .append(" - P").append(String.format("%.2f", account.getBalance()))
                   .append(" - ").append(account.getOwner().getName()).append("\n");
        }
        
        return summary.toString();
    }

    private double calculateTotalBalance() {
        double total = 0;
        for (Account account : accounts) {
            total += account.getBalance();
        }
        return total;
    }

    private void showCustomerDetails(String selectedCustomer) {
        if (selectedCustomer == null) {
            customerDetailsArea.setText("Select a customer to view details");
            return;
        }
        
        Customer customer = findCustomerByDisplayText(selectedCustomer);
        if (customer != null) {
            StringBuilder details = new StringBuilder();
            
            if (customer instanceof IndividualCustomer) {
                IndividualCustomer ind = (IndividualCustomer) customer;
                details.append("Type: Individual Customer\n")
                      .append("Name: ").append(ind.getName()).append("\n")
                      .append("ID: ").append(ind.getCustomerID()).append("\n")
                      .append("Address: ").append(ind.getAddress()).append("\n")
                      .append("ID Number: ").append(ind.getIdNum()).append("\n")
                      .append("Employed: ").append(ind.isEmployed() ? "Yes" : "No");
            } else if (customer instanceof CompanyCustomer) {
                CompanyCustomer comp = (CompanyCustomer) customer;
                details.append("Type: Company Customer\n")
                      .append("Company: ").append(comp.getName()).append("\n")
                      .append("ID: ").append(comp.getCustomerID()).append("\n")
                      .append("Address: ").append(comp.getAddress()).append("\n")
                      .append("Registration: ").append(comp.getRegistrationNum()).append("\n")
                      .append("Contact: ").append(comp.getContactPerson());
            }
            
            customerDetailsArea.setText(details.toString());
        }
    }

    private void updateAllDisplays() {
        updateCustomerLists();
        updateCustomerStatistics();
        updateAccountInfo();
        updateTransactionAccounts();
    }

    private void updateCustomerLists() {
        customerListView.getItems().clear();
        accountCustomerList.getItems().clear();
        transCustomerList.getItems().clear();
        
        for (Customer customer : customers) {
            String displayText = customer.getName() + " (" + customer.getCustomerID() + ")";
            customerListView.getItems().add(displayText);
            accountCustomerList.getItems().add(displayText);
            transCustomerList.getItems().add(displayText);
        }
    }

    private void updateCustomerStatistics() {
        int individualCount = 0;
        int companyCount = 0;
        
        for (Customer customer : customers) {
            if (customer instanceof IndividualCustomer) {
                individualCount++;
            } else if (customer instanceof CompanyCustomer) {
                companyCount++;
            }
        }
        
        totalCustomersLabel.setText(String.valueOf(customers.size()));
        individualCountLabel.setText(String.valueOf(individualCount));
        companyCountLabel.setText(String.valueOf(companyCount));
    }

    private void updateAccountInfo() {
        Customer customer = getSelectedCustomerFromAccounts();
        if (customer == null) {
            accountInfoArea.setText("Please select a customer to view accounts");
            return;
        }
        
        StringBuilder info = new StringBuilder();
        info.append("Accounts for ").append(customer.getName()).append(":\n\n");
        
        boolean hasAccounts = false;
        for (Account account : accounts) {
            if (account.getOwner().equals(customer)) {
                info.append("Account #").append(account.getAccountNumber())
                    .append(" (").append(account.getClass().getSimpleName()).append(")\n")
                    .append("Balance: P").append(String.format("%.2f", account.getBalance()))
                    .append("\n------------------------\n");
                hasAccounts = true;
            }
        }
        
        if (!hasAccounts) {
            info.append("No accounts found for this customer");
        }
        
        accountInfoArea.setText(info.toString());
    }

    private void updateTransactionAccounts() {
        transAccountList.getItems().clear();
        Customer customer = getSelectedCustomerFromTransactions();
        
        if (customer != null) {
            for (Account account : accounts) {
                if (account.getOwner().equals(customer)) {
                    transAccountList.getItems().add(
                        account.getAccountNumber() + " - " + account.getClass().getSimpleName() + " - P" + account.getBalance()
                    );
                }
            }
        }
    }

    private Customer getSelectedCustomerFromAccounts() {
        String selected = accountCustomerList.getSelectionModel().getSelectedItem();
        return findCustomerByDisplayText(selected);
    }

    private Customer getSelectedCustomerFromTransactions() {
        String selected = transCustomerList.getSelectionModel().getSelectedItem();
        return findCustomerByDisplayText(selected);
    }

    private Customer findCustomerByDisplayText(String displayText) {
        if (displayText == null) return null;
        
        String customerID = displayText.substring(displayText.lastIndexOf("(") + 1, displayText.lastIndexOf(")"));
        for (Customer customer : customers) {
            if (customer.getCustomerID().equals(customerID)) {
                return customer;
            }
        }
        return null;
    }

    private Account getSelectedAccountForTransaction() {
        String selected = transAccountList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            throw new IllegalArgumentException("Please select an account");
        }
        
        String accountNumberStr = selected.split(" - ")[0];
        long accountNumber = Long.parseLong(accountNumberStr);
        
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        throw new IllegalArgumentException("Selected account not found");
    }

    private void clearCustomerFields() {
        firstNameField.clear();
        lastNameField.clear();
        addressField.clear();
        idNumberField.clear();
        companyNameField.clear();
        companyAddressField.clear();
        compNameField.clear();
        regNumberField.clear();
        contactPersonField.clear();
        compAddressField.clear();
        employedCheckbox.setSelected(false);
        employmentInfo.setVisible(false);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}