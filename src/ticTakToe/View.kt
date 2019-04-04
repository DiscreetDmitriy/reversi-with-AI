package reversi

import javafx.stage.StageStyle
import tornadofx.*

class MyView : View() {
    override val root = vbox {
        minHeight = 200.0
        minWidth = 200.0
        style = "-fx-base: #575757"

        button("Press Me") {
            style = "-fx-base: #FFFFFF"

            action {
                find<MyFragment>().openModal(stageStyle = StageStyle.UTILITY)
            }
        }
    }
}


class MyFragment: Fragment() {
    override val root = label("This is a popup") {
        minHeight = 100.0
        minWidth = 100.0
    }
}

class MyApp: App(MyView::class)

fun main() {
    launch<MyApp>()
}
