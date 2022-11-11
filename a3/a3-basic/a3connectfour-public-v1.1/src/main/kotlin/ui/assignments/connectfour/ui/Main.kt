package ui.assignments.connectfour.ui

import javafx.animation.AnimationTimer
import javafx.animation.ScaleTransition
import javafx.animation.Transition
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape
import javafx.scene.text.Font
import javafx.stage.Stage
import javafx.util.Duration
import ui.assignments.connectfour.model.Model
import ui.assignments.connectfour.model.Player


class Main(stage: Stage): Pane(){
    private val gridNode = ImageView(javaClass.getResource("/ui/assignments/connectfour/grid_${Model.width}x${Model.height}.png").toString())
    private val playerOne = Label("Player #1")
    private val playerTwo = Label("Player #2")
    private val theScene = Scene(this, 360.0 + Model.width * 80, 240.0 + Model.height * 80)
    private var nodes: ArrayList<Circle>
    private var allPieces: ArrayList<Shape>

    private var insertColumn = -1
    private var inPlace = false
    private lateinit var currentPiece: Shape
    private var currentPlayer = Player.NONE

    init {
        stage.scene = theScene
        allPieces = ArrayList()
        Model.onGameWin.addListener {
            _, _, _->
            val winState = Label("Player ${Model.onGameWin.value} Wins!\n  Game Over!").apply {
                font = Font.font("Arial")
                textFill = Color.ORANGE
            }
            winState.layoutX = width / 2 - 35.0
            winState.layoutY = height / 2 - 35.0
            children.add(winState)

            val transition = ScaleTransition(Duration.millis(2000.0), winState).apply {
                cycleCount = 5
                byX = 6.0;
                byY = 6.0
                isAutoReverse = true
            }
            transition.play()
        }

        Model.onGameDraw.addListener {
                _, _, _->
            val winState = Label("     Draw!\nGame Over!").apply {
                font = Font.font("Arial")
                textFill = Color.GREY
            }
            winState.layoutX = width / 2 - 35.0
            winState.layoutY = height / 2 - 35.0
            children.add(winState)

            val transition = ScaleTransition(Duration.millis(2000.0), winState).apply {
                cycleCount = 5
                byX = 6.0;
                byY = 6.0
                isAutoReverse = true
            }
            transition.play()
        }

        Model.onPieceDropped.addListener{_, _, _->
            var distance = 0.0
            var isHit = false

            val timer = object: AnimationTimer() {
                override fun handle(now: Long) {
                    if(!isHit){
                        currentPiece.layoutY += 10.0;
                        distance += 10.0
                        isHit = checkPiecesIntersection(currentPiece)
                    }
                    else{
                        this.stop()
                        println(distance)
                        currentPiece.onMouseDragged = EventHandler {  }
                        currentPiece.onMouseReleased = EventHandler {  }
                        currentPiece.onMousePressed = EventHandler {  }
                    }
                }
            }
            timer.start()
        }

        Model.onNextPlayer.addListener { _, _, new ->
            if(new == Player.ONE){
                currentPlayer = Player.ONE
                val newPiece = Circle(
                    0.0,
                    0.0,
                    39.5)
                newPiece.layoutX = 100.0
                newPiece.layoutY = 100.0
                newPiece.fill = Color.RED
                newPiece.stroke = Color.BLACK
                children.add(newPiece)
                allPieces.add(newPiece)
                newPiece.toBack()

                setDragListeners(newPiece)
            }
            else if(new == Player.TWO){
                currentPlayer = Player.TWO
                val newPiece = Circle(
                    0.0,
                    0.0,
                    39.5)
                newPiece.layoutX = width - 100.0
                newPiece.layoutY = 100.0
                newPiece.fill = Color.YELLOW
                newPiece.stroke = Color.BLACK
                children.add(newPiece)
                allPieces.add(newPiece)
                newPiece.toBack()

                setDragListeners(newPiece)
            }
        }

        val players = BorderPane().apply { padding = Insets(10.0, 10.0, 10.0, 10.0) }
        players.prefWidthProperty().bind(this.widthProperty())
        playerOne.font = Font.font("Arial", 30.0)
        playerTwo.font = Font.font("Arial", 30.0)
        players.left = playerOne
        players.right = playerTwo
        children.add(players)

        val startButton = Rectangle(400.0, 100.0, Color.AQUA).apply {
            x = (theScene.width - 400.0) / 2; y = 75.0 }


        val clickToStart = Label("Click here to start game!").apply {
            layoutX = (theScene.width - 400.0) / 2 + 60.0; layoutY = 110.0; font = Font.font("Arial", 24.0)}
        children.addAll(startButton, clickToStart)

        startButton.setOnMouseClicked {
            this.children.remove(clickToStart)
            this.children.remove(startButton)
            Model.startGame()
        }

        clickToStart.setOnMouseClicked {
            this.children.remove(clickToStart)
            this.children.remove(startButton)
            Model.startGame()
        }


        gridNode.fitHeight = Model.height * 80.0
        gridNode.fitWidth = Model.width * 80.0
        gridNode.layoutY = 200.0
        gridNode.layoutX = 180.0
        children.add(gridNode)

        nodes = ArrayList()
        for(num in 1..Model.width){
            nodes.add(Circle(
                140.0 + 80.0 * num,
                160.0,
                1.0)
                .apply { stroke = Color.TRANSPARENT
                    fill = Color.TRANSPARENT
                })
        }
        children.addAll(nodes)

        val floor = Rectangle(width, 40.0, Color.TRANSPARENT).apply {
            x = 0.0
            y = theScene.height - 40.0 }
        children.addAll(floor)
        allPieces.add(floor)
    }


    private fun setDragListeners(shape: Circle) {
        // record a delta distance for the drag and drop operation.
        var clickX = 0.0
        var clickY = 0.0
        shape.onMousePressed = EventHandler {
            clickX =  it.x
            clickY =  it.y
            shape.cursor = Cursor.NONE
        }

        shape.onMouseReleased = EventHandler {
            shape.cursor = Cursor.HAND
            if(inPlace){
                currentPiece = shape
                Model.dropPiece(insertColumn)
            }
            else{
                val originalX = if(currentPlayer == Player.ONE) 100.0 else width - 100.0
                val originalY = 100.0
                val currentX = shape.layoutX
                val currentY = shape.layoutY


                val timer = object: AnimationTimer() {
                    var a = 0
                    override fun handle(now: Long) {
                        if(a < 10){
                            shape.layoutX += (originalX - currentX) / 10
                            shape.layoutY += (originalY - currentY) / 10
                            a++
                        }
                        else{
                            this.stop()
                        }
                    }
                }

                timer.start()
            }
        }

        shape.onMouseDragged = EventHandler {
            shape.layoutX = it.sceneX - clickX
            shape.layoutY = it.sceneY - clickY
            if(shape.layoutX <= 40.0){
                shape.layoutX = 40.0
            }
            else if(shape.layoutX >= width - 40.0){
                shape.layoutX = width - 40.0
            }

            if(shape.layoutY <= 40.0){
                shape.layoutY = 40.0
            }
            else if(shape.layoutY >= 160.0){
                shape.layoutY = 160.0
            }
            checkShapeIntersection(shape)
        }
    }

    private fun checkShapeIntersection(shape: Circle) {
        inPlace = false
        insertColumn = -1
        for ((countColumn, s: Circle) in nodes.withIndex()) {
            if(s != shape){
                val intersect = Shape.intersect(shape, s)
                if (intersect.boundsInLocal.width != -1.0) {
                    shape.layoutX = s.centerX
                    shape.layoutY = s.centerY
                    inPlace = true
                    insertColumn = countColumn
                }
            }
        }
    }

    private fun checkPiecesIntersection(shape: Shape): Boolean {
        for (s : Shape in allPieces) {
            if (s !== shape) {
                val intersect = Shape.intersect(shape, s)
                if (intersect.boundsInLocal.width != -1.0) {
                    shape.layoutY -= 10.0
                    return true;
                }
            }
        }
        return false;
    }

}