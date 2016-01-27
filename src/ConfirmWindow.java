import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * A dialog that confirms a given action.
 */

public class ConfirmWindow extends Dialog<ButtonType>{
	public static final int SAVE_CLOSE = 0, SAVE_EXIT = 1, DELETE_ENTRY = 2, DATA_VALIDATE = 3, PROGRAM_WARNING = 4, PROGRAM_ERROR = 5;
	ButtonType yes, no, cancel;

	/** Instantiates a ConfirmWindow with a given action. */
	public ConfirmWindow(String data, int type) {
		// set window properties
		setResizable(false);
		setGraphic(new ImageView(this.getClass().getResource("/com/sun/javafx/scene/control/skin/modena/dialog-information.png").toString()));

		// define return values
		setResultConverter(result -> {
			if (result == yes) {
				return ButtonType.YES;
			} else if (result == no) {
				return ButtonType.NO;
			} else {
				// cancel or window X button
				return ButtonType.CANCEL;
			}
		});

		// generate the appropriate type of dialog
		switch (type) {
		case 0:
			setTitle("Save");
			setContentText("Do you want to save changes to file \'" + data + "\' before closing?");
			yes = new ButtonType("Save", ButtonData.YES);
			no = new ButtonType("Don't save", ButtonData.NO);
			cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			getDialogPane().getButtonTypes().addAll(yes, no, cancel);
			break;
		case 1:
			setTitle("Save");
			setContentText("Do you want to save changes to file \'" + data + "\' before exiting?");
			yes = new ButtonType("Save", ButtonData.YES);
			no = new ButtonType("Don't save", ButtonData.NO);
			cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			getDialogPane().getButtonTypes().addAll(yes, no, cancel);
			break;
		case 2:
			setTitle("Delete");
			setContentText("Are you sure want to delete the selection?");
			yes = new ButtonType("Yes", ButtonData.YES);
			no = new ButtonType("No", ButtonData.NO);
			getDialogPane().getButtonTypes().addAll(yes, no);
			break;
		case 3:
			setTitle("Confirm data");
			setHeaderText("Input(s) do not match the expected format. Do you still want to apply them?");
			setContentText(data);
			yes = new ButtonType("Yes", ButtonData.YES);
			no = new ButtonType("No", ButtonData.NO);
			getDialogPane().getButtonTypes().addAll(yes, no);
			break;
		case 4:
			setTitle("Warning");
			setContentText(data);
			yes = new ButtonType("OK", ButtonData.YES);
			getDialogPane().getButtonTypes().addAll(yes);
			break;
		case 5:
			setTitle("Error");
			setHeaderText("An unexpected error occured.");
			yes = new ButtonType("OK", ButtonData.YES);
			getDialogPane().getButtonTypes().addAll(yes);
			// show error details
			GridPane layout = new GridPane();
			TextArea textArea = new TextArea(data);
			textArea.setEditable(false);
			textArea.setWrapText(true);
			layout.add(textArea, 0, 0);
			getDialogPane().setContent(layout);
			break;
		}
	}

	public ConfirmWindow(int type) {
		this(null, type);
	}
}