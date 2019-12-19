package model

data class Player(var playerChip: ChipValue) {
    var playerCanMove = false

    fun opposite(): ChipValue = if (playerChip == ChipValue.BLACK) ChipValue.WHITE else ChipValue.BLACK

    fun changePlayer() {
        playerChip = if (playerChip == ChipValue.BLACK) ChipValue.WHITE else ChipValue.BLACK
    }
}