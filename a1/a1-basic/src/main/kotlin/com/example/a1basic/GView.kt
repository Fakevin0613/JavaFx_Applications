package com.example.a1basic

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.layout.*
import javafx.scene.paint.Color

class GView(subtract: ReadOnlyDoubleProperty) : TilePane(), InvalidationListener {
    var size = subtract
    init {
        Model.addListener(this)
        invalidated(null)
    }

    override fun invalidated(observable: Observable?) {
        this.apply {
            prefWidthProperty().bind(size)
        }
        var notes : List<Pair<String, Boolean>> = Model.getNotes()
        children.clear()
        val listSpecial = VBox()
        listSpecial.apply {
            style = "-fx-pref-height: 225;" + "-fx-pref-width: 225"
            padding = Insets(10.0)
            background = Background(
                BackgroundFill(
                    Color.LIGHTSALMON,
                    CornerRadii(10.0),
                    Insets(10.0)
                )
            )
        }

        val userInput = TextArea()
        userInput.textProperty().addListener { _, _, newValue ->
            userInput.text = newValue
        }

        val inputBox = HBox(userInput)
        inputBox.apply { style = "-fx-pref-width: 205" }
        inputBox.padding = Insets(10.0, 10.0, 5.0, 10.0)
        HBox.setHgrow(inputBox, Priority.NEVER)

        val buttonSubmit = Button("Create")
        buttonSubmit.onAction = EventHandler {
            Model.addNotes(Pair(userInput.text, false))
        }
        buttonSubmit.apply { style = "-fx-pref-width: 205" }
        val buttonBox = HBox(buttonSubmit)
        buttonBox.padding = Insets(5.0, 10.0, 10.0, 10.0)
        HBox.setHgrow(buttonBox, Priority.NEVER)
        listSpecial.children.addAll(inputBox, buttonBox)

        children.add(listSpecial)

        var count = 0
        for (note in notes) {
            var index = count
            count++
            if (!Model.getShowArchived() && note.second) {

            } else {
                var tempNote = VBox()
                tempNote.apply {
                    padding = Insets(10.0)
                    style = "-fx-pref-height: 225;" + "-fx-pref-width: 225"
                    background = Background(
                        BackgroundFill(
                            if (note.second) Color.LIGHTGRAY else Color.LIGHTYELLOW,
                            CornerRadii(10.0),
                            Insets(10.0, 10.0, 10.0, 10.0)
                        )
                    )
                }

                var archivedOrNot = CheckBox("Archived")
                archivedOrNot.padding = Insets(10.0)
                archivedOrNot.isSelected = note.second
                archivedOrNot.selectedProperty().addListener { _, _, newValue ->
                    Model.changeArchived(index, Pair(note.first, newValue))
                }
                HBox.setHgrow(archivedOrNot, Priority.NEVER)

                var content = Label(note.first)
                content.isWrapText = true

                val contentBox = HBox(content)
                contentBox.apply { style = "-fx-pref-height: 205;" + "-fx-pref-width: 205" }
                contentBox.padding = Insets(10.0, 10.0, 5.0, 10.0)
                HBox.setHgrow(contentBox, Priority.NEVER)




                tempNote.children.addAll(
                    contentBox,
                    archivedOrNot
                )
                children.add(tempNote)
            }
        }
    }
}