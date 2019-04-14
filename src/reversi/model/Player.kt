package reversi.model

data class Player(var chip: ChipValue) {
    var isChanged = false
    private var playerCanMove: Boolean = false

    fun changePlayer() {
        chip = if (chip == ChipValue.BLACK)
            ChipValue.WHITE
        else
            ChipValue.BLACK
    }

    fun canMove(): Boolean {
        return playerCanMove
    }

    fun setCanMove(value: Boolean) {
        this.playerCanMove = value
    }
}