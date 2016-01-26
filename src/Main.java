import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class manages the active windows of the application.
 */

public class Main extends Application {
	// track the active windows
	private ArrayList<PrimaryWindow> windows;
	private int max_windows = 5;
	private int cur_windows;
	
	/** Constructor for the managing Main class. */
	public Main() {
		windows = new ArrayList<PrimaryWindow>(max_windows);
		cur_windows = 0;
	}
	
	/** Starting point of the application that will initialize all components */
	public static void main(String[] args) {
		launch(args);
	}
	
	/** Attempt to create a new window with a user-supplied book and return whether it was successful. */
	public boolean newWindow(AddressBook book) throws Exception {
		if (cur_windows == max_windows - 1) {
			// maximum windows reached
			return false;
		}
		// create the new window
		PrimaryWindow window = new PrimaryWindow(this, book);
		windows.add(window);
		window.start(new Stage());
		cur_windows++;
		return true;
	}
	
	/** Close the current window. */
	public void closeWindow(PrimaryWindow window) {
		if (cur_windows == 1) {
			// exit the application if this is the last window
			System.exit(0);
		}
		window.stop();
		windows.remove(window);
		cur_windows--;
	}
	
	/** Close all windows and exit the application. */
	public void closeAllWindows() {
		for (PrimaryWindow window : windows) {
			window._stage.requestFocus();
			window.saveModifications(window._stage, "exiting");
			window._stage.close();
		}
		System.exit(0);
	}

	/** Implemented method from Application used in launch(), overridden to launch a PrimaryWindow. */
	@Override
	public void start(Stage stage) throws Exception {
		// start the first window
		PrimaryWindow window = new PrimaryWindow(this, BookFile.newBook());
		windows.add(window);
		window.start(stage);
		cur_windows++;
	}
}