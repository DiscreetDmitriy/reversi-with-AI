package reversi.view

import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val dimension = Dimension(100.0, Dimension.LinearUnits.px)
        const val windowSize = 800.0
        const val cellSize = 100.0
        const val chipRadius = 45.0

        val rec by cssclass()
    }

    init {
        rec {
            prefHeight = dimension
            prefWidth = dimension

            backgroundColor += Color.WHITE

            borderColor += box(Color.DARKGREY)
            backgroundInsets = multi(box(2.px))
        }
    }
}