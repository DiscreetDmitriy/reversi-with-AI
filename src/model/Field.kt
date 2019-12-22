package model

import model.Chip.*
import model.ai.AIPlayer
import model.ai.Evaluator
import model.ai.Player

class Field {

    private val playerHuman = HumanPlayer(BLACK)
    private val playerAI = AIPlayer(WHITE, 3, Evaluator())
    private var humanTurn = true

    private val fieldArray =
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
                fieldArray[i][j] = EMPTY

        fieldArray[3][3] = WHITE
        fieldArray[4][4] = WHITE
        fieldArray[3][4] = BLACK
        fieldArray[4][3] = BLACK

        fieldArray[3][2] = OCCUPIABLE
        fieldArray[2][3] = OCCUPIABLE
        fieldArray[5][4] = OCCUPIABLE
        fieldArray[4][5] = OCCUPIABLE

        humanTurn = true
    }

    fun chip(x: Int, y: Int): Chip = fieldArray[x][y]

    fun currentPlayer(): Player {
        return if (humanTurn)
            playerHuman
        else
            playerAI
    }

    private fun changeTurn() {
        humanTurn = !humanTurn
    }

    fun getOccupiableCells(): List<Pair<Int, Int>> {
        val occupiable = mutableListOf<Pair<Int, Int>>()
        for (x in 0 until FIELD_SIZE)
            for (y in 0 until FIELD_SIZE)
                if (fieldArray[x][y] == OCCUPIABLE)
                    occupiable += x to y

        return occupiable
    }

    private val directions = listOf(
        -1 to -1, -1 to 0, -1 to 1, 0 to -1,
        0 to 1, 1 to -1, 1 to 0, 1 to 1
    )

    fun correctDirections(x: Int, y: Int, player: Player): List<Boolean> {

        if (fieldArray[x][y] == BLACK ||
            fieldArray[x][y] == WHITE
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
                    fieldArray[dx][dy] == EMPTY ||
                    fieldArray[dx][dy] == OCCUPIABLE
                ) break

                if (fieldArray[dx][dy] == player.oppositeChip()) {
                    oppositeChipsBetween++
                } else if (fieldArray[dx][dy] == player.chip) {
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

        if (fieldArray[x][y] != OCCUPIABLE || dirs.isEmpty())
            throw IllegalArgumentException()

        fieldArray[x][y] = player.chip

        for (dir in directions) {
            var i = x
            var j = y

            if (!dirs[directions.indexOf(dir)]) continue

            while (fieldArray[i + dir.second][j + dir.first] == player.oppositeChip()) {
                fieldArray[i + dir.second][j + dir.first] = player.chip
                i += dir.second
                j += dir.first
            }
        }

        for (row in 0 until FIELD_SIZE)
            for (column in 0 until FIELD_SIZE)
                if (fieldArray[row][column] == OCCUPIABLE)
                    fieldArray[row][column] = EMPTY

        changeTurn()

        for (i in 0 until FIELD_SIZE)
            for (j in 0 until FIELD_SIZE)
                if (correctDirections(i, j, currentPlayer()).isNotEmpty())
                    fieldArray[i][j] = OCCUPIABLE
    }

    fun isNotOver(): Boolean = fieldArray.any { row ->
        row.any { it == OCCUPIABLE }
    }

    fun score(): Pair<Int, Int> {
        var black = 0
        var white = 0

        for (i in 0 until FIELD_SIZE)
            for (j in 0 until FIELD_SIZE)
                if (fieldArray[i][j] == BLACK)
                    black++
                else if (fieldArray[i][j] == WHITE)
                    white++

        return black to white
    }

    companion object {
        const val FIELD_SIZE = 8
    }
}


