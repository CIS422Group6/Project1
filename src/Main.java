import java.util.ArrayList;
import java.util.Iterator;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class manages the active windows of the application.
 */

public class Main extends Application {
	// track the active windows
	private ArrayList<PrimaryWindow> windows;
	private final int MAX_WINDOWS = 5;

	/** Constructor for the managing Main class. */
	public Main() {
		windows = new ArrayList<PrimaryWindow>();
	}

	/** Starting point of the application that will initialize all components */
	public static void main(String[] args) {
		// create an instance of Main as an Application
		launch(args);
	}

	/** Attempt to create a new window with a loaded AddressBook and return whether it was successful. */
	public boolean createWindow(AddressBook book) {
		if (windows.size() == MAX_WINDOWS) {
			// maximum windows already created
			ConfirmWindow confirmWindow = new ConfirmWindow("The maximum number of program windows has been reached.", ConfirmWindow.PROGRAM_WARNING);
			confirmWindow.showAndWait();
			return false;
		}
		// create the new window
		PrimaryWindow window = new PrimaryWindow(this, book);
		Stage stage = new Stage();
		windows.add(window);
		try {
			window.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/** Close the current window. */
	public void closeWindow(PrimaryWindow window) {
		if (windows.size() == 1) {
			// exit the application if this is the last window
			System.exit(0);
		}
		windows.remove(window);
	}

	/** Close all windows and exit the application. */
	public void exit() {
		Iterator<PrimaryWindow> it = windows.iterator();
		// close all open windows
		while (it.hasNext()) {
			PrimaryWindow window = it.next();
			// prompt user to save changes before exiting
			if (!window.saveModifications(ConfirmWindow.SAVE_EXIT)) {
				// keep program open if user cancels
				return;
			}
			it.remove();
			windows.remove(window);
		} // all windows successfully closed, exit program
		System.exit(0);
	}

	/** Override the default Application.start() to create a new PrimaryWindow for our application instead. */
	@Override
	public void start(Stage stage) {
		stage.close();
		// create the initial window
		createWindow(BookFile.newBook());
	}
}