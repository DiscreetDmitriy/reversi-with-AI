package model

enum class Chip {
    BLACK,
    WHITE,
    EMPTY,
    OCCUPIABLE;

    override fun toString(): String =
        when (this) {
            BLACK -> "B"
            WHITE -> "W"
            EMPTY -> "E"
            OCCUPIABLE -> "O"
        }
}