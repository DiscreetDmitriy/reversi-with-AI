package reversi

import javafx.scene.paint.Color.*
import javafx.scene.text.FontWeight
import tornadofx.*
import tornadofx.View

class View : View("My View") {
    override val root = vbox {
        button("Press me") {
            style {
                fontWeight = FontWeight.BOLD
                borderColor += box(RED, GREEN, YELLOW, PINK)
                size = 80.px
                rotate = 45.deg
            }
        }
    }
}