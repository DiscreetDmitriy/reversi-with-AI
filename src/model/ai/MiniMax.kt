package model.ai

import model.Field

@Suppress("NAME_SHADOWING")

class MiniMax {
    companion object {

        fun solve(field: Field, depth: Int, eval: Evaluator): Pair<Int, Int> {
            val field1 = copyField(field)

            var bestMove = -1 to -1
            var bestScore = Integer.MIN_VALUE

            for (move in field1.occupiable(field1.currentPlayer())) {
                field1.makeTurn(move.first, move.second, field1.currentPlayer())

                val childScore = minmax(
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
            return bestMove
        }

        private fun minmax(
            field: Field,
            depth: Int,
            eval: Evaluator,
            max: Boolean,
            alpha: Int,
            beta: Int
        ): Int {
            var alpha = alpha
            var beta = beta

            if (depth == 0 || !field.isNotOver())
                return eval.eval()

            if (max && field.occupiable(field.playerAI).isEmpty()
                || !max && field.occupiable(field.playerHuman).isNotEmpty()
            ) return minmax(field, depth - 1, eval, !max, alpha, beta)

            var score: Int

            if (max) {
                score = Int.MIN_VALUE
                for (move in field.occupiable(field.playerAI)) {
                    val field = copyField(field)
                    val childScore = minmax(
                        field = field,
                        depth = depth - 1,
                        eval = eval,
                        max = false,
                        alpha = alpha,
                        beta = beta
                    )

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
                    val field = copyField(field)
                    field.makeTurn(move.first, move.second, field.playerAI)
                    val childScore = minmax(
                        field = field,
                        depth = depth - 1,
                        eval = eval,
                        max = true,
                        alpha = alpha,
                        beta = beta
                    )
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

        private fun copyField(field: Field): Field {
            val field1 = Field()

            for (i in field1.board.indices) {
                for (j in field1.board[i].indices)
                    field1.board[i][j] = field.board[i][j]
            }
            field1.humanTurn = field.humanTurn
            return field1
        }
    }
}