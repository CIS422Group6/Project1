import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 * A collection of functions to read an AddressBook into program memory and write to disk.
 */

public class BookFile {
	/** Creates a new instance of an empty AddressBook and returns it. */
	static AddressBook newBook() {
		AddressBook book = new AddressBook();
		return book;
	}

	/** Loads a book from a file (at a given path) into program memory. */
	static AddressBook openBook(String file) {
		// empty list of entries
		AddressBook book = new AddressBook();
		ObservableList<Entry> bot = FXCollections.observableArrayList();

		File bookSource = new File(file);
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuild = dbf.newDocumentBuilder();
			Document dBook = dBuild.parse(bookSource);

			NodeList entryList = dBook.getElementsByTagName("entry");

			// when we originally had this method returning an AddressBook this was possible but it's not at the moment. Will fix.
			book.setName(dBook.getElementsByTagName("bookName").item(0).getTextContent());
			Entry entry;

			for (int i = 0; i < entryList.getLength(); i++) {
				Node entryNode = entryList.item(i);

				if (entryNode.getNodeType() == Node.ELEMENT_NODE) {
					Element entryEle = (Element) entryNode;
					entry = new Entry(entryEle.getElementsByTagName("firstName").item(0).getTextContent(),
							entryEle.getElementsByTagName("lastName").item(0).getTextContent(),
							entryEle.getElementsByTagName("address").item(0).getTextContent(),
							entryEle.getElementsByTagName("zipcode").item(0).getTextContent());
					bot.add(entry);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		book.setBook(bot);
		return book;
	}

	/** Writes changes to an AddressBook back into its file. */
	static boolean saveBook(AddressBook book, Stage stage) {
		// check if file exists
		if (book.getPath() == "") {
			// prompt user for location
			saveAsBook(book, stage);
		} else {
			book.saveAddressBook();
		}
		return true;
	}

	/** Creates a new file and saves the AddressBook into it. */
	static boolean saveAsBook(AddressBook book, Stage stage) {
		String path = FileWindow.saveWindow(stage);
		book.setPath(path);
		book.saveAddressBook();
		return true;
	}

	/** Closes the currently open AddressBook. */
	static void closeBook() {
		// future iteration
	}
}