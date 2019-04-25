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

    private val dirs = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)

    fun trueDirections(x: Int, y: Int, player: Player): List<Boolean> {

        if (field[x][y] == ChipValue.BLACK || field[x][y] == ChipValue.WHITE) return listOf()

        val resDirections = mutableListOf<Boolean>()

        for (dir in dirs) {

            var lastChip = false
            var i = x
            var j = y
            var oppositeChipsBetween = 0

            while (i in 0..size && j in 0..size) {

                val dx = i + dir.second
                val dy = j + dir.first

                if (dx !in 0..7 || dy !in 0..7) break
                if (field[dx][dy] == ChipValue.EMPTY || field[dx][dy] == ChipValue.OCCUPIABLE) break

                if (field[dx][dy] == player.opposite()) oppositeChipsBetween++
                else if (field[dx][dy] == player.playerChip) {
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

    fun getFreeCells(player: Player): List<MutableList<Boolean>> {
        val freeCells = MutableList(8) { MutableList(8) { false } }
        for (i in 0..size)
            for (j in 0..size) {
                val value = trueDirections(i, j, player).isNotEmpty()
                freeCells[i][j] = value
                if (value) field[i][j] = ChipValue.OCCUPIABLE
                if (value) player.playerCanMove = true
            }
        return freeCells
    }

    fun blackAndWhiteScore(): Pair<Int, Int> {
        var black = 0
        var white = 0
//        = val black = fieldModel.map { a -> a.filter { b -> b == ChipValue.BLACK }.count() }.sum() to
//        val white = fieldModel.map { a1 -> a1.filter { b -> b == ChipValue.WHITE }.count() }.sum()
        for (i in 0..size)
            for (j in 0..size) {
                if (field[i][j] == ChipValue.BLACK) black++
                else if (field[i][j] == ChipValue.WHITE) white++
            }
        return black to white
    }

    fun putChip(x: Int, y: Int, player: Player) {
        val directions = trueDirections(x, y, player)

//        if (field[x][y] != ChipValue.OCCUPIABLE) throw IllegalArgumentException()
//        if (directions.isEmpty()) throw IllegalArgumentException()

        var i = x
        var j = y

        field[x][y] = player.playerChip

        for (dir in dirs) {

            if (!directions[dirs.indexOf(dir)]) continue

            val dx = i + dir.second
            val dy = j + dir.first

            while (field[dx][dy] == player.opposite()) {
                field[dx][dy] = player.playerChip
                i = dx
                j = dy
            }
        }
    }
}

