public class CompanyCustomer extends Customer {
    private String companyName;
    private String registrationNum;
    private String contactPerson;

    public CompanyCustomer() {
        super();
    }

    public CompanyCustomer(String customerID, String address, String companyName, String registrationNum, String contactPerson) {
        super(customerID, address);
        this.companyName = companyName;
        this.registrationNum = registrationNum;
        this.contactPerson = contactPerson;
    }

    @Override
    public String getName() {
        return companyName;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    // Setters for file loading
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}