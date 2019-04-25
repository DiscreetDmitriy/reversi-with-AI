package reversi.view

import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import reversi.model.ChipValue
import reversi.model.Field
import reversi.model.Player
import reversi.view.Styles.Companion.cellSize
import reversi.view.Styles.Companion.windowSize
import tornadofx.*
import tornadofx.View


class View : View("Reversi") {
    private val fieldModel = Field()
    private val field = fieldModel.field
    private val player = Player(ChipValue.BLACK)
    private var listField = createFieldList()

    private fun createFieldList(): List<ChipValue> {
        fieldModel.getFreeCells(Player(ChipValue.BLACK))
        val list = mutableListOf<ChipValue>()
        for (i in 0..7)
            for (j in 0..7)
                list.add(field[i][j])
        return list
    }

    private fun printField(cells: List<List<Any>>) {
        println(cells[7])
        println(cells[6])
        println(cells[5])
        println(cells[4])
        println(cells[3])
        println(cells[2])
        println(cells[1])
        println(cells[0])
    }

    fun gameOver() = listField.all { it == ChipValue.BLACK || it == ChipValue.WHITE }

    private fun getRowAndColumn(i: Int): Pair<Int, Int> = i / 7 to i % 7


    override val root = datagrid(listField) {
        vbox {
            menubar {
                menu {
                    item("Restart") {
                        fieldModel.restart()
                    }
                }
            }
        }
        // TODO add icon   addStageIcon(Image("file:icon.xcf"))
        maxRows = 8
        maxCellsInRow = 8
        setPrefSize(windowSize, windowSize)
        cellHeight = cellSize
        cellWidth = cellSize
        // TODO make window size fixed

        cellFormat { cell ->
            horizontalCellSpacing = 0.0
            verticalCellSpacing = 0.0

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
                        // TODO remove cell highlighting
                        rectangle(width = 98.0, height = 98.0) {
                            fill = Color.WHITE
                            parent.onHover { hovering ->
                                fillProperty().animate(if (hovering) Color.LIGHTGREEN else Color.WHITE, 100.millis)
                                opacity = 0.7
                            }
                            /*addEventFilter(MouseEvent.MOUSE_CLICKED) { click ->
                                if (click.button == MouseButton.PRIMARY) {
                                val rowAndColumn = getRowAndColumn(index)
                                fieldModel.putChip(
                                    rowAndColumn.first,
                                    rowAndColumn.second, player
                                )
                                player.isChanged
                                fieldModel.getFreeCells(player)
                                listField = createFieldList()
                                    click.consume()
                                }
                            }*/
                        }
                    }
                }
                label(index.toString())
            }
        }
    }
}

/*private fun drawField(
    chipValue: ChipValue,
    stackPane: StackPane,
    cell: DataGrid<ChipValue>
) {
    when (chipValue) {
        ChipValue.BLACK -> stackPane.circle(radius = 45) {
            fill = Color.BLACK
        }
        ChipValue.WHITE -> stackPane.circle(radius = 45) {
            fill = Color.ANTIQUEWHITE
        }
        ChipValue.EMPTY -> stackPane.rectangle(width = 98.0, height = 98.0) {
            fill = Color.WHITE
        }
        ChipValue.OCCUPIABLE -> stackPane.rectangle(width = 98.0, height = 98.0) {
            fill = Color.WHITE
            parent.onHover { hovering ->
                fillProperty().animate(if (hovering) Color.LIGHTGREEN else Color.WHITE, 100.millis)
                opacity = 0.7
            }
            setOnMouseClicked { click ->
                if (click.button == MouseButton.PRIMARY) {
                    val rowAndColumn = getRowAndColumn(cell.indexInParent)
                    fieldModel.putChip(
                        rowAndColumn.first,
                        rowAndColumn.second, player
                    )
                    player.isChanged
                    fieldModel.getFreeCells(player)
                    listField = createFieldList()
                }
            }
        }
    }
}*/

/*class FieldModel : ItemViewModel<Field>() {
    val field = bind(Field::field)
}*/

