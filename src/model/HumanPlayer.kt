package model

import model.ai.Player

data class HumanPlayer(override var chip: Chip) : Player(chip) {

    override fun play(field: Field): Pair<Int, Int> {
        throw IllegalAccessError("User plays by himself")
    }
}