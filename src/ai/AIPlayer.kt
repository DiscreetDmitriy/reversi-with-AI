package ai

import model.AbstractPlayer
import model.ChipValue

class AIPlayer(override var mark: Int) : AbstractPlayer(mark) {

    private val searchDepth: Int
    private val evaluator: Evaluator

    constructor(mark: Int, depth: Int) {
        super(mark)
        searchDepth = depth
        evaluator = DynamicEvaluator
    }

    override fun isHuman(): kotlin.Boolean {
        return false
    }

    override fun playerName(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun play(board: List<List<ChipValue>>): Pair<Int, Int> {
        sovlve()
    }
}