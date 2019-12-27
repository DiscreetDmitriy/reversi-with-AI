package model

import model.Chip.*
import model.ai.AIPlayer
import model.ai.Evaluator
import model.ai.Player

class Field {

    val playerHuman = HumanPlayer(BLACK)
    val playerAI = AIPlayer(WHITE, 3, Evaluator(this))
    var humanTurn = true
        internal set

    val board =
        List(FIELD_SIZE) { MutableList(FIELD_SIZE) { EMPTY } }
            .apply {
                this[3][3] = WHITE
                this[4][4] = WHITE
                this[3][4] = BLACK
                this[4][3] = BLACK

                this[3][2] = OCCUPIABLE
                this[2][3] = OCCUPIABLE
                this[5][4] = OCCUPIABLE
                this[4][5] = OCCUPIABLE
            }

    fun restart() {
        for (i in 0 until FIELD_SIZE)
            for (j in 0 until FIELD_SIZE)
                board[i][j] = EMPTY

        board[3][3] = WHITE
        board[4][4] = WHITE
        board[3][4] = BLACK
        board[4][3] = BLACK

        board[3][2] = OCCUPIABLE
        board[2][3] = OCCUPIABLE
        board[5][4] = OCCUPIABLE
        board[4][5] = OCCUPIABLE

        humanTurn = true
    }

    fun chip(x: Int, y: Int): Chip = board[x][y]

    fun currentPlayer(): Player {
        return if (humanTurn)
            playerHuman
        else
            playerAI
    }

    fun board(): List<List<Chip>> {
        return board
    }

    private fun changeTurn() {
        humanTurn = !humanTurn
    }

    private val directions = listOf(
        -1 to -1, -1 to 0, -1 to 1, 0 to -1,
        0 to 1, 1 to -1, 1 to 0, 1 to 1
    )

    fun correctDirections(x: Int, y: Int, player: Player): List<Boolean> {

        if (board[x][y] == BLACK ||
            board[x][y] == WHITE
        ) return listOf()

        val resDirections = mutableListOf<Boolean>()

        for (dir in directions) {

            var lastChip = false
            var i = x
            var j = y
            var oppositeChipsBetween = 0

            while (i in 0 until FIELD_SIZE && j in 0 until FIELD_SIZE) {

                val dx = i + dir.second
                val dy = j + dir.first

                if (dx !in 0 until FIELD_SIZE ||
                    dy !in 0 until FIELD_SIZE ||
                    board[dx][dy] == EMPTY ||
                    board[dx][dy] == OCCUPIABLE
                ) break

                if (board[dx][dy] == player.oppositeChip()) {
                    oppositeChipsBetween++
                } else if (board[dx][dy] == player.chip) {
                    lastChip = true
                    break
                }

                i = dx
                j = dy
            }
            resDirections.add(lastChip && oppositeChipsBetween > 0)
        }
        return if (true in resDirections) resDirections else listOf()
    }

    fun makeTurn(x: Int, y: Int, player: Player) {
        val dirs = correctDirections(x, y, player)

        if (board[x][y] != OCCUPIABLE)
            return

        board[x][y] = player.chip

        flipOtherChips(x, y, dirs, player)
        clearOccupiableChips()
        changeTurn()
        findOccupiable()
    }

    private fun flipOtherChips(x: Int, y: Int, dirs: List<Boolean>, player: Player) {
        for (dir in directions) {
            var i = x
            var j = y

            if (!dirs[directions.indexOf(dir)]) continue

            while (board[i + dir.second][j + dir.first] == player.oppositeChip()) {
                board[i + dir.second][j + dir.first] = player.chip
                i += dir.second
                j += dir.first
            }
        }
    }

    private fun clearOccupiableChips() {
        for (row in 0 until FIELD_SIZE)
            for (column in 0 until FIELD_SIZE)
                if (board[row][column] == OCCUPIABLE)
                    board[row][column] = EMPTY
    }

    private fun findOccupiable(): List<Pair<Int, Int>> {
        val list = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until FIELD_SIZE)
            for (j in 0 until FIELD_SIZE)
                if (correctDirections(i, j, currentPlayer()).isNotEmpty()) {
                    board[i][j] = OCCUPIABLE
                    list += i to j
                }

        return list
    }

    fun occupiable(player: Player): List<Pair<Int, Int>> {
        val list = mutableListOf<Pair<Int, Int>>()

        for (i in 0 until FIELD_SIZE)
            for (j in 0 until FIELD_SIZE)
                if (correctDirections(i, j, player).isNotEmpty())
                    list += i to j

        return list
    }

    fun isNotOver(): Boolean = board.any { row ->
        row.any { it == OCCUPIABLE }
    }

    fun score(): Pair<Int, Int> {
        var black = 0
        var white = 0

        for (i in 0 until FIELD_SIZE)
            for (j in 0 until FIELD_SIZE)
                if (board[i][j] == BLACK)
                    black++
                else if (board[i][j] == WHITE)
                    white++

        return black to white
    }

    companion object {
        const val FIELD_SIZE = 8
    }
}


