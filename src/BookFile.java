import java.io.File;

import javafx.stage.Stage;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.util.ArrayList;

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
					book.addEntry(entry);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
			book.saveAddressBook();
		}
		return true;
	}

	/** Creates a new file and saves the AddressBook into it. */
	static boolean saveAsBook(AddressBook book, Stage stage) {
		String path = FileWindow.saveWindow(stage);
		book.setPath(path);
		book.saveAddressBook();
		
		
		Entry tester = new Entry();
		tester.setFirstName("jack");
		tester.setLastName("Brigleb");
		search(book,tester);
		
		
		
		
		
		
		return true;
	}
	
	static AddressBook search(AddressBook book, Entry target){
		ArrayList<String> entry = target.toList();
		
		AddressBook temp = book;
		for(int i = 0;i<entry.size();i++){
			AddressBook srcd = new AddressBook();
			
			if(entry.get(i).length()>0){
				for(int j = 0;j<temp.getBook().size();j++){
					if(entry.get(i).equals(temp.getBook().get(j).toList().get(i))){
						srcd.addEntry(temp.getBook().get(j).clone());
					}
				}
				temp = srcd;
				
			}
			
			
		}
		return temp;
	}
	

	/** Closes the currently open AddressBook. */
	static void closeBook() {
		// future iteration
	}
	
	
}