/**
 * A collection of functions to read an AddressBook into program memory and write to disk.
 */

public class File {
	
	/** Creates a new instance of an empty AddressBook and returns it. */
	static AddressBook newBook() {
		AddressBook book = new AddressBook();
		return book;
	}
	
	/** Loads a book from a file (at a given path) into program memory. */
	static AddressBook openBook(String path) {
		AddressBook book = new AddressBook();
		// read in the data from the provided file into the book
		return book;
	}
	
	/** Writes changes to an AddressBook back into its file. */
	static boolean saveBook(AddressBook book) {
		// does path exist in AddressBook?
		// no: call saveAsBook(book)
		// yes: call book.saveAddressBook()
		return true;
	}
	
	/** Creates a file that the AddressBook can be saved into. */
	static boolean saveAsBook() {
		// prompt the user where to save the book
		// update book.setPath(path) then call book.saveAddressBook()
		return true;
	}
	
	/** Closes the currently open AddressBook. */
	static void closeBook() {
		// future iteration
	}
}