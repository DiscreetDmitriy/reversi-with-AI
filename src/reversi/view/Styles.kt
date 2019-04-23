package reversi.view

import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*
import tornadofx.Stylesheet.Companion.datagrid

class Styles : Stylesheet() {
    companion object {
        val windowSize = 820.0
    }

    init {
        Stylesheet.datagrid {
            alignment = Pos.CENTER
            backgroundColor += Color.WHITE
        }
        Stylesheet.datagridCell {
            borderColor += box(Color.DARKGREY)
            backgroundInsets = multi(box(1.px))
        }
//        datagrid {
//            contextMenu {
//            }
//        }
    }
}