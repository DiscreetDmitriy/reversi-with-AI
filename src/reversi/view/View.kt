package reversi.view

import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import reversi.model.ChipValue.*
import reversi.model.Field
import reversi.model.Field.Companion.FIELD_SIZE
import reversi.view.Styles.Companion.cellSize
import reversi.view.Styles.Companion.chipRadius
import reversi.view.Styles.Companion.rec
import reversi.view.Styles.Companion.windowSize
import tornadofx.*
import tornadofx.View

class View : View("Reversi") {
    private val field = Field()

    private val buttons = List(8) { MutableList(8) { StackPane() } }
    private var status: Label? = null

    override val root = borderpane {
        top {
            menubar {
                menu("Menu") {
                    item("Exit", "Esc").action { this@View.close() }
                    item("New game", "N").action {
                        field.restart()
                        update()
                    }
                }
            }
        }
        center {
            gridpane {
                alignment = Pos.CENTER
                prefHeight = windowSize
                prefWidth = windowSize

                for (row in 0 until FIELD_SIZE)
                    row {
                        for (column in 0 until FIELD_SIZE) {
                            val button = stackpane {
                                addClass(rec)
                                update()

                                setOnMouseClicked {
                                    if (field.getCell(row,column) == OCCUPIABLE)
                                        field.makeTurn(row, column)

                                    update()
                                }
                            }
                            buttons[row][column] = button
                        }
                    }
            }
        }
        bottom {
            status = label("Score:  Black: 2, White: 2\t\t\t Player black's turn")
        }
    }

    private fun update() {
        for (x in 0 until FIELD_SIZE)
            for (y in 0 until FIELD_SIZE) {
                val cell = field.getCell(x,y)
                buttons[x][y].apply {
                    rectangle(height = cellSize, width = cellSize) {
                        fill = when (cell) {
                            BLACK -> {
                                circle(radius = chipRadius) { fill = Color.BLACK }
                                Color.WHITE
                            }
                            WHITE -> {
                                circle(radius = chipRadius) { fill = Color.ANTIQUEWHITE }
                                Color.WHITE
                            }
                            EMPTY -> Color.WHITE
                            OCCUPIABLE -> {
                                parent.onHover { hovering ->
                                    fillProperty().animate(
                                        if (hovering) Color.LIGHTGREEN else Color.WHITE,
                                        100.millis
                                    )
                                }
                                Color.WHITE
                            }
                        }
                    }
                }
            }

        val score = field.blackAndWhiteScore()
        status?.text = if (field.hasFreeCells())
            "Score:  Black: ${score.first}, White: ${score.second}\t\t\t " +
                    "Player ${if (field.getCurrentPlayer() == BLACK) "black" else "white"}'s turn"
        else
            "Game is finished.\t" +
                    "Winner is player ${if (score.first > score.second) "Black" else "White"}\t\t " +
                    "Score:  Black: ${score.first}, White: ${score.second}"
    }
}

