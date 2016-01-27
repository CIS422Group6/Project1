
import java.util.ArrayList;

/**
 * A custom object that represents an entry in an address book.
 */

public class Entry {
	private String firstName, lastName,
		delivery,
		second,
		city, state, zipcode,
		phone, email;
	
	// formatted address
	//private String address;
	
	/* likely features for next iteration
	private String user-defined[];
	*/
	
	/** Constructor for an Entry with given values. */
	public Entry(String firstName, String lastName,
			String delivery,
			String second,
			String city, String state, String zipcode,
			String phone, String email) {
		setFirstName(firstName);
		setLastName(lastName);
		setDelivery(delivery);
		setSecond(second);
		setCity(city);
		setState(state);
		setZipcode(zipcode);
		setPhone(phone);
		setEmail(email);
		//this.address = getAddress();
	}

	/** Constructor for an Entry with blank values. */
	public Entry() {
		this("", "", "", "", "", "", "", "", "");
	}
	
	/** Returns a copy of the Entry. */
	public Entry clone() {
		return new Entry(getFirstName(), getLastName(),
				getDelivery(),
				getSecond(),
				getCity(), getState(), getZipcode(),
				getPhone(), getEmail());
	}
	
	/** Returns the formatted address of an entry. */
	public String getAddress() {
		String address = delivery;
		if (getSecond() != "") {
			address += "\n" + second;
		}
		address += "\n" + city + ", " + state + " " + zipcode;
		return address;
	}
	
	/** Returns whether a class variable was successfully assigned. */
	public boolean setFirstName(String firstName) {
		this.firstName = firstName;
		if(firstName.matches("[a-zA-Z]+")){
			return true;
		}
		return false;
	}
	public boolean setLastName(String lastName) {
		this.lastName = lastName;
		if(lastName.matches("[a-zA-Z]+")){
			return true;
		}
		return false;
	}
	public boolean setDelivery(String delivery) {
		this.delivery = delivery;
		return true;
	}
	public boolean setSecond(String second) {
		this.second = second;
		return true;
	}
	public boolean setCity(String city) {
		this.city = city;
		if(city.matches("[a-zA-Z]+")){
			return true;
		}
		return false;
	}
	public boolean setState(String state) {
		this.state = state;
		if(state.matches("[a-zA-Z]+")){
			return true;
		}
		return false;
	}
	public boolean setZipcode(String zipcode) {
		this.zipcode = zipcode;
		String[] parts = zipcode.split("-");
		if((zipcode.length()==5)&&(zipcode.matches("\\d+"))){
			
			return true;	
		}else if((zipcode.length() == 10)&&(parts.length>1)&&
				(parts[0].length() == 5)&&(parts[1].length()==4)&&
				(parts[0].matches("\\d+"))&&(parts[1].matches("\\d+"))){
			
			return true;	
		}else{
			
			return false;
		}
		
	}
	public boolean setPhone(String phone) {
		this.phone = phone;
		if(((phone.length()==10)&&(phone.matches("\\d+")))||(phone.matches("[0-9]{3}+-[0-9]{3}+-[0-9]{4}"))){
        	return true;
        }
			
		return false;
	}
	public boolean setEmail(String email) {
		this.email = email;
		
		if(email.matches("[0-9a-zA-Z._]+@[0-9a-zA-Z.-]+\\.[a-zA-Z]{2,3}")){
			return true;
		}else{
			return false;
		}
		
	}
	
	
	
	/** Returns the value associated with each class variable. */
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getDelivery() {
		return delivery;
	}
	public String getSecond() {
		return second;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	
	public ArrayList<String> toList(){
		ArrayList<String> tl = new ArrayList<String>();
		tl.add(getFirstName());
		tl.add(getLastName());
		tl.add(getDelivery());
		tl.add(getSecond());
		tl.add(getCity());
		tl.add(getState());
		tl.add(getZipcode());
		tl.add(getPhone());
		tl.add(getEmail());
		return tl;
	}
	
	
	
	
	
	
}