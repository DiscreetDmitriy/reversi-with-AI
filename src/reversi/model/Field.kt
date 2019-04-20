package reversi.model

class Field {
    private val emptyField = List(8) { MutableList(8) { ChipValue.EMPTY } }
    val field = emptyField.apply { restart() }
    private val size = field.size - 1


    fun restart() {
        for (i in 0..size)
            for (j in 0..size)
                emptyField[i][j] = ChipValue.EMPTY

        emptyField[3][4] = ChipValue.WHITE
        emptyField[4][3] = ChipValue.WHITE
        emptyField[3][3] = ChipValue.BLACK
        emptyField[4][4] = ChipValue.BLACK
    }

    private val directions = listOf(

        -1 to -1,
        -1 to 0,
        -1 to 1,
        0 to -1,
        0 to 1,
        1 to -1,
        1 to 0,
        1 to 1
    )

    fun trueDirections(x: Int, y: Int, player: Player): List<Boolean> {

        if (field[x][y] != ChipValue.EMPTY) return listOf()

        val resDirections = mutableListOf<Boolean>()

        for (dir in directions) {

            var lastChip = false
            var i = x
            var j = y
            var oppositeChipsBetween = 0

            while (i in 0..size && j in 0..size) {

                if (i + dir.second !in 0..7 || j + dir.first !in 0..7) break
                if (field[i + dir.second][j + dir.first] == ChipValue.EMPTY) break

                val condition = field[i + dir.second][j + dir.first] == player.playerChip

                if (!condition) oppositeChipsBetween++
                else if (condition) {
                    lastChip = true
                    break
                }


                i += dir.second
                j += dir.first
            }
            resDirections.add(lastChip && oppositeChipsBetween > 0)
        }
        return if (true in resDirections) resDirections else listOf()
    }

    fun getFreeCells(player: Player): MutableList<MutableList<Boolean>> {
        val freeCells = MutableList(8) { MutableList(8) { false } }
        for (i in 0..size)
            for (j in 0..size) {
                val value = trueDirections(i, j, player) != listOf<Boolean>()
                freeCells[i][j] = value
                field[i][j] = if (value) ChipValue.OCCUPIABLE else ChipValue.EMPTY
                if (value) player.isChanged = true
            }
        return freeCells
    }

    fun blackAndWhiteScore(): Pair<Int, Int> {
        var black = 0
        var white = 0
//        = val black = field.map { a -> a.filter { b -> b == ChipValue.BLACK }.count() }.sum() to
//        val white = field.map { a1 -> a1.filter { b -> b == ChipValue.WHITE }.count() }.sum()
        for (i in 0..size)
            for (j in 0..size) {
                if (field[i][j] == ChipValue.BLACK) black++
                else if (field[i][j] == ChipValue.WHITE) white++
            }
        return black to white
    }
}