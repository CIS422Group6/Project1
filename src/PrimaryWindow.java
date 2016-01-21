//import java.io.File;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The main window that contains the GUI and front-end functionality of the program.
 */

public class PrimaryWindow extends Application {
	AddressBook book = new AddressBook();

	/** Create and show the main program window. */
	@SuppressWarnings("unchecked")
	public void start(Stage stage) throws Exception {
		// set window properties
		stage.setTitle("The Address Book");
		stage.setMinHeight(400);
		stage.setMinWidth(400);
		
		// create the layout manager
		VBox layout = new VBox(0);
		Scene scene = new Scene(layout, 400, 400);
	
		// table that displays the data
		TableView<Entry> table = new TableView<Entry>();
		
		// build the menu bar
		MenuBar menuBar = new MenuBar();
		
		// file menu
		Menu menuFile = new Menu("File");
		MenuItem newBook = new MenuItem("New");
		newBook.setOnAction((ActionEvent t) -> {
			book = BookFile.newBook();
			// update table gui
	        table.setItems(book.getBook());
			table.setVisible(true);
		});
		MenuItem openBook = new MenuItem("Open...");
		openBook.setOnAction((ActionEvent t) -> {
			String path = FileWindow.openWindow(stage);
			if (path != null) {
				book.setPath(path);
			}
			stage.setTitle(book.getName());
			book = BookFile.openBook(path);
	        // update table gui
	        table.setItems(book.getBook());
	        table.setVisible(true);
		});
		MenuItem saveBook = new MenuItem("Save");
		saveBook.setOnAction((ActionEvent t) -> {
			BookFile.saveBook(book, stage);
		});
		MenuItem saveAsBook = new MenuItem("Save As...");
		saveAsBook.setOnAction((ActionEvent t) -> {
			BookFile.saveAsBook(book, stage);
		});
		MenuItem closeBook = new MenuItem("Close address book");
		closeBook.setOnAction((ActionEvent t) -> {
			// check if book is saved then close
			book = null;
			table.setVisible(false);
		});
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction((ActionEvent t) -> {
			// check if book(s) are saved
			System.exit(0);
		});
		menuFile.getItems().addAll(newBook, openBook, new SeparatorMenuItem(), saveBook, saveAsBook, new SeparatorMenuItem(), closeBook, exit);
		
		// edit menu
		Menu menuEdit = new Menu("Edit");
		MenuItem addPerson = new MenuItem("Add new person");
		addPerson.setOnAction((ActionEvent t) -> {
			EntryWindow entryWindow = new EntryWindow();
			Optional<Entry> result = entryWindow.showAndWait();
			if (result.isPresent()) {
				book.addEntry(result.get());
			}
		});
		MenuItem editPerson = new MenuItem("Edit person");
		editPerson.setOnAction((ActionEvent t) -> {
			int selected = table.getSelectionModel().getSelectedIndex();
			Entry entry = book.getBook().get(selected).clone();
			EntryWindow entryWindow = new EntryWindow(entry);
			Optional<Entry> result = entryWindow.showAndWait();
			if (result.isPresent()) {
				book.editEntry(result.get(), selected);
			}

		});
		MenuItem deletePerson = new MenuItem("Delete person");
		deletePerson.setOnAction((ActionEvent t) -> {
			// only if an entry is selected
			int selected = table.getSelectionModel().getSelectedIndex();
			book.deleteEntry(selected);
		});
		menuEdit.getItems().addAll(addPerson, editPerson, deletePerson);
		
		// help menu
		Menu menuHelp = new Menu("Help");
		MenuItem about = new MenuItem("About The Address Book");
		MenuItem website = new MenuItem("Visit website");
		menuHelp.getItems().addAll(about, website);
		
		menuBar.getMenus().addAll(menuFile, menuEdit, menuHelp);

		// build the table
		VBox.setVgrow(table, Priority.ALWAYS);
		table.setEditable(false);
		table.setVisible(true);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
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

        table.setItems(book.getBook());
		table.getColumns().addAll(firstName, lastName, address, zipcode);

		// add the elements to the layout manager
		layout.getChildren().addAll(menuBar, table);

		// display the window
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}
}