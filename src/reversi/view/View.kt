package reversi.view

import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.Cell
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import reversi.model.ChipValue
import reversi.model.Field
import reversi.model.Player
import reversi.view.Styles.Companion.cellSize
import reversi.view.Styles.Companion.windowSize
import tornadofx.*
import tornadofx.View
import tornadofx.getValue
import tornadofx.setValue

// TODO add icon   addStageIcon(Image("file:icon.xcf"))
// TODO make window size fixed   ResizeType.Fixed(windowSize)
// TODO remove cell highlighting

class View : View("Reversi") {
//        private val fieldProperty: FieldModel by inject()
    private val fieldClass = Field()
    private val field = fieldClass.field
    private val player = fieldClass.player

    private val fieldListProperty = SimpleObjectProperty<ObservableList<ChipValue>>(createFieldList())
    private var fieldList: ObservableList<ChipValue> by fieldListProperty

//    private var listBinding

    private fun createFieldList(): ObservableList<ChipValue> {
        fieldClass.getFreeCells(Player(ChipValue.BLACK))
        val list = FXCollections.observableArrayList<ChipValue>()
        for (i in 0..7)
            for (j in 0..7)
                list.add(field[i][j])
        return list
    }

    /*private fun printField(cells: List<List<Any>>) {
        println(cells[7])
        println(cells[6])
        println(cells[5])
        println(cells[4])
        println(cells[3])
        println(cells[2])
        println(cells[1])
        println(cells[0])
    }*/

    fun gameOver() = fieldList.all { it == ChipValue.BLACK || it == ChipValue.WHITE }

    private fun getRowAndColumn(i: Int): Pair<Int, Int> = i / 8 to i % 8
    
    override val root = borderpane {
        top {
            menubar {
                menu("Game") {
                    item("New game") { fieldClass.restart() }
                }
            }
        }
        center {
            datagrid(fieldList) {
                maxRows = 8
                maxCellsInRow = 8
                setPrefSize(windowSize, windowSize)
                cellHeight = cellSize
                cellWidth = cellSize

                /*bindChildren(fieldList) {cell ->
                    stackpane {
                        rectangle(width = 100, height = 100) {
                            fill = Color.ANTIQUEWHITE
                            setOnMouseClicked {
//                                fieldList[]
                            }
                        }
                        label(this@flowpane.indexInParent.toString())
                    }
                }*/

                cellFormat { cell ->
                    horizontalCellSpacing = 0.0
                    verticalCellSpacing = 0.0

//                    bind(fieldList[index]) {
//                        label("")
//                    }
                    graphic = stackpane {
                        when (cell) {
                            ChipValue.BLACK -> {
                                addEventFilter(MouseEvent.ANY) { it.consume() }
                                circle(radius = 45) {
                                    fill = Color.BLACK
                                }
                            }
                            ChipValue.WHITE -> {
                                addEventFilter(MouseEvent.ANY) { it.consume() }
                                circle(radius = 45) {
                                    fill = Color.ANTIQUEWHITE
                                }
                            }
                            ChipValue.EMPTY -> {
                                addEventFilter(MouseEvent.ANY) { it.consume() }
                                rectangle(width = 98.0, height = 98.0) {
                                    fill = Color.WHITE
                                }
                            }
                            ChipValue.OCCUPIABLE -> {
                                rectangle(width = 98.0, height = 98.0) {
                                    fill = Color.WHITE
                                    parent.onHover { hovering ->
                                        fillProperty().animate(
                                            if (hovering) Color.LIGHTGREEN else Color.WHITE,
                                            100.millis
                                        )
                                        opacity = 0.7
                                    }
                                    setOnMouseReleased { click ->
                                        if (click.button == MouseButton.PRIMARY) {
                                            println(click.eventType.toString())
                                            val rowAndColumn = getRowAndColumn(index)
                                            fieldClass.putChip(
                                                rowAndColumn.first,
                                                rowAndColumn.second, player
                                            )
                                            player.isChanged
                                            fieldClass.getFreeCells(player)
                                            fieldList = createFieldList()
//                                    click.consume()

                                        }
                                    }
                                }
                            }
                        }
                        label(index.toString())
                    }
                }
            }
        }
    }
}

/*class FieldModel : ItemViewModel<Field>() {
    val field = bind(Field::field)
}*/

