package com.example.a1enhanced

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.beans.binding.DoubleBinding
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.input.TransferMode
import javafx.scene.layout.*
import javafx.scene.paint.Color
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths


class LView(subtract: DoubleBinding) : VBox(), InvalidationListener {
    var size = subtract
    init {
        Model.addListener(this)
        invalidated(null)
    }

    override fun invalidated(observable: Observable?) {
        val notes : List<Pair<String, Int>> = Model.getNotes()

        children.clear()
// input note
        val listSpecial = HBox()
        listSpecial.apply {
            style = "-fx-pref-height: 62"
            padding = Insets(10.0)
            background = Background(
                BackgroundFill(
                    Color.LIGHTSALMON,
                    CornerRadii(10.0),
                    Insets(10.0)))
        }
        val userInput = TextArea()
        userInput.textProperty().addListener { _, _, newValue ->
            userInput.text = newValue
        }
        userInput.prefWidthProperty().bind(size)


        val inputBox = HBox(userInput)
        inputBox.padding = Insets(10.0, 5.0, 10.0, 10.0)
        HBox.setHgrow(inputBox, Priority.ALWAYS)



        val buttonSubmit = Button("Create")
        buttonSubmit.onAction = EventHandler {
            Model.addNotes(Pair(userInput.text, 0))
        }
        buttonSubmit.apply { style = "-fx-pref-height: 42;" + "-fx-pref-width: 75" }
        val buttonBox = HBox(buttonSubmit)
        buttonBox.padding = Insets(10.0, 10.0, 10.0, 5.0)
        HBox.setHgrow(buttonBox, Priority.NEVER)
        listSpecial.children.addAll(inputBox, buttonBox)

        children.add(listSpecial)
//new feature drag

        val drag = HBox()
        drag.onDragOver = EventHandler { event ->
            if (event.gestureSource !== drag
                && event.dragboard.hasFiles()
            ) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(*TransferMode.COPY_OR_MOVE)
            }
            event.consume()
        }

        drag.setOnDragDropped { event ->
            val db = event.dragboard
            var success = false
            if (db.hasFiles()) {
                val content = String(Files.readAllBytes(Paths.get(db.files.get(0).toString())), StandardCharsets.UTF_8)
                Model.addNotes(Pair(content, 0))
                success = true
            }
            event.isDropCompleted = success
            event.consume()
        }
        drag.apply {
            style = "-fx-pref-height: 62"
            padding = Insets(10.0)
            background = Background(
                BackgroundFill(
                    Color.LIGHTSALMON,
                    CornerRadii(10.0),
                    Insets(10.0)))
        }
        val dragNotice = Label("Drag a .txt file to add new note")
        dragNotice.padding = Insets(20.0)
        drag.children.add(dragNotice)
        drag.alignment = Pos.CENTER_LEFT
        children.add(drag)

        // notes urgent

        var count = 0
        for (note in notes) {
            val index = count
            count++
            if(note.second == 0 || note.second == 1){

            }
            else {
                val tempNote = HBox()
                tempNote.apply {
                    padding = Insets(10.0)
                    background = Background(
                        BackgroundFill(
                            Color.LIGHTBLUE,
                            CornerRadii(10.0),
                            Insets(5.0, 10.0, 5.0, 10.0)
                        )
                    )
                }

                val buttons = VBox()

                val urgentOrNot = CheckBox("Urgent")
                urgentOrNot.padding = Insets(10.0, 10.0, 0.0, 10.0)
                urgentOrNot.isSelected = (note.second == 2)
                urgentOrNot.selectedProperty().addListener { _, _, newValue ->
                    val output = if(newValue == true) 2 else 0
                    Model.changeArchived(index, Pair(note.first, output))
                }

                val archivedOrNot = CheckBox("Archived")
                archivedOrNot.padding = Insets(10.0)
                archivedOrNot.isSelected = (note.second == 1)
                archivedOrNot.selectedProperty().addListener { _, _, newValue ->
                    val output = if(newValue == true) 1 else 0
                    Model.changeArchived(index, Pair(note.first, output))
                }

                buttons.children.addAll(urgentOrNot, archivedOrNot)

                val content = Label(note.first)
                content.isWrapText = true
                content.prefWidthProperty().bind(size)
                content.padding = Insets(10.0)
                HBox.setHgrow(content, Priority.ALWAYS)


                tempNote.children.addAll(content,
                    buttons)
                children.add(tempNote)
            }
        }
        // notes and archived
        count = 0
        for (note in notes) {
            val index = count
            count++
            if(note.second == 2){

            }
            else if(!Model.getShowArchived() && note.second == 1){

            }
            else {
                val tempNote = HBox()
                tempNote.apply {
                    padding = Insets(10.0)
                    background = Background(
                        BackgroundFill(
                            if (note.second == 1)  Color.LIGHTGRAY else Color.LIGHTYELLOW,
                            CornerRadii(10.0),
                            Insets(5.0, 10.0, 5.0, 10.0)
                        )
                    )
                }

                val buttons = VBox()

                val urgentOrNot = CheckBox("Urgent")
                urgentOrNot.padding = Insets(10.0, 10.0, 0.0, 10.0)
                urgentOrNot.isSelected = (note.second == 2)
                urgentOrNot.selectedProperty().addListener { _, _, newValue ->
                    val output = if(newValue == true) 2 else 0
                    Model.changeArchived(index, Pair(note.first, output))
                }

                val archivedOrNot = CheckBox("Archived")
                archivedOrNot.padding = Insets(10.0)
                archivedOrNot.isSelected = (note.second == 1)
                archivedOrNot.selectedProperty().addListener { _, _, newValue ->
                    val output = if(newValue == true) 1 else 0
                    Model.changeArchived(index, Pair(note.first, output))
                }

                buttons.children.addAll(urgentOrNot, archivedOrNot)

                val content = Label(note.first)
                content.isWrapText = true
                content.prefWidthProperty().bind(size)
                content.padding = Insets(10.0)
                HBox.setHgrow(content, Priority.ALWAYS)


                tempNote.children.addAll(content,
                    buttons)
                children.add(tempNote)
            }
        }
    }
}