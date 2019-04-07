package reversi

import tornadofx.*
import tornadofx.View

class View : View("My View") {
    override val root = vbox {
        button("Press me")
    }
}