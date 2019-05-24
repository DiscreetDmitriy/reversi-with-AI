package reversi.view

import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        const val windowSize = 800.0
        const val cellSize = 100.0
        val rec by cssclass()
        val grid by cssclass()
    }

    init {
        grid {
            alignment = Pos.CENTER
            backgroundColor += Color.WHITE
        }
        rec {
            prefHeight = Dimension(100.0, Dimension.LinearUnits.px)
            prefWidth = Dimension(100.0, Dimension.LinearUnits.px)

            backgroundColor += Color.WHITE

            borderColor += box(Color.DARKGREY)
            backgroundInsets = multi(box(2.px))
        }
    }
}