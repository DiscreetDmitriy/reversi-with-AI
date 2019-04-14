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

    fun trueDirections(x: Int, y: Int, player: Player): List<Boolean> {

        if (field[x][y] != ChipValue.EMPTY) return listOf()

        val directions = mutableListOf<Boolean>()

        for (dx in -1..1)
            for (dy in -1..1) {

                var lastChip = false
                var i = x
                var j = y
                var sum = 0

                while (i in 0..7 && j in 0..7) {
                    if (field[i + dx][j + dy] == ChipValue.EMPTY) break
                    if (field[i + dx][j + dy] == player.playerChip) {
                        lastChip = true
                        break
                    } else sum++

                    i += dx
                    j += dy

                }
                directions.add(lastChip && sum > 0)
            }
        return if (directions.contains(true)) directions else listOf()
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