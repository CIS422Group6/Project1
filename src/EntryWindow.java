import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * A dialog that prompts the user to enter data for an entry.
 */

public class EntryWindow extends Dialog<Entry> {
	
	/** Instantiates an EntryWindow and fills the fields with the provided Entry. */
	public EntryWindow(Entry entry) {
		// set window properties
		setTitle("Entry window");
		setHeaderText("Create a new person or edit an existing one");
		setResizable(false);
		
		// create and add buttons
		ButtonType save = new ButtonType("Save", ButtonData.APPLY);
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		getDialogPane().getButtonTypes().addAll(save, cancel);
		
		// create the layout manager
		GridPane layout = new GridPane();
		layout.setHgap(10);
		layout.setVgap(10);
		layout.setPadding(new Insets(20, 20, 20, 20));
		
		
		// create and optionally pre-fill the textfields
		TextField firstName = new TextField();
		firstName.setText(entry.getFirstName());
		TextField lastName = new TextField();
		lastName.setText(entry.getLastName());
		TextField address = new TextField();
		address.setText(entry.getAddress());
		TextField zipcode = new TextField();
		zipcode.setText(entry.getZipcode());
		
		// add the labels and textfields to the dialog
		layout.add(new Label("First name:"), 0, 0);
		layout.add(firstName, 1, 0);
		layout.add(new Label("Last name:"), 0, 1);
		layout.add(lastName, 1, 1);
		layout.add(new Label("Address:"), 0, 2);
		layout.add(address, 1, 2);
		layout.add(new Label("Zipcode:"), 0, 3);
		layout.add(zipcode, 1, 3);
		
		// apply changes
		getDialogPane().setContent(layout);
		firstName.requestFocus();
		
		// define return values
		setResultConverter(result -> {
			if (result == cancel) {
				return null;
			}
			entry.setFirstName(firstName.getText());
			entry.setLastName(lastName.getText());
			entry.setAddress(address.getText());
			entry.setZipcode(zipcode.getText());
			return entry;
		});
	}
	
	/** Instantiates an empty EntryWindow. */
	public EntryWindow() {
		this(new Entry());
	}
}