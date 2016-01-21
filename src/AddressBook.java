import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerException;
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
	protected ObservableList<Entry> book; // allows other classes to view it
	private String name;
	private String path;
	private boolean isModified;
	
	/** Instantiates an empty AddressBook with default values. */
	public AddressBook() {
		book = FXCollections.observableArrayList();
		setName("Address Book");
		setPath(""); // no path by default
		setModified(false);
	}
	
	/** Returns whether adding a new Entry was successful. */
	public boolean addEntry(Entry entry) {
		book.add(entry);
		return true;
	}
	
	/** Returns whether an edit of an Entry was successful. */
	public boolean editEntry(Entry entry, int index) {
		book.set(index, entry);
		return true;
	}
	
	/** Returns whether the deletion of an Entry was successful. */
	public boolean deleteEntry(int index) {
		book.remove(index);
		return true;
	}
	
	/** Returns whether the AddressBook was successfully saved into a file. */
	public boolean saveAddressBook() {
		// convert the data to (xml) format and write it to the file located at path
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuild = dbf.newDocumentBuilder();
			
			// base
			Document dBook = dBuild.newDocument();
			Element dBookRoot = dBook.createElement("addressBook");
			dBook.appendChild(dBookRoot);

			/* leaving the name save feature out for the moment, till load problem is fixed*/
			Element bookName = dBook.createElement("bookName");
			bookName.appendChild(dBook.createTextNode(name));
			dBookRoot.appendChild(bookName);
			
			
			Element entry;
			Attr atr;
			Element firstName;
			Element lastName;
			Element address;
			Element zipcode;

			// add all entries to document under entry tag
			for (int i = 0; i < book.size(); i++) {
				entry = dBook.createElement("entry");
				dBookRoot.appendChild(entry);

				atr = dBook.createAttribute("id");
				atr.setValue(Integer.toString(i));
				entry.setAttributeNode(atr);

				firstName = dBook.createElement("firstName");
				firstName.appendChild(dBook.createTextNode(book.get(i).getFirstName()));
				entry.appendChild(firstName);

				lastName = dBook.createElement("lastName");
				lastName.appendChild(dBook.createTextNode(book.get(i).getLastName()));
				entry.appendChild(lastName);

				address = dBook.createElement("address");
				address.appendChild(dBook.createTextNode(book.get(i).getAddress()));
				entry.appendChild(address);

				zipcode = dBook.createElement("zipcode");
				zipcode.appendChild(dBook.createTextNode(book.get(i).getZipcode()));
				entry.appendChild(zipcode);
			}

			// save document built
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trFo = tf.newTransformer();
			DOMSource dIn = new DOMSource(dBook);
			StreamResult dOut = new StreamResult(new File(path));
			trFo.transform(dIn, dOut);
		} catch (Exception e) {
			e.printStackTrace();
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
	public ObservableList<Entry> getBook(){
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