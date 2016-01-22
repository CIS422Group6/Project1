import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * A dialog that prompts the user to select a file in their system.
 */

public class FileWindow {
	/** Returns the location to an AddressBook to be opened. */
	public static String openWindow(Stage stage) {
		FileChooser fileWindow = new FileChooser();
		File file = fileWindow.showOpenDialog(stage);
		if (file != null) {
			return file.getPath();
		} else {
			return null;
		}
	}
	
	/** Returns the user-selected location that an AddressBook is to be saved. */
	public static String saveWindow(Stage stage) {
		FileChooser fileWindow = new FileChooser();
		fileWindow.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.XML)", "*.XML"));
		String path = fileWindow.showSaveDialog(stage).getPath();
		return path;
	}
}
