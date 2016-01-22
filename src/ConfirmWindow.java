import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 * A dialog that confirms a given action.
 */

public class ConfirmWindow extends Dialog<ButtonType>{
	
	/** Instantiates a ConfirmWindow with a given action. */
	public ConfirmWindow(String action) {
		// set window properties
		setTitle("Save");
		setHeaderText("Save changes to file before " + action + "?");
		setResizable(false);
		
		// create and add buttons
		ButtonType yes = new ButtonType("Yes", ButtonData.YES);
		ButtonType no = new ButtonType("No", ButtonData.NO);
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		getDialogPane().getButtonTypes().addAll(yes, no, cancel);
		
		// define return values
		setResultConverter(result -> {
			if (result == yes) {
				return ButtonType.YES;
			} else if (result == no) {
				return ButtonType.NO;
			} else {
				return ButtonType.CANCEL;
			}
		});
	}
}