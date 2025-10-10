public class IndividualCustomer extends Customer{
    private String firstName;
    private String surname;
    private String idNumber;
    private String companyName;
    private String companyAddress;

    //employed individuals 
    public IndividualCustomer(String customerID, String address, String firstName, String surname, String idNumber, String companyName, String companyAddress){
        super(customerID,address);
        this.firstName = firstName;
        this.surname = surname;
        this.idNumber = idNumber;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
    }

    //unemployed individuals
    public IndividualCustomer(String customerID, String address, String firstName, String surname, String idNumber){
        super(customerID,address);
        this.firstName = firstName;
        this.surname = surname;
        this.idNumber = idNumber;
    }


    @Override
    public String getName(){
        return firstName + " " + surname;
    }

    public String getIdNum(){
        return idNumber;
    }

    public String getCompanyName(){
        return companyName;
    }

    public String getCompanyAddress(){
        return companyAddress;
    }

    public boolean isEmployed(){
        return companyName != null && !companyName.isEmpty();
    }

    public void setCompany(String companyName, String companyAddress) {
        this.companyName = companyName;
        this.companyAddress = companyAddress;
    }
}