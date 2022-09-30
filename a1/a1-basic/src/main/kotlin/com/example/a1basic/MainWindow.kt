package com.example.a1basic

import javafx.application.Application
import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.geometry.NodeOrientation
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.stage.Stage


class MainWindow : Application() {


    override fun start(stage: Stage) {

        var notes : MutableList<Pair<String, Boolean>> = mutableListOf()
        notes.add(Pair("Note Simple", false))
        notes.add(Pair("Notelonggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg" +
                "ggggggggggggggggggggggggggggggggggggggggggggggggg", false))
        notes.add(Pair("Note Archived", true))
        notes.add(Pair("Note high \na\na\na\na\na\na\na\n", false))

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

//        val toolTextArchive = Text("Show archived:")
        val buttonArchive = CheckBox("Show archived:")
        buttonArchive.nodeOrientation = NodeOrientation.RIGHT_TO_LEFT;
        val divider2 = Separator()

        val toolTextOrder = Text("Order by:")
        val orderChoice = ChoiceBox(FXCollections.observableArrayList("Length (asc)", "Length (desc)"))
        orderChoice.selectionModel.select(0)
        val buttonClear = Button("Clear")
        buttonClear.apply {
            style = "-fx-pref-width: 50"
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

// list view area
        var listView = VBox()

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
        userInput.prefWidthProperty().bind(toolPane.widthProperty().subtract(125))
        val inputBox = HBox(userInput)
        inputBox.padding = Insets(10.0, 5.0, 10.0, 10.0)
        HBox.setHgrow(inputBox, Priority.ALWAYS)



        val buttonSubmit = Button("Create")
        buttonSubmit.apply { style = "-fx-pref-height: 42;" + "-fx-pref-width: 75" }
        val buttonBox = HBox(buttonSubmit)
        buttonBox.padding = Insets(10.0, 10.0, 10.0, 5.0)
        HBox.setHgrow(buttonBox, Priority.NEVER)

        listSpecial.children.addAll(inputBox, buttonBox)
        listView.children.add(listSpecial)

        listView.apply { for (note in notes) {
            var tempNote = HBox()
            tempNote.apply {
                padding = Insets(10.0)
                background = Background(
                    BackgroundFill(
                        if (note.second)  Color.LIGHTGRAY else Color.LIGHTYELLOW,
                        CornerRadii(10.0),
                        Insets(5.0, 10.0, 5.0, 10.0)))
            }

            var archivedOrNot = CheckBox("Archived")
            archivedOrNot.padding = Insets(10.0)
            archivedOrNot.isSelected = note.second
            HBox.setHgrow(archivedOrNot, Priority.NEVER)

            var content = Label(note.first)
            content.isWrapText = true
            content.prefWidthProperty().bind(toolPane.widthProperty().subtract(125))
            content.padding = Insets(10.0)
            HBox.setHgrow(content, Priority.ALWAYS)


            tempNote.children.addAll(content,
                archivedOrNot)
            children.add(tempNote)

        } }
        val scroll = ScrollPane(listView).apply {
            hbarPolicy = ScrollPane.ScrollBarPolicy.ALWAYS
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
    Application.launch(MainWindow::class.java)
}