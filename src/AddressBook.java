import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A custom object that represents an address book.
 */

public class AddressBook {
	// the book is a protected ObservableList to allow other classes to view it
	protected ObservableList<Entry> book;
	private String name;
	private String path; // this will need to be changed to whatever data-type Java uses
	private boolean isModified;
	
	/** Instantiates an AddressBook with default values and no entries. */
	public AddressBook() {
		book = FXCollections.observableArrayList();
		setName("Address Book");
		setPath("Untitled.book"); // check if this is correct!
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
		return true;
	}
	
	/** Returns whether a class variable was successfully assigned to a value. */
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