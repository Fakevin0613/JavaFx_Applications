package com.example.a2basic

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.beans.binding.DoubleBinding
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import java.text.DecimalFormat




class DataEntry(thewidth: DoubleBinding) : VBox(), InvalidationListener {
    val thiswidth = thewidth
    init {
        Model.addListener(this)
        invalidated(null)
    }
    override fun invalidated(observable: Observable?) {
        children.clear()
        val title = Label("Dataset name: " + Model.getAlls()[Model.getDataNum()].first)

        var allTextField = List(Model.getAlls()[Model.getDataNum()].second.size) {TextField().apply { isFocusTraversable = false }}
        allTextField[Model.getLastFocus()].isFocusTraversable = true
        val rows = VBox()
        for((count, data) in Model.getAlls()[Model.getDataNum()].second.withIndex()){
            val aRow = HBox()
            val entryNumber = Label("Entry #$count")
            val newInput = allTextField[count]
            newInput.text = data.toString()
            newInput.positionCaret(newInput.text.length);
            newInput.apply {
                HBox.setHgrow(newInput, Priority.ALWAYS)
            }

            if(count == Model.getLastFocus()){
                newInput.requestFocus()
            }

            newInput.textProperty().addListener {_, _, newValue ->
                newInput.text = newValue
                if(newValue.matches("-?\\d+(\\.\\d+)?".toRegex())){
                    val df = DecimalFormat()
                    Model.modifyData(Model.getAlls()[Model.getDataNum()].first, count, df.parse(newInput.text).toDouble())
                }
            }

            val inputBox = HBox(newInput)
            inputBox.apply {
                HBox.setHgrow(inputBox, Priority.ALWAYS)
            }

            val buttonDelete = Button("X")
            buttonDelete.isFocusTraversable = false
            buttonDelete.onAction = EventHandler {
                Model.deleteData(Model.getAlls()[Model.getDataNum()].first, count)
            }
            if(Model.getAlls()[Model.getDataNum()].second.size == 1){
                buttonDelete.isDisable = true
            }
            aRow.children.addAll(entryNumber, inputBox, buttonDelete)
            aRow.alignment = Pos.CENTER_LEFT
            aRow.spacing = 10.0
            rows.children.add(aRow)
        }

        rows.apply {
            spacing = 10.0
            HBox.setHgrow(rows, Priority.ALWAYS)
        }



        val addButton = Button("Add Entry")
        addButton.apply {
            HBox.setHgrow(addButton, Priority.ALWAYS)
            prefWidthProperty().bind(thiswidth)
        }
        addButton.onAction = EventHandler {
            Model.addData(Model.getAlls()[Model.getDataNum()].first, 0.0)
        }
        children.addAll(title, rows, addButton)
        this.apply {
            spacing = 10.0
            padding = Insets(5.0, 5.0, 5.0, 5.0)
            prefWidthProperty().bind(thiswidth)
        }
    }
}
