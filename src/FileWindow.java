import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * A dialog that prompts the user to select a file in their system.
 */

public class FileWindow {
	public static final int OPEN_XML = 0, SAVE_XML = 1, IMPORT_TSV = 2, EXPORT_TSV = 3;

	/** Returns the location to an AddressBook to be opened. */
	public static String chooseFile(Stage stage, int type) {
		FileChooser fileWindow = new FileChooser();
		FileChooser.ExtensionFilter all = new FileChooser.ExtensionFilter("All types", "*.*"),
				xml = new FileChooser.ExtensionFilter("eXtensible Markup Language file", "*.xml"),
				tsv = new FileChooser.ExtensionFilter("Tab-separated Values file", "*.tsv");
		File file = null;

		// generate appropriate dialog and file-types
		switch (type) {
		case 0:
			fileWindow.getExtensionFilters().addAll(xml, all);
			file = fileWindow.showOpenDialog(stage);
			break;
		case 1:
			fileWindow.getExtensionFilters().addAll(xml, all);
			file = fileWindow.showSaveDialog(stage);
			break;
		case 2:
			fileWindow.getExtensionFilters().addAll(tsv, all);
			file = fileWindow.showOpenDialog(stage);
			break;
		case 3:
			fileWindow.getExtensionFilters().addAll(tsv, all);
			file = fileWindow.showSaveDialog(stage);
			break;
		}

		// return chosen file
		if (file != null) {
			return file.getPath();
		} // otherwise
		return null;
	}
}