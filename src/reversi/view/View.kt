package reversi.view

import javafx.scene.image.Image
import javafx.scene.paint.Color
import reversi.model.ChipValue
import reversi.model.Field
import reversi.model.Player
import reversi.view.Styles.Companion.windowSize
import tornadofx.*
import tornadofx.View

class View : View("Reversi") {
    //    private val fieldModel: FieldModel by inject()
    private val fieldModel = Field()
    private val field = fieldModel.field
    private val cellSize = 100.0

    private fun createFieldList(): List<ChipValue> {
        fieldModel.getFreeCells(Player(ChipValue.BLACK))
        val list = mutableListOf<ChipValue>()
        for (i in 0..7)
            for (j in 0..7)
                list.add(field[i][j])
        return list
    }

    private fun printField(cells: List<List<Any>>) {
        println(cells[0].reversed())
        println(cells[1].reversed())
        println(cells[2].reversed())
        println(cells[3].reversed())
        println(cells[4].reversed())
        println(cells[5].reversed())
        println(cells[6].reversed())
        println(cells[7].reversed())
    }

    fun gameOver() = createFieldList().all { it == ChipValue.BLACK || it == ChipValue.WHITE }

//    private val num = (1..64).toList()

    override val root = datagrid(createFieldList()) {
        addStageIcon(Image("file:icon.xcf"))
        setPrefSize(windowSize, windowSize - 20.0)
//        addClass(gridStyle)
        cellHeight = cellSize
        cellWidth = cellSize
        ResizeType.Fixed(windowSize)

        cellFormat {
//            addClass(cellStyle)
            horizontalCellSpacing = 0.0
            verticalCellSpacing = 0.0
            graphic = stackpane {
                setOnMouseClicked { graphic = null }
                when (it) {
                    ChipValue.BLACK -> circle(radius = 45) {
                        fill = Color.BLACK
                    }
                    ChipValue.WHITE -> circle(radius = 45) {
                        fill = Color.NAVAJOWHITE
                    }
                    ChipValue.EMPTY -> rectangle(width = 98.0, height = 98.0) {
                        fill = Color.WHITE
                    }
                    ChipValue.OCCUPIABLE -> rectangle(width = 98.0, height = 98.0) {
                        fill = Color.WHITE
                        parent.onHover { hovering ->
                            fillProperty().animate(if (hovering) Color.LIGHTGREEN else Color.WHITE, 100.millis)
                            opacity = 0.7
                        }
                    }
                }
            }
        }
    }
}

class FieldModel : ItemViewModel<Field>() {
    val field = bind(Field::field)
}

