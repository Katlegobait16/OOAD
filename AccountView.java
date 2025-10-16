import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AccountView extends TabPane {
    // Customers Tab
    public TextField firstNameField, lastNameField, addressField, idNumberField;
    public TextField companyNameField, companyAddressField;
    public TextField compNameField, regNumField, contactField, compAddressField;
    public RadioButton individualRB, companyRB;
    public CheckBox employedCB;
    public Button addCustomerBtn;
    public ListView<String> customerListView;

    // Accounts Tab  
    public ListView<String> accountCustomerList;
    public TextArea accountInfoArea;
    public Button createSavingsBtn, createChequeBtn, createInvestmentBtn;

    // Transactions Tab
    public ListView<String> transCustomerList;
    public ListView<String> transAccountList;
    public TextField amountField;
    public Button depositBtn, withdrawBtn;
    public Button logoutBtn;

    // Form container
    private VBox formContainer;

    public AccountView() {
        initializeUI();
    }

    private void initializeUI() {
        setStyle("-fx-background-color: #f5f5f5;");
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        Tab customersTab = new Tab("Customers");
        customersTab.setContent(createCustomerTab());
        
        Tab accountsTab = new Tab("Accounts");
        accountsTab.setContent(createAccountTab());
        
        Tab transactionsTab = new Tab("Transactions");
        transactionsTab.setContent(createTransactionTab());

        getTabs().addAll(customersTab, accountsTab, transactionsTab);
    }

    private VBox createCustomerTab() {
        VBox tab = new VBox(15);
        tab.setPadding(new Insets(20));
        tab.setStyle("-fx-background-color: white;");

        Label header = new Label("Customer Management");
        header.setFont(Font.font("System", FontWeight.BOLD, 18));

        // Customer Type
        ToggleGroup typeGroup = new ToggleGroup();
        individualRB = new RadioButton("Individual Customer");
        companyRB = new RadioButton("Company Customer");
        individualRB.setToggleGroup(typeGroup);
        companyRB.setToggleGroup(typeGroup);
        individualRB.setSelected(true);
        
        HBox typeBox = new HBox(20, individualRB, companyRB);
        typeBox.setPadding(new Insets(10, 0, 10, 0));

        // Form container
        formContainer = new VBox();
        formContainer.setStyle("-fx-border-color: #dddddd; -fx-border-width: 1; -fx-padding: 15; -fx-background-color: #f9f9f9;");
        showIndividualForm();

        // Toggle listener
        typeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == individualRB) {
                showIndividualForm();
            } else if (newVal == companyRB) {
                showCompanyForm();
            }
        });

        // Add Customer Button
        addCustomerBtn = new Button("Add Customer");
        addCustomerBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14; -fx-padding: 8 16;");

        Label listLabel = new Label("Current Customers:");
        listLabel.setStyle("-fx-font-weight: bold;");

        customerListView = new ListView<>();
        customerListView.setPrefHeight(200);

        tab.getChildren().addAll(
            header,
            new Label("Customer Type:"),
            typeBox,
            formContainer,
            addCustomerBtn,
            new Separator(),
            listLabel,
            customerListView
        );

        return tab;
    }

    private VBox createAccountTab() {
        VBox tab = new VBox(15);
        tab.setPadding(new Insets(20));
        tab.setStyle("-fx-background-color: white;");

        Label header = new Label("Account Management");
        header.setFont(Font.font("System", FontWeight.BOLD, 18));

        // Separate customer list for Accounts tab
        Label customerLabel = new Label("Select Customer:");
        customerLabel.setStyle("-fx-font-weight: bold;");

        accountCustomerList = new ListView<>();
        accountCustomerList.setPrefHeight(150);
        
        accountInfoArea = new TextArea();
        accountInfoArea.setEditable(false);
        accountInfoArea.setPrefHeight(100);
        
        createSavingsBtn = createColoredButton("Savings Account", "#4CAF50");
        createChequeBtn = createColoredButton("Cheque Account", "#FF9800");
        createInvestmentBtn = createColoredButton("Investment Account", "#2196F3");
        
        HBox buttonBox = new HBox(10, createSavingsBtn, createChequeBtn, createInvestmentBtn);

        tab.getChildren().addAll(
            header,
            customerLabel,
            accountCustomerList,
            new Label("Account Details:"),
            accountInfoArea,
            new Label("Create Account:"),
            buttonBox
        );

        return tab;
    }

    private VBox createTransactionTab() {
        VBox tab = new VBox(15);
        tab.setPadding(new Insets(20));
        tab.setStyle("-fx-background-color: white;");

        Label header = new Label("Transaction Management");
        header.setFont(Font.font("System", FontWeight.BOLD, 18));

        // Separate customer list for Transactions tab
        Label customerLabel = new Label("Select Customer:");
        customerLabel.setStyle("-fx-font-weight: bold;");

        transCustomerList = new ListView<>();
        transCustomerList.setPrefHeight(120);
        
        Label accountLabel = new Label("Select Account:");
        accountLabel.setStyle("-fx-font-weight: bold;");
        
        transAccountList = new ListView<>();
        transAccountList.setPrefHeight(120);
        
        amountField = createTextField("Enter amount");
        
        depositBtn = createColoredButton("Deposit", "#4CAF50");
        withdrawBtn = createColoredButton("Withdraw", "#F44336");
        
        HBox transactionButtons = new HBox(15, depositBtn, withdrawBtn);

        logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: #757575; -fx-text-fill: white; -fx-font-size: 12; -fx-padding: 6 12;");

        tab.getChildren().addAll(
            header,
            customerLabel,
            transCustomerList,
            accountLabel,
            transAccountList,
            new Label("Amount:"),
            amountField,
            new Label("Actions:"),
            transactionButtons,
            new Separator(),
            logoutBtn
        );

        return tab;
    }

    private void showIndividualForm() {
        formContainer.getChildren().clear();
        
        firstNameField = createTextField("First Name");
        lastNameField = createTextField("Last Name");
        addressField = createTextField("Address");
        idNumberField = createTextField("ID Number");
        
        employedCB = new CheckBox("Employed?");
        
        companyNameField = createTextField("Company Name");
        companyAddressField = createTextField("Company Address");
        
        companyNameField.setVisible(false);
        companyAddressField.setVisible(false);
        
        employedCB.selectedProperty().addListener((obs, oldVal, newVal) -> {
            companyNameField.setVisible(newVal);
            companyAddressField.setVisible(newVal);
        });

        formContainer.getChildren().addAll(
            new Label("Individual Customer Information:"),
            new VBox(5, new Label("First Name:"), firstNameField),
            new VBox(5, new Label("Last Name:"), lastNameField),
            new VBox(5, new Label("Address:"), addressField),
            new VBox(5, new Label("ID Number:"), idNumberField),
            employedCB,
            new VBox(5, new Label("Company Name:"), companyNameField),
            new VBox(5, new Label("Company Address:"), companyAddressField)
        );
    }

    private void showCompanyForm() {
        formContainer.getChildren().clear();
        
        compNameField = createTextField("Company Name");
        regNumField = createTextField("Registration Number");
        contactField = createTextField("Contact Person");
        compAddressField = createTextField("Company Address");

        formContainer.getChildren().addAll(
            new Label("Company Customer Information:"),
            new VBox(5, new Label("Company Name:"), compNameField),
            new VBox(5, new Label("Registration Number:"), regNumField),
            new VBox(5, new Label("Contact Person:"), contactField),
            new VBox(5, new Label("Company Address:"), compAddressField)
        );
    }

    private TextField createTextField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setStyle("-fx-padding: 6; -fx-font-size: 13;");
        return field;
    }

    private Button createColoredButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 12; -fx-padding: 8 12;");
        return button;
    }

    public boolean isIndividualSelected() { 
      return individualRB.isSelected(); 
    }
    
    public boolean isEmployed() { 
      return employedCB.isSelected(); 
    }
    
    public String getFirstName() { 
      return firstNameField != null ? firstNameField.getText().trim() : ""; 
    }
    
    public String getLastName() { 
      return lastNameField != null ? lastNameField.getText().trim() : "";  
    }
    
    public String getAddress() { 
      return addressField != null ? addressField.getText().trim() : ""; 
    }
    
    public String getIdNumber() { 
      return idNumberField != null ? idNumberField.getText().trim() : ""; 
    }
    
    public String getCompanyName() { 
      return companyNameField != null ? companyNameField.getText().trim() : ""; 
    }
    
    public String getCompanyAddress() { 
      return companyAddressField != null ? companyAddressField.getText().trim() : ""; 
    }
    
    public String getCompName() { 
      return compNameField != null ? compNameField.getText().trim() : ""; 
    }
    
    public String getRegNum() { 
      return regNumField != null ? regNumField.getText().trim() : ""; 
    }
    
    public String getContact() { 
      return contactField != null ? contactField.getText().trim() : ""; 
    }
    public String getCompAddress() { 
      return compAddressField != null ? compAddressField.getText().trim() : ""; 
    }
    
    public String getAmount() { 
      return amountField.getText().trim(); 
    }

    public void clearCustomerFields() {
        if (firstNameField != null) firstNameField.clear();
        if (lastNameField != null) lastNameField.clear();
        if (addressField != null) addressField.clear();
        if (idNumberField != null) idNumberField.clear();
        if (companyNameField != null) companyNameField.clear();
        if (companyAddressField != null) companyAddressField.clear();
        if (compNameField != null) compNameField.clear();
        if (regNumField != null) regNumField.clear();
        if (contactField != null) contactField.clear();
        if (compAddressField != null) compAddressField.clear();
    }

    public void clearAmount() { amountField.clear(); }
}