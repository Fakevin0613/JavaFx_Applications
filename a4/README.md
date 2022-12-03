
# A4 Notes for Android
Kevin Ke (q4ke 20872321)
 
## Setup
* macOS 12.2.1
* IntelliJ IDEA 2022.2.2 (Community Edition)
* kotlin.jvm 1.7.10
* Java SDK 17.0.4 (corretto)
 
## Enhancement 
### Search by Title
* Next to the "show archived" toggle, there is a search field
* By entering the words or characters, the list will automatically filter out the notes, of which the Title contains those words or characters
* This make it easier for user to find the notes they want based on keywords
* however, for notes with empty title, the filter functions does not works, because I assume those notes are meaningless to user
* it only works for notes with non-empty title
