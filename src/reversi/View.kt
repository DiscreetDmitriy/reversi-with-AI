package reversi

import javafx.scene.paint.Color
import reversi.Styles.Companion.stackPaneStyle
import tornadofx.*
import tornadofx.View

class View : View("My View") {
    val game: GameModel by inject()
    val a = Game::game
    val list: List<MutableList<Tile>> = game.gameField.value
    val numbers = (1..64).toList()
    val size = 655.0

    override val root =
        datagrid(numbers) {
            setPrefSize(size, size)
            cellHeight = 80.0
            cellWidth = 80.0
            padding = insets(0)
            cellCache { value ->
                stackpane {
                    addClass(stackPaneStyle)
                    horizontalCellSpacing = 0.0
                    verticalCellSpacing = 0.0
                    setOnMouseClicked {
                        println("$value")
                    }
                    rectangle(width = 80.0, height = 80.0) {
                        fill = Color.YELLOWGREEN
                    }

                }
            }
        }

    /*override val root = gridpane {
        vgap = 15.0
        padding = insets(15)
        for ( i in 1..8) {
            add(horizontalStreetPane())
        }
    }

    private fun horizontalStreetPane(): StackPane {
        return stackpane {
            rectangle {
                fill = c("4E9830")
                width = 100.0
                height = 100.0
            }
            rectangle {
                fill = c("919191")
                width = 100.0
                height= 40.0
            }
        }
    }*/
}



