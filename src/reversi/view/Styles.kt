package reversi.view

import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        const val windowSize = 800.0
        const val cellSize = 100.0

    }

    init {
        datagrid {
            alignment = Pos.CENTER
            backgroundColor += Color.WHITE
        }
        datagridCell {
            borderColor += box(Color.DARKGREY)
            backgroundInsets = multi(box(1.px))
        }
    }
}