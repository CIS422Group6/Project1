import java.util.Optional;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;

/**
 * The main window that contains the GUI and front-end functionality of the application.
 */

public class PrimaryWindow extends Application {
	Stage _stage;
	Main program;
	// represents the currently open AddressBook wrapped as a bindable GUI element
	ObjectProperty<AddressBook> book = new SimpleObjectProperty<AddressBook>();

	/** Constructor for a PrimaryWindow with an AddressBook. */
	public PrimaryWindow(Main main, AddressBook book) {
		program = main;
		this.book.set(book);
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
		MenuItem newBook = new MenuItem("New");
		MenuItem openBook = new MenuItem("Open...");
		MenuItem saveBook = new MenuItem("Save");
		MenuItem saveAsBook = new MenuItem("Save As...");
		MenuItem closeBook = new MenuItem("Close address book");
		MenuItem exit = new MenuItem("Exit");
		Menu menuEdit = new Menu("Edit");
		MenuItem addEntry = new MenuItem("Add new entry");
		MenuItem editEntry = new MenuItem("Edit entry");
		MenuItem deleteEntry = new MenuItem("Delete entry");
		MenuItem searchEntry = new MenuItem("Search");
		MenuItem importEntry = new MenuItem("Import...");
		MenuItem exportEntry = new MenuItem("Export...");
		Menu menuHelp = new Menu("Help");
		MenuItem about = new MenuItem("About The Address Book");
		MenuItem website = new MenuItem("Visit website");


		// fileMenu buttons
		newBook.setOnAction((ActionEvent t) -> {
			if (book.get() == null) {
				// open a blank AddressBook and display it
				book.set(BookFile.newBook());
				table.itemsProperty().bind(new SimpleObjectProperty<ObservableList<Entry>>(book.get().getBook()));
			} else {
				// use a new window if an AddressBook is open
				try {
					program.newWindow(BookFile.newBook());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		openBook.setOnAction((ActionEvent t) -> {
			// prompt the user to open an AddressBook
			String path = FileWindow.openWindow(stage);
			if (path != null) {
				book.set(BookFile.openBook(path));
			} else {
				return; // do nothing if no AddressBook is selected
			}
			stage.setTitle(book.get().getName());
			// set table data
			table.itemsProperty().bind(new SimpleObjectProperty<ObservableList<Entry>>(book.get().getBook()));
		});
		// buttons disabled if no AddressBook is open
		saveBook.disableProperty().bind(book.isNull());
		saveBook.setOnAction((ActionEvent t) -> {
			// changes are saved, reset flag
			book.get().setModified(false);
			BookFile.saveBook(book.get(), stage);
		});
		saveAsBook.disableProperty().bind(book.isNull());
		saveAsBook.setOnAction((ActionEvent t) -> {
			book.get().setModified(false);
			BookFile.saveAsBook(book.get(), stage);
		});
		closeBook.disableProperty().bind(book.isNull());
		closeBook.setOnAction((ActionEvent t) -> {
			if (!saveModifications(stage, "closing")) {
				return;
			}
			// close the current window
			program.closeWindow(this);
		});
		exit.setOnAction((ActionEvent t) -> {
			if (!saveModifications(stage, "exiting")) {
				return;
			}
			program.closeAllWindows();
		});
		menuFile.getItems().addAll(newBook, openBook, new SeparatorMenuItem(), saveBook, saveAsBook, new SeparatorMenuItem(), closeBook, exit);
		// editMenu buttons
		// button disabled if no AddressBook is open
		addEntry.disableProperty().bind(book.isNull());
		addEntry.setOnAction((ActionEvent t) -> {
			EntryWindow entryWindow = new EntryWindow();
			Optional<Entry> result = entryWindow.showAndWait();
			if (result.isPresent()) {
				book.get().setModified(true);
				book.get().addEntry(result.get());
			} // otherwise user cancelled adding an Entry
		});
		// button disabled if exactly 1 Entry is not selected or no AddressBook is open
		editEntry.disableProperty().bind(Bindings.or(Bindings.notEqual(1, Bindings.size(table.getSelectionModel().getSelectedItems())), book.isNull()));
		editEntry.setOnAction((ActionEvent t) -> {
			// copy Entry to pre-fill input window
			int selected = table.getSelectionModel().getSelectedIndex();
			Entry entry = book.get().getBook().get(selected).clone();
			EntryWindow entryWindow = new EntryWindow(entry);
			Optional<Entry> result = entryWindow.showAndWait();
			if (result.isPresent()) {
				book.get().setModified(true);
				book.get().editEntry(result.get(), selected);
			}
		});
		// button disabled if no Entry is selected or no AddressBook is open
		deleteEntry.disableProperty().bind(Bindings.or(table.getSelectionModel().selectedItemProperty().isNull(), book.isNull()));
		deleteEntry.setOnAction((ActionEvent t) -> {
			//ObservableList<Integer> selected = table.getSelectionModel().getSelectedIndices();
			book.get().getBook().removeAll(table.getSelectionModel().getSelectedItems());
			book.get().setModified(true);
			//for (Integer i : selected) {
			//	book.get().deleteEntry(i);
			//}
		});
		searchEntry.setOnAction((ActionEvent t) -> {
		});
		importEntry.setOnAction((ActionEvent t) -> {
		});
		exportEntry.setOnAction((ActionEvent t) -> {
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
		table.setEditable(false);
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		table.visibleProperty().bind(book.isNotNull());
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

		//table.itemsProperty().bind(new SimpleObjectProperty<ObservableList<Entry>>(book.get().getBook()));
		// build table
		table.getColumns().addAll(lastName, firstName, address, zipcode, phone, email);
		
		// display the window
		layout.getChildren().addAll(menuBar, table);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
		_stage = stage;
	}
	
	public void stop() {
		_stage.requestFocus();
		_stage.close();
	}

	/** Prompts the user if changes to the current AddressBook should be saved and returns whether it can safely be closed. */
	public boolean saveModifications(Stage stage, String action) {
		if (book.get() != null && book.get().isModified()) {
			ConfirmWindow confirmWindow = new ConfirmWindow(action);
			Optional<ButtonType> result = confirmWindow.showAndWait();
			if (result.get() == ButtonType.YES) {
				BookFile.saveBook(book.get(), stage);
			} else if (result.get() == ButtonType.CANCEL) {
				// keep program open
				return false;
			}
			// ButtonType.NO does nothing
		}
		return true;
	}
}