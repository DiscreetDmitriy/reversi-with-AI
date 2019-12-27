package model.ai

import model.Chip
import model.Field

abstract class Player(internal open val chip: Chip) {

    fun oppositeChip(): Chip =
        if (chip == Chip.BLACK)
            Chip.WHITE
        else
            Chip.BLACK

    abstract fun play(field:Field): Pair<Int, Int>
}
