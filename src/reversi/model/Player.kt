package reversi.model

data class Player(var playerChip: ChipValue) {
    var isChanged = false
    var playerCanMove = false

    fun changePlayer() {
        playerChip = if (playerChip == ChipValue.BLACK) ChipValue.WHITE
        else ChipValue.BLACK
    }
}