package com.example.a1basic

import javafx.application.Application
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.NodeOrientation
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.text.Text
import javafx.stage.Stage


class Main : Application() {


    override fun start(stage: Stage) {

// toolbar area
        val toolText1 = Text("Views:")
        val buttonList = Button("List")
        buttonList.apply {
            style = "-fx-pref-width: 50"
        }
        val buttonGrid = Button("Grid")
        buttonGrid.apply {
            style = "-fx-pref-width: 50"
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
        val toolPane = HBox()


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


        toolPane.children.addAll(toolPaneLeft, toolPaneRight)

//listView Area

        val listView = LView(toolPane.widthProperty().subtract(125))
        val scroll = ScrollPane(listView).apply {
            hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        }

////////////////////////////////////////////////////////////////////////////////
        val root = BorderPane()
        root.top = toolPane
        root.center = scroll



        val scene = Scene(root, 800.0, 600.0)


        stage.title = "CS349 - A1 Notes - q4ke"
        stage.scene = scene
        stage.show()
        stage.apply { minWidth = 640.0; minHeight = 480.0}
    }

}

fun main() {
    Application.launch(Main::class.java)
}
