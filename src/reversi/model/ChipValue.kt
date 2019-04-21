package reversi.model

enum class ChipValue {
    BLACK,
    WHITE,
    EMPTY,
    OCCUPIABLE;

    override fun toString(): String = when(this) {
        BLACK -> "B"
        WHITE -> "W"
        EMPTY -> "E"
        OCCUPIABLE -> "O"
    }
}