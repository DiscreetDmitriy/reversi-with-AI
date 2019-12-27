package model.ai

import model.Field
import model.ai.Evaluator.GamePhase.*

class Evaluator(private val field: Field) {

    enum class GamePhase {
        EARLY_GAME,
        MID_GAME,
        LATE_GAME
    }

    private fun gamePhase(): GamePhase {
        val score = field.score().first + field.score().second

        return when {
            score < 20 -> EARLY_GAME
            score <= 58 -> MID_GAME
            else -> LATE_GAME
        }
    }

    fun eval(): Int = when (gamePhase()) {
        EARLY_GAME -> 1000 * evalCorner() + 50 * evalMobility()
        MID_GAME -> 1000 * evalCorner() + 20 * evalMobility() + 10 * evalChipDiff()
        LATE_GAME -> 1000 + 100 * evalMobility() + 500 * evalChipDiff()
    }

    private fun evalCorner(): Int {
        val board = field.board()
        val myChip = field.playerAI.chip

        var myCorners = 0
        var opCorners = 0

        if (board[0][0] == myChip) myCorners++ else opCorners++
        if (board[0][7] == myChip) myCorners++ else opCorners++
        if (board[7][0] == myChip) myCorners++ else opCorners++
        if (board[7][7] == myChip) myCorners++ else opCorners++

        return 100 * (myCorners - opCorners) / (myCorners * opCorners + 1)
    }

    private fun evalMobility(): Int {
        val humanMoves = field.occupiable(field.playerHuman).size
        val aiMoves = field.occupiable(field.playerAI).size

        return 100 * (aiMoves - humanMoves) / (aiMoves + humanMoves + 1)
    }

    private fun evalChipDiff(): Int {
        val (black, white) = field.score()

        return 100 * (white - black) / (white + black)
    }
}
