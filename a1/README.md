# A1 Notes
Kevin Ke (q4ke 20872321)
 
## Setup
* macOS 12.2.1
* IntelliJ IDEA 2022.2.2 (Community Edition)
* kotlin.jvm 1.7.10
* Java SDK 17.0.4 (corretto)
 
## Enhancement 
### Allow a text file to be dragged onto the application to create a note.
* In both ListView and GridView, the user can drag a txt file to the drag area to import notes
* After dragging the txt file, a note will be created with all the contents in the txt file
### Add more properties / tags to notes than “archived”.
* In both ListView and GridView, the notes are classified to be urgent/archived. 
* The urgent notes will be on top of all normal and archived notes so that user can pay more attention to them.
* User can choose the checkbox to classify them
* A note cannot be both urgent and archived at the same time because it is common sense that an urgent note would not be archived.
