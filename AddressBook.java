import java.nio.file.Path;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddressBook {
	// class variables
	// book is protected so that the List can be directly viewed by other classes
	protected ObservableList<Entry> book;
	private String name;
	private Path path;
	private boolean isModified;
	
	/** Creates a new instance of an address book with default values */
	public AddressBook() {
		book = FXCollections.observableArrayList();
		setName("Address Book");
		//setPath(path.getFileName()); // check if this is correct!
		setModified(false);
	}
	
	/** Adds a new entry to the address book and returns if it was successful */
	public boolean addEntry(Entry entry) {
		book.add(entry);
		return true;
	}
	
	/** Edits an existing entry in the address book and returns if it was successful */
	public boolean editEntry(Entry entry, int index) {
		book.set(index, entry);
		return true;
	}
	
	/** Deletes an existing entry in the address book and returns if it was successful */
	public boolean deleteEntry(int index) {
		book.remove(index);
		return true;
	}
	
	/** Returns whether a class variable was assigned to a value */
	public boolean setName(String name) {
		this.name = name;
		return true;
	}
	public boolean setPath(Path path) {
		this.path = path;
		return true;
	}
	public void setModified(boolean modified) {
		this.isModified = modified;
	}
	
	/** Returns the value associated with each class variable */
	public String getName() {
		return name;
	}
	public Path getPath() {
		return path;
	}
	public boolean isModified() {
		return isModified;
	}
}
