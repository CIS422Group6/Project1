import java.util.Optional;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The main window that contains the GUI and front-end functionality of the application.
 */

public class PrimaryWindow extends Application {
	// class variables
	Main main;
	Stage stage;
	AddressBook book = new AddressBook();

	/** Constructor for a PrimaryWindow with the AddressBook to be loaded. */
	public PrimaryWindow(Main main, AddressBook book) {
		this.main = main;
		this.book = book;
	}

	/** Create and show the main program window. */
	@SuppressWarnings("unchecked")
	public void start(Stage stage) throws Exception {
		// set window properties
		stage.setTitle("The Address Book");
		stage.setMinHeight(600);
		stage.setMinWidth(800);

		// initialize all the GUI components
		VBox layout = new VBox(0);
		Scene scene = new Scene(layout, 800, 600);
		// table
		TableView<Entry> table = new TableView<Entry>();
		// menuBar
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		MenuItem newBook = new MenuItem("New"),
				openBook = new MenuItem("Open..."),
				saveBook = new MenuItem("Save"),
				saveAsBook = new MenuItem("Save As..."),
				closeBook = new MenuItem("Close address book"),
				exit = new MenuItem("Exit");
		Menu menuEdit = new Menu("Edit");
		MenuItem addEntry = new MenuItem("Add new entry"),
				editEntry = new MenuItem("Edit entry"),
				deleteEntry = new MenuItem("Delete entry"),
				searchEntry = new MenuItem("Search"),
				importEntry = new MenuItem("Import..."),
				exportEntry = new MenuItem("Export...");
		Menu menuHelp = new Menu("Help");
		MenuItem about = new MenuItem("About The Address Book"),
				website = new MenuItem("Visit website");


		// set fileMenu button properties
		newBook.setOnAction((ActionEvent t) -> {
			main.createWindow(BookFile.newBook());
		});
		openBook.setOnAction((ActionEvent t) -> {
			// prompt the user to select a file from disk 
			String path = FileWindow.chooseFile(stage, FileWindow.OPEN_XML);
			if (path != null) {
				// if an AddressBook is not currently open
				if (!book.isModified() && book.getPath() == "") {
					// open the file into an AddressBook in this window
					book = BookFile.openBook(path);
					table.setItems(book.getBook());
					return;
				} // otherwise use a new window, alerting if we cannot
				main.createWindow(BookFile.openBook(path));
			} // do nothing if no AddressBook was selected
		});
		saveBook.setOnAction((ActionEvent t) -> {
			// changes are saved, reset flag
			book.setModified(false);
			BookFile.saveBook(book, stage);
		});
		saveAsBook.setOnAction((ActionEvent t) -> {
			// changes are saved, reset flag
			book.setModified(false);
			BookFile.saveAsBook(book, stage);
		});
		closeBook.setOnAction((ActionEvent t) -> {
			// prompt user to save changes before closing
			if (!saveModifications(ConfirmWindow.SAVE_CLOSE)) {
				return;
			}
			// close the current window
			this.main.closeWindow(this);
		});
		// override X button behavior
		stage.setOnCloseRequest(event -> {
			// prompt user to save changes before closing
			if (!saveModifications(ConfirmWindow.SAVE_CLOSE)) {
				return;
			}
			// close the current window
			this.main.closeWindow(this);
		});
		exit.setOnAction((ActionEvent t) -> {
			this.main.exit();
		});
		menuFile.getItems().addAll(newBook, openBook, new SeparatorMenuItem(), saveBook, saveAsBook, new SeparatorMenuItem(), closeBook, exit);
		// editMenu buttons
		addEntry.setOnAction((ActionEvent t) -> {
			// prompt user for Entry details
			EntryWindow entryWindow = new EntryWindow(EntryWindow.ADD);
			Optional<Entry> result = entryWindow.showAndWait();
			if (result.isPresent()) {
				// changes are made, set flag
				book.setModified(true);
				book.getBook().add(result.get());
			} // otherwise user cancelled
		});
		// button disabled if exactly 1 Entry is not selected
		editEntry.disableProperty().bind(Bindings.notEqual(1, Bindings.size(table.getSelectionModel().getSelectedItems())));
		editEntry.setOnAction((ActionEvent t) -> {
			// fill input window
			int selected = table.getSelectionModel().getSelectedIndex();
			Entry entry = book.getBook().get(selected).clone();
			EntryWindow entryWindow = new EntryWindow(entry, EntryWindow.EDIT);
			Optional<Entry> result = entryWindow.showAndWait();
			if (result.isPresent()) {
				// changes are made, set flag
				book.setModified(true);
				book.getBook().set(selected, result.get());
			}
		});
		// button disabled if no Entry is selected
		deleteEntry.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
		deleteEntry.setOnAction((ActionEvent t) -> {
			// confirm deletion
			ConfirmWindow confirmWindow = new ConfirmWindow(ConfirmWindow.DELETE_ENTRY);
			Optional<ButtonType> result = confirmWindow.showAndWait();
			if (result.get() == ButtonType.YES) {
				// delete entry and set flag
				book.getBook().removeAll(table.getSelectionModel().getSelectedItems());
				book.setModified(true);
			} // otherwise do nothing
		});
		// button disabled while dialog is open
		searchEntry.setOnAction((ActionEvent t) -> {
			searchEntry.setDisable(true);
			EntryWindow entryWindow = new EntryWindow(EntryWindow.SEARCH);
			entryWindow.initModality(Modality.NONE);
			Optional<Entry> result = entryWindow.showAndWait();
			// update search until user closes dialog
			while (result.isPresent()) {
				// search and apply new filtered AddressBook
				AddressBook filter = BookFile.search(book, result.get());
				table.setItems(filter.getBook());
				// re-show prompt
				result = entryWindow.showAndWait();
			}
			// reset table to original AddressBook
			table.setItems(this.book.getBook());
			searchEntry.setDisable(false);
		});
		importEntry.setOnAction((ActionEvent t) -> {
			String path = FileWindow.chooseFile(stage, FileWindow.IMPORT_TSV);
			if (path != null) {
				// add the .tsv into the current book
				book.getBook().addAll(BookFile.importTSV(path));
				book.setModified(true);
			} // do nothing if no file selected
		});
		exportEntry.setOnAction((ActionEvent t) -> {
			String path = FileWindow.chooseFile(stage, FileWindow.EXPORT_TSV);
			if (path != null) {
				BookFile.exportTSV(book, path);
			} // do nothing if no file selected
		});
		menuEdit.getItems().addAll(addEntry, editEntry, deleteEntry, new SeparatorMenuItem(), searchEntry, new SeparatorMenuItem(), importEntry, exportEntry);
		// helpMenu buttons
		about.setOnAction((ActionEvent t) -> {
		});
		website.setOnAction((ActionEvent t) -> {
		});
		menuHelp.getItems().addAll(about, website);
		// build menuBar
		menuBar.getMenus().addAll(menuFile, menuEdit, menuHelp);

		// set table properties
		VBox.setVgrow(table, Priority.ALWAYS);
		table.setItems(book.getBook());
		table.setEditable(false);
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		// table columns
		TableColumn<Entry, String> firstName = new TableColumn<Entry, String>("First Name");
		firstName.setCellValueFactory(new PropertyValueFactory<Entry, String>("firstName"));
		firstName.setMinWidth(80);

		TableColumn<Entry, String> lastName = new TableColumn<Entry, String>("Last Name");
		lastName.setCellValueFactory(new PropertyValueFactory<Entry, String>("lastName"));
		lastName.setMinWidth(80);

		TableColumn<Entry, String> address = new TableColumn<Entry, String>("Address");
		address.setCellValueFactory(new PropertyValueFactory<Entry, String>("address"));
		address.setMinWidth(80);

		TableColumn<Entry, String> zipcode = new TableColumn<Entry, String>("Zipcode");
		zipcode.setCellValueFactory(new PropertyValueFactory<Entry, String>("zipcode"));
		zipcode.setMinWidth(80);

		TableColumn<Entry, String> phone = new TableColumn<Entry, String>("Phone");
		phone.setCellValueFactory(new PropertyValueFactory<Entry, String>("phone"));
		phone.setMinWidth(80);

		TableColumn<Entry, String> email = new TableColumn<Entry, String>("Email");
		email.setCellValueFactory(new PropertyValueFactory<Entry, String>("email"));
		email.setMinWidth(80);
		// build table
		table.getColumns().addAll(lastName, firstName, address, zipcode, phone, email);

		// build the window
		layout.getChildren().addAll(menuBar, table);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
		this.stage = stage;
	}

	/** Prompts the user if changes to the current AddressBook should be saved and returns whether the window can safely be closed. */
	public boolean saveModifications(int type) {
		if (book != null && book.isModified()) {
			// pull focus into current window
			stage.requestFocus();
			// prompt the user if they would like to save changes
			ConfirmWindow confirmWindow = new ConfirmWindow(book.getName(), type);
			Optional<ButtonType> result = confirmWindow.showAndWait();
			if (result.get() == ButtonType.YES) {
				// save changes if YES
				BookFile.saveBook(book, this.stage);
			} else if (result.get() == ButtonType.CANCEL) {
				// keep window open if CANCEL
				return false;
			} // discard changes if NO
		} // window can safely be closed
		stage.close();
		return true;
	}
}