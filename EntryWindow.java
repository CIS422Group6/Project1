import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

// dialog box from adding/editing people, uses JavaFX Dialog for added functionality
public class EntryWindow extends Dialog<Entry> {
	
	public EntryWindow(Entry person) {
		//set window properties
		setTitle("Edit entry");
		setHeaderText("Create a new person or edit an existing one");
		setResizable(false);
		
		ButtonType save = new ButtonType("Save", ButtonData.APPLY);
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		getDialogPane().getButtonTypes().addAll(save, cancel);
		
		GridPane layout = new GridPane();
		layout.setHgap(10);
		layout.setVgap(10);
		layout.setPadding(new Insets(20, 20, 20, 20));
		
		TextField firstName = new TextField();
		firstName.setText(person.getFirstName());
		TextField lastName = new TextField();
		lastName.setText(person.getLastName());
		TextField address = new TextField();
		address.setText(person.getAddress());
		TextField zipcode = new TextField();
		zipcode.setText(person.getZipcode());
		
		Label test = new Label("hello");
		test.alignmentProperty();
		layout.add(new Label("First name:"), 0, 0);
		layout.add(firstName, 1, 0);
		layout.add(new Label("Last name:"), 0, 1);
		layout.add(lastName, 1, 1);
		layout.add(new Label("Address:"), 0, 2);
		layout.add(address, 1, 2);
		layout.add(new Label("Zipcode:"), 0, 3);
		layout.add(zipcode, 1, 3);
		
		getDialogPane().setContent(layout);
		firstName.requestFocus();
		
		setResultConverter(result -> {
			if (result == cancel) {
				return null;
			}
			person.setFirstName(firstName.getText());
			person.setLastName(lastName.getText());
			person.setAddress(address.getText());
			person.setZipcode(zipcode.getText());
			return person;
		});
	}
	
	public EntryWindow() {
		this(new Entry());
	}
}