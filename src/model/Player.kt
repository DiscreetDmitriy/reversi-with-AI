package model

data class Player(override var mark:Int) : AbstractPlayer(mark) {
    override fun isHuman(): Boolean {
        return true
    }

    override fun playerName(): String {
        return "User"
    }

    override fun play(board: List<List<ChipValue>>): Pair<Int, Int> {
        throw IllegalAccessError("User plays by himself")
    }

    var playerChip = ChipValue.EMPTY
    var playerCanMove = false

    fun opposite(): ChipValue = if (playerChip == ChipValue.BLACK) ChipValue.WHITE else ChipValue.BLACK

    fun changePlayer() {
        playerChip = if (playerChip == ChipValue.BLACK) ChipValue.WHITE else ChipValue.BLACK
    }
}

//data class AIPlayer() : Player {
//
//}