package ticTacToe

import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.input.MouseButton
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Line
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Stage
import javafx.util.Duration

class TicTacToeApp : Application() {

    private val board = MutableList(3) { MutableList(3) { Tile() } }
    private val combos = mutableListOf<Combo>()
    var playable = true
    var turnX = true
    private val line = Line()

    private val root = Pane()

    private fun createContent(): Parent {
        root.setPrefSize(600.0, 600.0)

        for (i in 0..2) {
            for (j in 0..2) {
                val tile = board[j][i]
                tile.translateX = (j * 200).toDouble()
                tile.translateY = (i * 200).toDouble()

                root.children.add(tile)
            }
        }


        for (y in 0..2)
            combos.add(Combo(board[0][y], board[1][y], board[2][y]))

        for (x in 0..2)
            combos.add(Combo(board[x][0], board[x][1], board[x][2]))

        combos.add(Combo(board[0][0], board[1][1], board[2][2]))
        combos.add(Combo(board[2][0], board[1][1], board[0][2]))

        return root
    }

    override fun start(primaryStage: Stage) {
        primaryStage.scene = Scene(createContent())
        primaryStage.show()
    }


    fun checkState() {
        for (combo in combos)
            if (combo.isComplete) {
                playable = false
                playWinAnimation(combo)
                break
            }
    }


    private fun playWinAnimation(combo: Combo) {

        line.startX = combo.tiles[0].centerX
        line.startY = combo.tiles[0].centerY
        line.endX = combo.tiles[0].centerX
        line.endY = combo.tiles[0].centerY

        root.children.add(line)

        val timeline = Timeline()
        timeline.keyFrames.add(
            KeyFrame(
                Duration.seconds(0.5),
                KeyValue(line.endXProperty(), combo.tiles[2].centerX),
                KeyValue(line.endYProperty(), combo.tiles[2].centerY)
            )
        )
        timeline.play()
    }

    private fun resetBoard() {
        for (i in 0..2)
            for (j in 0..2) {
                board[i][j].text.text = null
                board[i][j].isOccupied = false
            }

        playable = true
        turnX = true
        root.children.remove(line)
    }

    inner class Combo(vararg val tiles: Tile) {
        val isComplete: Boolean
            get() = if (tiles[0].value.isEmpty()) false
            else tiles[0].value == tiles[1].value && tiles[0].value == tiles[2].value
    }

    inner class Tile : StackPane() {
        val text = Text()

        val centerX: Double
            get() = translateX + 100

        val centerY: Double
            get() = translateY + 100

        val value: String
            get() = text.text

        var isOccupied = false

        init {
            val border = Rectangle(200.0, 200.0)
            border.fill = Color.ANTIQUEWHITE
            border.stroke = Color.BLACK
            text.font = Font.font(120.0)
            alignment = Pos.CENTER
            children.addAll(border, text)

            setOnMouseClicked {

                if (it.button == MouseButton.SECONDARY)
                    resetBoard()

                if (!playable)
                    return@setOnMouseClicked

                if (!isOccupied && it.button == MouseButton.PRIMARY)
                    if (turnX) {
                        isOccupied = true
                        turnX = false
                        drawX()
                    } else {
                        isOccupied = true
                        turnX = true
                        drawO()
                    }

                checkState()
            }
        }

        private fun drawX() {
            text.text = "X"
        }

        private fun drawO() {
            text.text = "O"
        }
    }
}

