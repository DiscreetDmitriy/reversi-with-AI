package model.ai

import model.Chip

data class AIPlayer(
    override var chip: Chip,
    val depth: Int,
    val evaluator: Evaluator
) : Player(chip) {

    override fun play(board: List<List<Chip>>): Pair<Int, Int> {
        return MiniMax.solve(board, chip, depth, evaluator)
    }

}