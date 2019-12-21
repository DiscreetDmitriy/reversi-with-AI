package model

import model.ai.Player

data class HumanPlayer(override var chip: Chip) : Player(chip) {

    override fun isHuman(): Boolean {
        return true
    }

    override fun playerName(): String {
        return "User"
    }

    override fun play(board: List<List<Chip>>): Pair<Int, Int> {
        throw IllegalAccessError("User plays by himself")
    }
}