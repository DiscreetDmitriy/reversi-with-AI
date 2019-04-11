package reversi

import javafx.scene.layout.StackPane
import reversi.Enum.EMPTY
import tornadofx.*

data class Game(val game: List<MutableList<Tile>> = List(8) { MutableList(8) { Tile(EMPTY) } }) {
    fun getGame() {
        println(game[0][0])
    }
}

class GameModel : ItemViewModel<Game>() {
    val gameField = bind(Game::game)
}


class Tile(val value: Enum) : StackPane() {

}

enum class Enum {
    BLACK,
    WHITE,
    OCCUPIABLE,
    EMPTY
}