/**
 * A custom object that represents an entry in an address book.
 */

public class Entry {
	private String firstName;
	private String lastName;
	private String address;
	private String zipcode;
	
	// likely features for the next iteration
	//private String id;
	//private String user-defined[];
	
	/** Instantiates an Entry with the provided values. */
	public Entry(String firstName, String lastName, String address, String zipcode) {
		setFirstName(firstName);
		setLastName(lastName);
		setAddress(address);
		setZipcode(zipcode);
	}
	/** Instantiates an Entry with blank values. */
	public Entry() {
		this("", "", "", "");
	}
	
	/** Returns a copy of the Entry. */
	public Entry clone() {
		return new Entry(getFirstName(), getLastName(), getAddress(), getZipcode());
	}
	
	/** Returns whether a class variable was successfully assigned. */
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
	
	/** Returns the value associated with each class variable. */
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