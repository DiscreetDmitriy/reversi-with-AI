package view

import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import model.Chip.*
import model.Field
import model.Field.Companion.FIELD_SIZE
import tornadofx.*
import tornadofx.View
import view.Styles.Companion.CELL_SIZE
import view.Styles.Companion.CHIP_RADIUS
import view.Styles.Companion.WINDOW_SIZE
import view.Styles.Companion.rec
import java.util.*

class View : View("Reversi") {
    private val field = Field()

    private val buttons = List(FIELD_SIZE) { MutableList(FIELD_SIZE) { StackPane() } }
    private var status: Label? = null

    override val root = borderpane {
        top {
            menubar {
                menu("Menu") {
                    item("New game", "N").action {
                        field.restart()
                        updateScore()
                        repaint()
                    }
                    item("Exit", "Esc").action { this@View.close() }
                }
            }
        }
        center {
            gridpane {
                alignment = Pos.CENTER
                prefHeight = WINDOW_SIZE
                prefWidth = WINDOW_SIZE

                for (row in 0 until FIELD_SIZE) {
                    row {
                        for (column in 0 until FIELD_SIZE) {
                            val button = stackpane {
                                addClass(rec)
                                repaint()

                                setOnMouseClicked {
                                    if (field.board()[row][column] == OCCUPIABLE) {
                                        firstPlayerTurn(row, column)

                                        secondPlayerTurn()
                                    }
                                }
                            }
                            buttons[row][column] = button
                        }
                    }
                }
            }
        }
        bottom {
            status = label("Score:  Black: 2, White: 2\t\t\t Player black's turn")
        }
    }

    private fun firstPlayerTurn(row: Int, column: Int) {
        field.makeTurn(row, column, field.currentPlayer())

        updateScore()
        repaint()
    }

    private fun secondPlayerTurn() {
        val timer = Timer("TimerBetweenTurns", true)
        val task = FXTimerTask(
                {
                    val (x, y) = field.currentPlayer().play(field.board())
                    field.makeTurn(x, y, field.currentPlayer())
                    updateScore()
                    repaint()
                }
                , timer)

        timer.schedule(task, 1000)
    }

    private fun repaint() {
        for (x in 0 until FIELD_SIZE) {
            for (y in 0 until FIELD_SIZE) {
                val cell = field.chip(x, y)
                buttons[x][y].apply {
                    clear()
                    rectangle(height = CELL_SIZE, width = CELL_SIZE) {
                        fill = Color.WHITE
                        when (cell) {
                            BLACK -> circle(radius = CHIP_RADIUS) {
                                fill = Color.BLACK
                            }
                            WHITE -> circle(radius = CHIP_RADIUS) {
                                fill = Color.ANTIQUEWHITE
                            }
                            EMPTY -> {
                            }
                            OCCUPIABLE -> {
                                fill = Color.LIGHTGREEN
                                if (field.humanTurn) {
                                    parent.onHover { hovering ->
                                        opacityProperty().animate(
                                                if (hovering) 0.6 else 1.0,
                                                100.millis
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateScore() {
        val (black, white) = field.score()
        status?.text = if (field.isNotOver())
            "Score:  Black: $black, White: $white\t\t\t " +
                    "Player ${if (field.currentPlayer().chip == BLACK) "black" else "white"}'s turn"
        else
            "Game is finished.\t" +
                    "Winner is player ${if (black > white) "Black" else "White"}\t\t " +
                    "Score:  Black: $black, White: $white"

    }
}

