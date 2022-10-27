package com.example.a2basic

import javafx.geometry.Insets
import javafx.scene.control.ScrollPane
import javafx.scene.control.SplitPane

class Middle(): SplitPane() {
    init {
        val left = ScrollPane().apply {
            vbarPolicy = ScrollPane.ScrollBarPolicy.ALWAYS
            hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        }
        left.content = DataEntry(left.widthProperty().subtract(20))



        val right = ScrollPane().apply {
            vbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
            hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
            padding = Insets(5.0, 5.0, 5.0, 5.0)
        }
        val canvas = ResizableCanvas()
        right.content = canvas
        this.items.addAll(left, right)

        canvas.widthProperty().bind(
            this.widthProperty().multiply(this.dividers[0].positionProperty()).subtract(20))
        canvas.heightProperty().bind(
            this.heightProperty().subtract(20));

        this.dividers[0].positionProperty().addListener { _, _, _ ->
            canvas.widthProperty().bind(
                this.widthProperty().multiply(this.dividers[0].positionProperty().add(-1).multiply(-1)).subtract(20))
        }
    }
}
