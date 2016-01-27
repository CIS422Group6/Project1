# this is generally unreliable so please use "compile" instead

JFLAGS = -g -d ./bin -cp ./bin
JC = javac

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

SOURCES = \
	src/Entry.java \
	src/AddressBook.java \
	src/BookFile.java \
	src/ConfirmWindow.java \
	src/FileWindow.java \
	src/EntryWindow.java \
	src/PrimaryWindow.java \
	src/Main.java

default: classes

classes: $(SOURCES:.java=.class)
	$(JC) $(JFLAGS) $<

clean:
	$(RM) *.class
