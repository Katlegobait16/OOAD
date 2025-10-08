public class IndividualCustomer extends Customer{
    private String firstName;
    private String surname;
    private String idNumber;

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
}