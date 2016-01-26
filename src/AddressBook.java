import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A custom object that represents an address book.
 */

public class AddressBook {
	protected ObservableList<Entry> book; // allow other classes to view it but use methods to change it
	private String name;
	private String path;
	private boolean isModified;
	
	/** Constructor for an AddressBook with default values. */
	public AddressBook() {
		book = FXCollections.observableArrayList();
		setName("Address Book");
		setPath(""); // no path by default
		setModified(false);
	}
	
	/** Add a new Entry and return if it was successful. */
	public boolean addEntry(Entry entry) {
		book.add(entry);
		return true;
	}
	
	/** Edit an Entry and return if it was successful. */
	public boolean editEntry(Entry entry, int index) {
		book.set(index, entry);
		return true;
	}
	
	/** Delete an Entry an return if it was successful. */
	public boolean deleteEntry(int index) {
		book.remove(index);
		return true;
	}
	
	/** Save the AddressBook into a file and return if it was successful. */
	public boolean saveAddressBook() {
		// convert the data to (xml) format and write it to the file located at path
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuild = dbf.newDocumentBuilder();

			// addressbook element
			Document dBook = dBuild.newDocument();
			Element dBookRoot = dBook.createElement("addressBook");
			dBook.appendChild(dBookRoot);
			
			// name element
			Element bookName = dBook.createElement("bookName");
			bookName.appendChild(dBook.createTextNode(name));
			dBookRoot.appendChild(bookName);

			// entry elements
			Element entry;
			int i = 0;
			Attr atr;
			Element firstName, lastName,
				delivery,
				second,
				city, state, zipcode,
				phone, email;

			// build each entry
			for (Entry e : book) {
				entry = dBook.createElement("entry");
				dBookRoot.appendChild(entry);

				atr = dBook.createAttribute("id");
				atr.setValue(Integer.toString(i));
				entry.setAttributeNode(atr);
				i++;

				firstName = dBook.createElement("firstName");
				firstName.appendChild(dBook.createTextNode(e.getFirstName()));
				entry.appendChild(firstName);

				lastName = dBook.createElement("lastName");
				lastName.appendChild(dBook.createTextNode(e.getLastName()));
				entry.appendChild(lastName);

				delivery = dBook.createElement("delivery");
				delivery.appendChild(dBook.createTextNode(e.getDelivery()));
				entry.appendChild(delivery);

				second = dBook.createElement("second");
				second.appendChild(dBook.createTextNode(e.getSecond()));
				entry.appendChild(second);

				city = dBook.createElement("city");
				city.appendChild(dBook.createTextNode(e.getCity()));
				entry.appendChild(city);

				state = dBook.createElement("state");
				state.appendChild(dBook.createTextNode(e.getState()));
				entry.appendChild(state);

				zipcode = dBook.createElement("zipcode");
				zipcode.appendChild(dBook.createTextNode(e.getZipcode()));
				entry.appendChild(zipcode);

				phone = dBook.createElement("phone");
				phone.appendChild(dBook.createTextNode(e.getPhone()));
				entry.appendChild(phone);

				email = dBook.createElement("email");
				email.appendChild(dBook.createTextNode(e.getEmail()));
				entry.appendChild(email);
			}

			// save the document to a file
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trFo = tf.newTransformer();
			DOMSource dIn = new DOMSource(dBook);
			StreamResult dOut = new StreamResult(new File(path));
			trFo.transform(dIn, dOut);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
	    }
		return true;
	}
	
	/** Returns whether a class variable was successfully assigned to a value. */
	public boolean setBook(ObservableList<Entry> book){
		this.book = book;
		return true;
	}
	public boolean setName(String name) {
		this.name = name;
		return true;
	}
	public boolean setPath(String path) {
		this.path = path;
		return true;
	}
	public void setModified(boolean modified) {
		this.isModified = modified;
	}
	
	/** Returns the value associated with each class variable. */
	public ObservableList<Entry> getBook() {
		return book;
	}
	public String getName() {
		return name;
	}
	public String getPath() {
		return path;
	}
	public boolean isModified() {
		return isModified;
	}
}