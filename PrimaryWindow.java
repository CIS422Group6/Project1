import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class PrimaryWindow extends Application{

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		launch(args);
	}

	// create and show the window
	public void start(Stage stage) throws Exception {
		// set window properties
		stage.setTitle("The Address Book");
		stage.setMinHeight(400);
		stage.setMinWidth(400);
		
		// create the layout manager
		VBox layout = new VBox(0);
		Scene scene = new Scene(layout);
		
		// sample database
		ObservableList<Person> data = FXCollections.observableArrayList(
				new Person("John", "Doe", "123 West Lane", "12345"),
				new Person("Jane", "Don", "321 East Street", "54321")
				);
				
		// table that displays the data
		TableView<Person> table = new TableView<Person>();
		
		// build the menu bar
		MenuBar menuBar = new MenuBar();
		// file menu
		Menu menuFile = new Menu("File");
		MenuItem newBook = new MenuItem("New");
		MenuItem openBook = new MenuItem("Open...");
		MenuItem saveBook = new MenuItem("Save");
		MenuItem saveAsBook = new MenuItem("Save As...");
		MenuItem closeBook = new MenuItem("Close address book");
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction((ActionEvent t) -> {
			System.exit(0);
		});
		menuFile.getItems().addAll(newBook, openBook, new SeparatorMenuItem(), saveBook, saveAsBook, new SeparatorMenuItem(), closeBook, exit);
		
		// edit menu with initial functionality
		Menu menuEdit = new Menu("Edit");
		MenuItem addPerson = new MenuItem("Add new person");
		addPerson.setOnAction((ActionEvent t) -> {
			PersonDialog personDialog = new PersonDialog(new Person());
			Optional<Person> result = personDialog.showAndWait();
			if (result.isPresent()) {
				data.add(result.get());
			}
		});
		MenuItem editPerson = new MenuItem("Edit person");
		editPerson.setOnAction((ActionEvent t) -> {
			int selected = table.getSelectionModel().getSelectedIndex();
			PersonDialog personDialog = new PersonDialog(data.get(selected));
			Optional<Person> result = personDialog.showAndWait();
			if (result.isPresent()) {
				data.set(selected, result.get());
			}

		});
		MenuItem deletePerson = new MenuItem("Delete person");
		deletePerson.setOnAction((ActionEvent t) -> {
			int selected = table.getSelectionModel().getSelectedIndex();
			data.remove(selected);
		});
		menuEdit.getItems().addAll(addPerson, editPerson, deletePerson);
		
		// help menu
		Menu menuHelp = new Menu("Help");
		MenuItem about = new MenuItem("About The Address Book");
		MenuItem website = new MenuItem("Visit website");
		menuHelp.getItems().addAll(about, website);
		
		menuBar.getMenus().addAll(menuFile, menuEdit, menuHelp);


		// build the table
		table.setEditable(false);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		TableColumn<Person, String> firstName = new TableColumn<Person, String>("First Name");
		firstName.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
		firstName.setMinWidth(80);
		TableColumn<Person, String> lastName = new TableColumn<Person, String>("Last Name");
		lastName.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
		lastName.setMinWidth(80);
		TableColumn<Person, String> address = new TableColumn<Person, String>("Address");
		address.setCellValueFactory(new PropertyValueFactory<Person, String>("address"));
		address.setMinWidth(80);
		TableColumn<Person, String> zipcode = new TableColumn<Person, String>("Zipcode");
		zipcode.setCellValueFactory(new PropertyValueFactory<Person, String>("zipcode"));
		zipcode.setMinWidth(80);
		
		table.setItems(data);
		table.getColumns().addAll(firstName, lastName, address, zipcode);

		// add the elements to the layout manager
		layout.getChildren().addAll(menuBar, table);
		VBox.setVgrow(table, Priority.ALWAYS);

		// display the window
		stage.setScene(scene);
		stage.sizeToScene();;
		stage.show();
	}
}