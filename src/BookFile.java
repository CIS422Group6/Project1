import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

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

	/** Loads a book from a file (at a given path) into an AddressBook and returns it. */
	static AddressBook openBook(String file) {
		File bookSource = new File(file);

		// create a blank AddressBook to fill
		AddressBook book = new AddressBook();
		book.setPath(file);
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuild = dbf.newDocumentBuilder();
			Document dBook = dBuild.parse(bookSource);

			book.setName(dBook.getElementsByTagName("bookName").item(0).getTextContent());

			NodeList entryList = dBook.getElementsByTagName("entry");
			for (int i = 0; i < entryList.getLength(); i++) {
				Node entryNode = entryList.item(i);

				if (entryNode.getNodeType() == Node.ELEMENT_NODE) {
					Element entryEle = (Element) entryNode;
					Entry entry = new Entry(entryEle.getElementsByTagName("firstName").item(0).getTextContent(),
							entryEle.getElementsByTagName("lastName").item(0).getTextContent(),
							entryEle.getElementsByTagName("delivery").item(0).getTextContent(),
							entryEle.getElementsByTagName("second").item(0).getTextContent(),
							entryEle.getElementsByTagName("city").item(0).getTextContent(),
							entryEle.getElementsByTagName("state").item(0).getTextContent(),
							entryEle.getElementsByTagName("zipcode").item(0).getTextContent(),
							entryEle.getElementsByTagName("phone").item(0).getTextContent(),
							entryEle.getElementsByTagName("email").item(0).getTextContent());
					book.getBook().add(entry);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			String data = s.toString();
			ConfirmWindow confirmWindow = new ConfirmWindow(data, ConfirmWindow.PROGRAM_ERROR);
			confirmWindow.showAndWait();
		}
		return book;
	}

	/** Writes changes to an AddressBook back into its file. */
	static boolean saveBook(AddressBook book, Stage stage) {
		// check if file exists
		if (book.getPath() == "") {
			// prompt user for location
			saveAsBook(book, stage);
		} else {
			// save into existing file
			book.saveAddressBook();
		}
		return true;
	}

	/** Creates a new file and saves the AddressBook into it. */
	static boolean saveAsBook(AddressBook book, Stage stage) {
		String path = FileWindow.chooseFile(stage, FileWindow.SAVE_XML);
		if (path == null) {
			return false;
		}
		book.setPath(path);
		book.saveAddressBook();
		return true;
	}

	/** Returns an AddressBook that only contains entries matching the given Entry. */
	static AddressBook search(AddressBook book, Entry entry){
		ArrayList<String> entryList = entry.toList();
		AddressBook result = new AddressBook();

		// check all entries
		for (int i = 0; i < book.getBook().size(); i++) {
			// iterate over all fields
			ArrayList<String> bookList = book.getBook().get(i).toList();
			boolean match = true;
			for (int j = 0; j < entryList.size(); j++) {
				// check if field is a search parameter
				if (entryList.get(j) != null && entryList.get(j).length() > 0) {
					// if entry does not match all specified fields, skip to the next
					if (!bookList.get(j).contains(entryList.get(j))) {
						match = false;
						break;
					}
				}
			}
			if (match) {
				result.getBook().add(book.getBook().get(i));
			}
		}
		return result;
	}

	/** Returns a list of Entry types as imported from a .tsv file. */
	static ArrayList<Entry> importTSV(String file) {
		ArrayList<Entry> imported = new ArrayList<Entry>();
		try {
			// read in the file
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String current = "";
			boolean skipped = false;
			while ((current = reader.readLine()) != null) {
				String[] fields = current.split("\t");
				Entry entry = new Entry();
				boolean test = true;
				// only add the first 8 fields, passing them through validation
				test = test && entry.setCity(fields[0]);
				test = test && entry.setState(fields[1]);
				test = test && entry.setZipcode(fields[2]);
				test = test && entry.setDelivery(fields[3]);
				test = test && entry.setSecond(fields[4]);
				test = test && entry.setLastName(fields[5]);
				test = test && entry.setFirstName(fields[6]);
				test = test && entry.setPhone(fields[7]);
				if (test) {
					imported.add(entry);
				} else {
					skipped = true;
				}
			}
			reader.close();
			if (skipped) {
				ConfirmWindow confirmWindow = new ConfirmWindow("Not all entries could be imported.", ConfirmWindow.PROGRAM_WARNING);
				confirmWindow.showAndWait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imported;
	}

	/** Export the entries in the AddressBook into a standard .tsv file. */
	static void exportTSV(AddressBook book, String path){
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(path));
			for (Entry entry : book.getBook()) {
				out.write(entry.getCity());
				out.write("\t");
				out.write(entry.getState());
				out.write("\t");
				out.write(entry.getZipcode());
				out.write("\t");
				out.write(entry.getDelivery());
				out.write("\t");
				out.write(entry.getSecond());
				out.write("\t");
				out.write(entry.getLastName());
				out.write("\t");
				out.write(entry.getFirstName());
				out.write("\t");
				out.write(entry.getPhone());
				out.write("\n");
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}