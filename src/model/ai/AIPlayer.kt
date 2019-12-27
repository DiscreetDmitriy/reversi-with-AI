package model.ai

import model.Chip
import model.Field

data class AIPlayer(
    override var chip: Chip,
    val depth: Int,
    val evaluator: Evaluator
) : Player(chip) {

    override fun play(field: Field): Pair<Int, Int> {
        return MiniMax.solve(field, depth, evaluator)
    }

}