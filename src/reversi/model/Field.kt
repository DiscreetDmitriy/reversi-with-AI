package reversi.model

import java.util.*

class Field {
    val cells = MutableList(8) { MutableList(8) { ChipValue.EMPTY } }

    fun restart() {
        for (i in 0..7)
            for (j in 0..7)
                cells[i][j] = ChipValue.EMPTY

        cells[3][4] = ChipValue.BLACK
        cells[4][3] = ChipValue.BLACK
        cells[3][3] = ChipValue.WHITE
        cells[4][4] = ChipValue.WHITE
    }

    fun trueDirections(x: Int, y: Int, player: Player): List<Boolean>? {

        if (cells[x][y] != ChipValue.EMPTY) return null

        val directions = ArrayList<Boolean>()
        var i: Int
        var j: Int
        var dx: Int
        var dy: Int
        var X1: Int
        var X2: Int
        var X3: Int
        var notX1: Int
        var notX2: Int
        var notX3: Int
        var sum: Int
        var lastChip: Boolean

        for (k in 0..7) {
            lastChip = false
            sum = 0
            X1 = k / 4
            X2 = k / 2 % 2
            X3 = k % 2
            notX1 = X1 xor 1
            notX2 = X2 xor 1
            notX3 = X3 xor 1
            dx = X1 * (X2 or X3) - notX1 * (notX2 or notX3)
            dy = (notX1 * X2 * notX3 or X1 * (X2 xor X3 xor 1)) - (notX1 * (X2 xor X3 xor 1) or X1 * notX2 * X3)
            i = x + dx
            j = y + dy
            while (i in 0..7 && j in 0..7) {
                if (cells[i][j] == ChipValue.EMPTY) break
                if (cells[i][j] === player.chip) {
                    lastChip = true
                    break
                } else {
                    sum++
                }
                i += dx
                j += dy
            }
            directions.add(lastChip && sum > 0)
        }
        return if (directions.contains(true))
            directions
        else
            null
    }

    fun blackAndWhiteScore(): Pair<Int, Int> {
        var black = 0
        var white = 0
//        = val black = cells.map { a -> a.filter { b -> b == ChipValue.BLACK }.count() }.sum() to
//        val white = cells.map { a1 -> a1.filter { b -> b == ChipValue.WHITE }.count() }.sum()
        for (i in 0..7)
            for (j in 0..7) {
                if (cells[i][j] == ChipValue.BLACK) black++
                else if (cells[i][j] == ChipValue.WHITE) white++
            }
        return black to white
    }
}