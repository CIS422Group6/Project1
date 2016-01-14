
public class Entry {
	// class variables
	private String firstName;
	private String lastName;
	private String address;
	private String zipcode;
	
	//private String id;
	//private String user-defined[];
	
	/** Creates a new instance of an entry with the supplied arguments */
	public Entry(String firstName, String lastName, String address, String zipcode) {
		setFirstName(firstName);
		setLastName(lastName);
		setAddress(address);
		setZipcode(zipcode);
	}
	public Entry() {
		this("", "", "", "");
	}
	
	// quick clone method
	public Entry clone() {
		return new Entry(getFirstName(), getLastName(), getAddress(), getZipcode());
	}
	
	/** Returns whether a class variable was successfully assigned */
	public boolean setFirstName(String firstName) {
		this.firstName = firstName;
		return true;
	}
	public boolean setLastName(String lastName) {
		this.lastName = lastName;
		return true;
	}
	public boolean setAddress(String address) {
		this.address = address;
		return true;
	}
	public boolean setZipcode(String zipcode) {
		this.zipcode = zipcode;
		return true;
	}
	
	/** Returns the value associated with each class variable */
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
