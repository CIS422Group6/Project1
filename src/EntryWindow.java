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
		setHeaderText("Create a new entry or edit an existing one");
		setResizable(false);
		
		// create and add buttons
		ButtonType save = new ButtonType("Save");
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		getDialogPane().getButtonTypes().addAll(save, cancel);
		
		// create the layout manager
		GridPane layout = new GridPane();
		layout.setHgap(10);
		layout.setVgap(10);
		layout.setPadding(new Insets(20, 20, 20, 20));
		
		// create and pre-fill the textfields
		TextField firstName = new TextField();
		firstName.setText(entry.getFirstName());
		TextField lastName = new TextField();
		lastName.setText(entry.getLastName());
		TextField delivery = new TextField();
		delivery.setText(entry.getDelivery());
		TextField second = new TextField();
		second.setText(entry.getSecond());
		TextField city = new TextField();
		city.setText(entry.getCity());
		TextField state = new TextField();
		state.setText(entry.getState());
		TextField zipcode = new TextField();
		zipcode.setText(entry.getZipcode());
		TextField phone = new TextField();
		phone.setText(entry.getPhone());
		TextField email = new TextField();
		email.setText(entry.getEmail());
		
		// add the labels and textfields to the dialog
		layout.add(new Label("First name:"), 0, 0);
		layout.add(firstName, 1, 0);
		layout.add(new Label("Last name:"), 0, 1);
		layout.add(lastName, 1, 1);
		layout.add(new Label("Address Line 1:"), 0, 2);
		layout.add(delivery, 1, 2);
		layout.add(new Label("Address Line 2:"), 0, 3);
		layout.add(second, 1, 3);
		layout.add(new Label("City:"), 0, 4);
		layout.add(city, 1, 4);
		layout.add(new Label("State:"), 0, 5);
		layout.add(state, 1, 5);
		layout.add(new Label("Zipcode:"), 0, 6);
		layout.add(zipcode, 1, 6);
		layout.add(new Label("Phone:"), 0, 7);
		layout.add(phone, 1, 7);
		layout.add(new Label("Email:"), 0, 8);
		layout.add(email, 1, 8);
		
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
			entry.setDelivery(delivery.getText());
			entry.setSecond(second.getText());
			entry.setCity(city.getText());
			entry.setState(state.getText());
			entry.setZipcode(zipcode.getText());
			entry.setPhone(phone.getText());
			entry.setEmail(email.getText());
			return entry;
		});
	}
	
	/** Instantiates an empty EntryWindow. */
	public EntryWindow() {
		this(new Entry());
	}
}