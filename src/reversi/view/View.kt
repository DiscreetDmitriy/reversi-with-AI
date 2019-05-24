package reversi.view

import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import reversi.model.ChipValue.*
import reversi.model.Field
import reversi.view.Styles.Companion.rec
import tornadofx.*
import tornadofx.View

class View : View("Reversi") {
    private val field = Field()
    private val fieldArray = field.field
    private val player = field.player
    private var status: Label? = null

    private val buttons = List(8) { MutableList(8) { StackPane() } }

//    private val fieldListProperty = SimpleObjectProperty<ObservableList<ChipValue>>(createFieldList())
//    private var fieldList: ObservableList<ChipValue> by fieldListProperty
    /*private fun createFieldList(): ObservableList<ChipValue> {
        field.getFreeCells(Player(ChipValue.BLACK))
        val list = FXCollections.observableArrayList<ChipValue>()
        for (i in 0..7)
            for (j in 0..7)
                list.add(fieldArray[i][j])
        return list
    }*/

    fun hasFreeCells(): Boolean = fieldArray.all { row -> row.all { it == BLACK || it == WHITE } }

//    private fun getRowAndColumn(i: Int): Pair<Int, Int> = i / 8 to i % 8

    override val root = borderpane {
        top {
            menubar {
                menu("Menu") {
                    item("New game", "N").action {
                        field.restart()
                        update()
                    }
                    item("Exit", "Esc").action { this@View.close() }
                }
            }
        }
        center {
            gridpane {
                alignment = Pos.CENTER
                style {
                    fill = Color.WHITE
                }
                for (row in 0 until FIELD_SIZE) {
                    row {
                        for (column in 0 until FIELD_SIZE) {
                            val button =
                                stackpane {
                                    addClass(rec)
//                                    label((row * 8 + column).toString())
                                    update()
                                    setOnMouseReleased {
                                        field.putChip(row, column, player)
                                        player.changePlayer()
                                        field.getFreeCells(player)
                                        update()
                                    }
                                }
                            buttons[row][column] = button
                        }
                    }
                }
            }
        }
        bottom {
            status = label(
                "Score:   Black: ${field.blackAndWhiteScore().first},  " +
                        "White: ${field.blackAndWhiteScore().second}             " +
                        "     Player ${if (player.playerChip == BLACK) "black" else "white"} turn"
            ) {
                alignment = Pos.CENTER
            }
        }
    }

    private fun update() {
        field.getFreeCells(player)
        for (x in 0 until FIELD_SIZE)
            for (y in 0 until FIELD_SIZE) {
                val cell = fieldArray[x][y]
                buttons[x][y].apply {
                    rectangle(height = 98.0, width = 98.0) {
                        fill = when (cell) {
                            BLACK -> {
                                circle(radius = 45) { fill = Color.BLACK }
                                Color.WHITE
                            }
                            WHITE -> {
                                circle(radius = 45) { fill = Color.ANTIQUEWHITE }
                                Color.WHITE
                            }
                            EMPTY -> Color.WHITE
                            OCCUPIABLE -> {
                                parent.onHover { hovering ->
                                    fillProperty().animate(
                                        if (hovering) Color.LIGHTGREEN else Color.WHITE,
                                        100.millis
                                    )
//                                    opacity = 0.7
                                }
                                Color.WHITE
                            }
                        }
                    }
                }
            }

        status?.text =
            "Score:   Black: ${field.blackAndWhiteScore().first},  " +
                    "White: ${field.blackAndWhiteScore().second}             " +
                    "     Player ${if (player.playerChip == BLACK) "black" else "white"} turn"

    }
}

const val FIELD_SIZE = 8

