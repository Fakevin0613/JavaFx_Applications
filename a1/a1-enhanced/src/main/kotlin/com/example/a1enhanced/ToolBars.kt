package com.example.a1enhanced

import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.NodeOrientation
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.text.Text

class ToolBars() : HBox() {

    init {
        val toolText1 = Text("Views:")
        val buttonList = Button("List")
        val buttonGrid = Button("Grid")

        buttonList.onAction = EventHandler {
            Model.changeViewNumber(1)
            buttonList.apply {
                isDisable = Model.getViewNumber() == 1
            }
            buttonGrid.apply {
                isDisable = Model.getViewNumber() == 2
            }
        }

        buttonGrid.onAction = EventHandler {
            Model.changeViewNumber(2)
            buttonList.apply {
                isDisable = Model.getViewNumber() == 1
            }
            buttonGrid.apply {
                isDisable = Model.getViewNumber() == 2
            }
        }

        buttonList.apply {
            style = "-fx-pref-width: 50"
            isDisable = Model.getViewNumber() == 1
        }

        buttonGrid.apply {
            style = "-fx-pref-width: 50"
            isDisable = Model.getViewNumber() == 2
        }


        val divider1 = Separator()

        val buttonArchive = CheckBox("Show archived:")
        buttonArchive.isSelected = Model.getShowArchived()
        buttonArchive.selectedProperty().addListener { _, _, newValue ->
            Model.changeShowArchived(newValue)
        }
        buttonArchive.nodeOrientation = NodeOrientation.RIGHT_TO_LEFT;

        val divider2 = Separator()

        val toolTextOrder = Text("Order by:")
        val orderChoice = ChoiceBox(FXCollections.observableArrayList("Length (asc)", "Length (desc)"))
        orderChoice.selectionModel.select(0)
        orderChoice.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            Model.changeAscend(newValue)
        }

        val buttonClear = Button("Clear")
        buttonClear.apply {
            style = "-fx-pref-width: 50"
        }

        buttonClear.onAction = EventHandler {
            Model.clearNotes()
        }

        val toolPaneLeft = ToolBar()
        val toolPaneRight = ToolBar()


        val toolLeft = HBox()
        toolLeft.apply {
            padding = Insets(10.0, 10.0, 10.0, 10.0)
            spacing = 10.0
            alignment = Pos.CENTER
        }
        val toolRight = HBox()
        toolRight.apply {
            padding = Insets(10.0, 10.0, 10.0, 10.0)
            spacing = 10.0
            alignment = Pos.CENTER_LEFT
        }
        val toolCenter = HBox()
        toolCenter.apply {
            padding = Insets(10.0, 10.0, 10.0, 10.0)
            spacing = 10.0
            alignment = Pos.CENTER
        }
        val toolBut = HBox()
        toolBut.apply {
            padding = Insets(10.0, 10.0, 10.0, 10.0)
            spacing = 10.0
            alignment = Pos.CENTER_RIGHT
        }

        toolLeft.children.addAll(toolText1, buttonList, buttonGrid)

        toolCenter.children.addAll(buttonArchive)

        toolRight.children.addAll(toolTextOrder, orderChoice)

        toolBut.children.addAll(buttonClear)

        toolPaneLeft.items.addAll(toolLeft,
            divider1,
            toolCenter,
            divider2,
            toolRight)
        toolPaneLeft.apply {
            padding = Insets(0.0, 0.0, 0.0, 0.0)
            HBox.setHgrow(this, Priority.ALWAYS)
        }

        toolPaneRight.items.addAll(toolBut)
        toolPaneRight.apply {
            padding = Insets(0.0, 0.0, 0.0, 0.0)
            HBox.setHgrow(this, Priority.NEVER)
        }

        children.addAll(toolPaneLeft, toolPaneRight)
    }
}