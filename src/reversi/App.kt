package reversi

import tornadofx.*
import tornadofx.App

class App : App(View::class) {
    init {
        reloadStylesheetsOnFocus()
    }
}