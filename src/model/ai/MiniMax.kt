package model.ai

import model.Field

class MiniMax {
    companion object {
        var nodesExplored = 0
        fun solve(
            field: Field,
            depth: Int,
            eval: Evaluator
        ): Pair<Int, Int> {
            val field1 = Field()
            for (i in field1.board.indices) {
                for (j in field1.board[i].indices)
                    field1.board[i][j] = field.board[i][j]
            }
            field1.humanTurn = field.humanTurn
            var bestMove = -1 to -1;
            var bestScore = Integer.MIN_VALUE

            for (move in field1.occupiable(field1.currentPlayer())) {
                field1.makeTurn(move.first, move.second, field1.currentPlayer())

                val childScore = MMAB(
                    field = field1,
                    depth = depth,
                    eval = eval,
                    max = false,
                    alpha = Int.MIN_VALUE,
                    beta = Int.MAX_VALUE
                )

                if (childScore > bestScore) {
                    bestScore = childScore
                    bestMove = move
                }
            }
            println("Nodes explored: $nodesExplored")
            return bestMove
        }

        private fun MMAB(
            field: Field,
            depth: Int,
            eval: Evaluator,
            max: Boolean,
            alpha: Int,
            beta: Int
        ): Int {
            nodesExplored++

            if (depth == 0 || !field.isNotOver())
                return eval.eval()

            if (max && field.occupiable(field.playerAI).isEmpty()
                || !max && field.occupiable(field.playerHuman).isNotEmpty()
            ) return MMAB(field, depth - 1, eval, !max, alpha, beta)

            var score: Int

            if (max) {
                score = Int.MIN_VALUE
                for (move in field.occupiable(field.playerAI)) {
                    // new board
                    val childScore = MMAB(/*new board*/field, depth - 1, eval, false, alpha, beta)

                    if (childScore > score)
                        score = childScore

                    if (score > alpha)
                        alpha = score
                    if (beta <= alpha)
                        break
                }
            } else {
                score = Int.MAX_VALUE

                for (move in field.occupiable(field.playerHuman)) {
                    // new board
                    val childScore = MMAB(/*new board*/field, depth - 1, eval, true, alpha, beta)
                    if (childScore < score)
                        score = childScore
                    if (score < beta)
                        beta = score
                    if (beta <= alpha)
                        break
                }
            }

            return score
        }
    }
}