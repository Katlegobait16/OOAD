public class IndividualCustomer extends Customer {
    private String firstName;
    private String surname;
    private String idNumber;
    private String companyName;
    private String companyAddress;

    // Default constructor for file loading
    public IndividualCustomer() {
        super();
        this.firstName = "";
        this.surname = "";
        this.idNumber = "";
        this.companyName = "";
        this.companyAddress = "";
    }

    // Employed individuals
    public IndividualCustomer(String customerID, String address, String firstName, String surname, String idNumber, String companyName, String companyAddress) {
        super(customerID, address);
        this.firstName = firstName;
        this.surname = surname;
        this.idNumber = idNumber;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
    }

    // Unemployed individuals
    public IndividualCustomer(String customerID, String address, String firstName, String surname, String idNumber) {
        super(customerID, address);
        this.firstName = firstName;
        this.surname = surname;
        this.idNumber = idNumber;
    }

    @Override
    public String getName() {
        return firstName + " " + surname;
    }

    public String getIdNum() {
        return idNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public boolean isEmployed() {
        return companyName != null && !companyName.isEmpty();
    }

    public void setCompany(String companyName, String companyAddress) {
        this.companyName = companyName;
        this.companyAddress = companyAddress;
    }

    // Setters for file loading
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }
}