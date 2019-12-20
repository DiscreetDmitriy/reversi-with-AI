package model

abstract class AbstractPlayer {

     abstract var mark: Int

    constructor(mark: Int) {
        this.mark = mark
    }

    abstract fun isHuman(): Boolean

    abstract fun playerName(): String

    abstract fun play(board: List<List<ChipValue>>): Pair<Int, Int>
}
