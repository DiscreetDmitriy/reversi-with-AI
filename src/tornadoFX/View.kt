package tornadoFX

import tornadofx.*

class MasterView: View() {
    override val root = vbox {
        label("Working")
    }
}

fun main() {
    launch<MyApp>()
}