import java.util.Optional;

import javafx.application.Platform;
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
	public static final int ADD = 0, EDIT = 1, SEARCH = 2;
	ButtonType confirm, cancel;

	public static String fields[] = new String[]{
			"First name must use letters only.",
			"Last name must use letters only.",
			"Delivery address.",
			"Secondary address.",
			"City must use letters only.",
			"State must use letters only.",
			"Zipcode must be in ZIP(+4) form.",
			"Phone numbers must be 10 digits with optional hyphenation.",
			"Email addresses must be in the form user@website.com."
	};

	/** Instantiates an EntryWindow of the given type. */
	public EntryWindow(Entry entry, int type) {
		// set window properties
		setResizable(false);
		GridPane layout = new GridPane();
		layout.setHgap(10);
		layout.setVgap(10);
		layout.setPadding(new Insets(20, 20, 20, 20));

		// create the text-fields
		TextField firstName = new TextField(),
				lastName = new TextField(),
				delivery = new TextField(),
				second = new TextField(),
				city = new TextField(),
				state = new TextField(),
				zipcode = new TextField(),
				phone = new TextField(),
				email = new TextField();

		// define the default button behaviors
		setResultConverter(result -> {
			if (result == confirm) {
				// entry validation
				boolean test = true;
				String data = "";
				if(!entry.setFirstName(firstName.getText())) {
					test = false;
					data += fields[0] + "\n";
				}
				if (!entry.setLastName(lastName.getText())) {
					test = false;
					data += fields[1] + "\n";
				}
				if (!entry.setDelivery(delivery.getText())) {
					test = false;
					data += fields[2] + "\n";
				}
				if (!entry.setSecond(second.getText())) {
					test = false;
					data += fields[3] + "\n";
				}
				if (!entry.setCity(city.getText())) {
					test = false;
					data += fields[4] + "\n";
				}
				if (!entry.setState(state.getText())) {
					test = false;
					data += fields[5] + "\n";
				}
				if (!entry.setZipcode(zipcode.getText())) {
					test = false;
					data += fields[6] + "\n";
				}
				if (!entry.setPhone(phone.getText())) {
					test = false;
					data += fields[7] + "\n";
				}
				if (!entry.setEmail(email.getText())) {
					test = false;
					data += fields[8] + "\n";
				}

				// inform user if input validation failed
				if (test) {
					return entry;
				} else {
					ConfirmWindow confirmWindow = new ConfirmWindow(data, ConfirmWindow.DATA_VALIDATE);
					Optional<ButtonType> answer = confirmWindow.showAndWait();
					if (answer.get() == ButtonType.YES) {
						// apply changes anyways
						return entry;
					} // cancel changes
					return null;
				}
			} // otherwise return nothing
			return null;
		});

		// generate the appropriate type of dialog
		switch (type) {
		case 0:
			setTitle("Add");
			setHeaderText("Add a new entry using the provided fields");
			confirm = new ButtonType("Save");
			cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			break;
		case 1:
			setTitle("Edit");
			setHeaderText("Edit an existing entry");
			confirm = new ButtonType("Update");
			cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			// fill the text-fields with the Entry to be edited
			firstName.setText(entry.getFirstName());
			lastName.setText(entry.getLastName());
			delivery.setText(entry.getDelivery());
			second.setText(entry.getSecond());
			city.setText(entry.getCity());
			state.setText(entry.getState());
			zipcode.setText(entry.getZipcode());
			phone.setText(entry.getPhone());
			email.setText(entry.getEmail());
			break;
		case 2:
			setTitle("Search");
			setHeaderText("Filter entries using the provided fields\nClose this window to return");
			confirm = new ButtonType("Search");
			cancel = new ButtonType("Close", ButtonData.CANCEL_CLOSE);
			// if searching, remove the Entry validation
			setResultConverter(result -> {
				if (result == confirm) {
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
				} // otherwise do nothing
				return null;
			});
			break;
		}

		// add the labels, text-fields, and buttons to the dialog
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
		getDialogPane().getButtonTypes().addAll(confirm, cancel);
		getDialogPane().setContent(layout);
		Platform.runLater(() -> firstName.requestFocus());
	}

	/** Instantiates an empty EntryWindow. */
	public EntryWindow(int type) {
		this(new Entry(), type);
	}
}