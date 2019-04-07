package reversi

import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {

    companion object {
        val firstButton by cssclass()
        val mainStyle by cssclass()
    }

    init {
        firstButton {
            fontWeight = FontWeight.BOLD
            borderColor += box(Color.RED, Color.GREEN, Color.YELLOW, Color.PINK)
            padding = box(20.px)
        }
        mainStyle {
            prefWidth = 200.px
            prefHeight = 200.px
            padding = box(50.px)
        }
    }
}