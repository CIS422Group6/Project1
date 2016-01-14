import javafx.application.Application;

/**
 * 
 * @author watchdogs
 * 
 * The starting point of the program.
 */

public class Main {
	
	// create a sample address book until file i/o is working
	public static AddressBook book = new AddressBook();
	
	/** Initializes the program */
	public static void main(String[] args) {
		// add entries to demonstrate functionality
		book.addEntry(new Entry("John", "Doe", "123 West Lane", "12345"));
		book.addEntry(new Entry("Jane", "Don", "321 East Street", "54321"));
		
		// create and show the GUI
		Application.launch(PrimaryWindow.class, args);
	}
}
