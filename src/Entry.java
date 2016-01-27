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
		String address = "";
		if (getDelivery() != "") {
			address += getDelivery();
		}
		if (getSecond() != "") {
			if (getDelivery() != "")
				address += " ";
			address += getSecond();
		}
		if (getCity() != "") {
			if (getSecond() != "" || getDelivery() != "")
				address += "\n";
			address += getCity();
		}
		if (getState() != "") {
			if (getCity() != "")
				address += ", ";
			address += getState();
		}
		if (getZipcode() != "") {
			if (getCity() != "" || getState() != "")
				address += " ";
			address += getZipcode();
		}
		return address;
	}

	/** Returns an ArrayList with the fields. */
	public ArrayList<String> toList(){
		ArrayList<String> list = new ArrayList<String>();
		list.add(getFirstName());
		list.add(getLastName());
		list.add(getDelivery());
		list.add(getSecond());
		list.add(getCity());
		list.add(getState());
		list.add(getZipcode());
		list.add(getPhone());
		list.add(getEmail());
		return list;
	}

	/** Assign the class variable and return whether it passes the validation. */
	public boolean setFirstName(String firstName) {
		if (firstName == null) {
			this.firstName = "";
		} else {
			this.firstName = firstName;
		}
		if (firstName.matches("[a-zA-Z]+")) {
			return true;
		}
		return false;
	}
	public boolean setLastName(String lastName) {
		if (lastName == null) {
			this.lastName = "";
		} else {
			this.lastName = lastName;
		}
		if (lastName.matches("[a-zA-Z]+")) {
			return true;
		}
		return false;
	}
	public boolean setDelivery(String delivery) {
		if (delivery == null) {
			this.delivery = "";
		} else {
			this.delivery = delivery;
		}
		return true;
	}
	public boolean setSecond(String second) {
		if (second == null) {
			this.second = "";
		} else {
			this.second = second;
		}
		return true;
	}
	public boolean setCity(String city) {
		if (city == null) {
			this.city = "";
		} else {
			this.city = city;
		}
		if (city.matches("[a-zA-Z]+")) {
			return true;
		}
		return false;
	}
	public boolean setState(String state) {
		if (state == null) {
			this.state = "";
		} else {
			this.state = state;
		}
		if (state.matches("[a-zA-Z]+")) {
			return true;
		}
		return false;
	}
	public boolean setZipcode(String zipcode) {
		if (zipcode == null) {
			this.zipcode = "";
		} else {
			this.zipcode = zipcode;
		}
		String[] parts = zipcode.split("-");
		if ((zipcode.length() == 5) && (zipcode.matches("\\d+"))) {
			return true;	
		} else if ((zipcode.length() == 10) &&
				(parts.length > 1 ) &&
				(parts[0].length() == 5) &&
				(parts[1].length() == 4) &&
				(parts[0].matches("\\d+")) &&
				(parts[1].matches("\\d+"))) {
			return true;	
		}
		return false;
	}
	public boolean setPhone(String phone) {
		if (phone == null) {
			this.phone = "";
		} else {
			this.phone = phone;
		}
		if (((phone.length() == 10) &&
				(phone.matches("\\d+"))) ||
				(phone.matches("[0-9]{3}+-[0-9]{3}+-[0-9]{4}")))
		{
			return true;
		}
		return false;
	}
	public boolean setEmail(String email) {
		if (email == null) {
			this.email = "";
		} else {
			this.email = email;
		}
		if (email.matches("[0-9a-zA-Z._]+@[0-9a-zA-Z.-]+\\.[a-zA-Z]{2,3}")) {
			return true;
		}
		return false;
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
}