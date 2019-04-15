@file:Suppress("MemberVisibilityCanBePrivate")

package reversi.model

class Field {
    private val emptyField = MutableList(8) { MutableList(8) { ChipValue.EMPTY } }
    val field = emptyField.apply { restart() }

    fun restart() {
        for (i in 0..7)
            for (j in 0..7)
                emptyField[i][j] = ChipValue.EMPTY

        emptyField[3][4] = ChipValue.BLACK
        emptyField[4][3] = ChipValue.BLACK
        emptyField[3][3] = ChipValue.WHITE
        emptyField[4][4] = ChipValue.WHITE
    }

    private val directions = listOf(-1 to -1, -1 to 0, -1 to -1, 0 to -1, 0 to 1, 1 to 0, 1 to 1)

    fun trueDirections(x: Int, y: Int, player: Player): List<Boolean> {

        if (field[x][y] != ChipValue.EMPTY) return listOf()

        val resDirections = mutableListOf<Boolean>()

        for (dir in directions) {

            var lastChip = false
            var i = x
            var j = y
            var sum = 0

            while (i in 0..7 && j in 0..7) {
                if (field[i + dir.first][j + dir.second] == ChipValue.EMPTY) break
                if (field[i + dir.first][j + dir.second] == player.playerChip) {
                    lastChip = true
                    break
                } else sum++

                i += dir.first
                j += dir.second

            }
            resDirections.add(lastChip && sum > 0)
        }
        return if (resDirections.contains(true)) resDirections else listOf()
    }

    fun getFreeCells(player: Player): Array<BooleanArray> {
        val freeCells = Array(8) { BooleanArray(8) }
        var value: Boolean
        player.playerCanMove = false
        for (i in 0..7) {
            for (j in 0..7) {
                value = trueDirections(i, j, player) != listOf<Boolean>()
                freeCells[i][j] = value
                field[i][j] = ChipValue.OCCUPIABLE
                if (value) player.playerCanMove = true
            }
        }
        return freeCells
    }

    fun blackAndWhiteScore(): Pair<Int, Int> {
        var black = 0
        var white = 0
//        = val black = field.map { a -> a.filter { b -> b == ChipValue.BLACK }.count() }.sum() to
//        val white = field.map { a1 -> a1.filter { b -> b == ChipValue.WHITE }.count() }.sum()
        for (i in 0..7)
            for (j in 0..7) {
                if (field[i][j] == ChipValue.BLACK) black++
                else if (field[i][j] == ChipValue.WHITE) white++
            }
        return black to white
    }
}