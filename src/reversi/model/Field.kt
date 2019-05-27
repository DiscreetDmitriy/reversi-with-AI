package reversi.model

class Field {

    private val player = Player(ChipValue.BLACK)
    private val fieldArray =
        List(FIELD_SIZE) { MutableList(FIELD_SIZE) { ChipValue.EMPTY } }
            .apply {
                this[3][3] = ChipValue.WHITE
                this[4][4] = ChipValue.WHITE
                this[3][4] = ChipValue.BLACK
                this[4][3] = ChipValue.BLACK

                this[3][2] = ChipValue.OCCUPIABLE
                this[2][3] = ChipValue.OCCUPIABLE
                this[5][4] = ChipValue.OCCUPIABLE
                this[4][5] = ChipValue.OCCUPIABLE
            }


    fun restart() {
        for (i in 0 until FIELD_SIZE)
            for (j in 0 until FIELD_SIZE)
                fieldArray[i][j] = ChipValue.EMPTY

        fieldArray[3][3] = ChipValue.WHITE
        fieldArray[4][4] = ChipValue.WHITE
        fieldArray[3][4] = ChipValue.BLACK
        fieldArray[4][3] = ChipValue.BLACK

        fieldArray[3][2] = ChipValue.OCCUPIABLE
        fieldArray[2][3] = ChipValue.OCCUPIABLE
        fieldArray[5][4] = ChipValue.OCCUPIABLE
        fieldArray[4][5] = ChipValue.OCCUPIABLE

        player.playerChip = ChipValue.BLACK
    }

    fun getCell(x: Int, y: Int): ChipValue = fieldArray[x][y]

    fun getCurrentPlayer(): Player = player


    private val directions = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)

    fun correctDirections(x: Int, y: Int, player: Player): List<Boolean> {

        if (fieldArray[x][y] == ChipValue.BLACK || fieldArray[x][y] == ChipValue.WHITE) return listOf()

        val resDirections = mutableListOf<Boolean>()

        for (dir in directions) {

            var lastChip = false
            var i = x
            var j = y
            var oppositeChipsBetween = 0

            while (i in 0 until FIELD_SIZE && j in 0 until FIELD_SIZE) {

                val dx = i + dir.second
                val dy = j + dir.first

                if (dx !in 0..7 || dy !in 0..7) break
                if (fieldArray[dx][dy] == ChipValue.EMPTY || fieldArray[dx][dy] == ChipValue.OCCUPIABLE) break

                if (fieldArray[dx][dy] == player.opposite()) oppositeChipsBetween++
                else if (fieldArray[dx][dy] == player.playerChip) {
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

    fun blackAndWhiteScore(): Pair<Int, Int> {
        var black = 0
        var white = 0
        for (i in 0 until FIELD_SIZE)
            for (j in 0 until FIELD_SIZE) {
                if (fieldArray[i][j] == ChipValue.BLACK) black++
                else if (fieldArray[i][j] == ChipValue.WHITE) white++
            }
        return black to white
    }

    fun hasFreeCells(): Boolean = fieldArray.any { row -> row.any { it == ChipValue.OCCUPIABLE } }

    fun makeTurn(x: Int, y: Int, player: Player) {
        val dirs = correctDirections(x, y, player)

        if (fieldArray[x][y] != ChipValue.OCCUPIABLE) throw IllegalArgumentException()
        if (dirs.isEmpty()) throw IllegalArgumentException()

        fieldArray[x][y] = player.playerChip

        for (dir in directions) {
            var i = x
            var j = y

            if (!dirs[directions.indexOf(dir)]) continue

            while (fieldArray[i + dir.second][j + dir.first] == player.opposite()) {
                fieldArray[i + dir.second][j + dir.first] = player.playerChip
                i += dir.second
                j += dir.first
            }
        }

        for (row in 0 until FIELD_SIZE)
            for (column in 0 until FIELD_SIZE)
                if (fieldArray[row][column] == ChipValue.OCCUPIABLE)
                    fieldArray[row][column] = ChipValue.EMPTY

        player.changePlayer()

        for (i in 0 until FIELD_SIZE)
            for (j in 0 until FIELD_SIZE) {
                if (correctDirections(i, j, player).isNotEmpty()) {
                    fieldArray[i][j] = ChipValue.OCCUPIABLE
                    player.playerCanMove = true
                }
            }
    }

    companion object {
        const val FIELD_SIZE = 8
    }
}


