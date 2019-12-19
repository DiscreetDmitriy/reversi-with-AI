package view

import javafx.scene.paint.Color
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val DIMENSION = Dimension(100.0, Dimension.LinearUnits.px)
        const val WINDOW_SIZE = 800.0
        const val CELL_SIZE = 100.0
        const val CHIP_RADIUS = 45.0

        val rec by cssclass()
    }

    init {
        rec {
            prefHeight = DIMENSION
            prefWidth = DIMENSION

            backgroundColor += Color.WHITE

            borderColor += box(Color.DARKGREY)
            backgroundInsets = multi(box(2.px))
        }
    }
}