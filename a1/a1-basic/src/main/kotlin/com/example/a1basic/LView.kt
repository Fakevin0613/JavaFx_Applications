package com.example.a1basic

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.beans.binding.DoubleBinding
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.layout.*
import javafx.scene.paint.Color

class LView(subtract: DoubleBinding) : VBox(), InvalidationListener {
    var size = subtract
    init {
        Model.addListener(this)
        invalidated(null)
    }

    override fun invalidated(observable: Observable?) {
        var notes = Model.getNotes()

        children.clear()

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
            Model.addNotes(Pair(userInput.text, false))
        }
        buttonSubmit.apply { style = "-fx-pref-height: 42;" + "-fx-pref-width: 75" }
        val buttonBox = HBox(buttonSubmit)
        buttonBox.padding = Insets(10.0, 10.0, 10.0, 5.0)
        HBox.setHgrow(buttonBox, Priority.NEVER)
        listSpecial.children.addAll(inputBox, buttonBox)

        children.add(listSpecial)

        for (note in notes) {
            var tempNote = HBox()
            tempNote.apply {
                padding = Insets(10.0)
                background = Background(
                    BackgroundFill(
                        if (note.second)  Color.LIGHTGRAY else Color.LIGHTYELLOW,
                        CornerRadii(10.0),
                        Insets(5.0, 10.0, 5.0, 10.0)
                    )
                )
            }

            var archivedOrNot = CheckBox("Archived")
            archivedOrNot.padding = Insets(10.0)
            archivedOrNot.isSelected = note.second
            HBox.setHgrow(archivedOrNot, Priority.NEVER)

            var content = Label(note.first)
            content.isWrapText = true
            content.prefWidthProperty().bind(size)
            content.padding = Insets(10.0)
            HBox.setHgrow(content, Priority.ALWAYS)


            tempNote.children.addAll(content,
                archivedOrNot)
            children.add(tempNote)

        }
    }
}