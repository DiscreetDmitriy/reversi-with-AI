package reversi

import reversi.Styles.Companion.firstButton
import reversi.Styles.Companion.mainStyle
import tornadofx.*
import tornadofx.View

class View : View("My View") {
    override val root = vbox {
        addClass(mainStyle)
        button("Press me") {
            addClass(firstButton)
            setOnAction {
                println("button has been pressed")
            }
        }
    }
}