// basic person object to demonstrate gui functionality
public class Person {
	private String firstName;
	private String lastName;
	private String address;
	private String zipcode;
	
	// constructors
	public Person(String firstName, String lastName, String address, String zipcode) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.zipcode = zipcode;
	}
	public Person() {
		this("", "", "", "");
	}
	
	// setters
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	// getters
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getAddress() {
		return address;
	}
	public String getZipcode() {
		return zipcode;
	}
}
