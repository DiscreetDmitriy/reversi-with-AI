package reversi.model

class Field {
    private val emptyField = List(8) { MutableList(8) { ChipValue.EMPTY } }
    val player = Player(ChipValue.BLACK)
    val fieldArray = emptyField.apply { restart() }


    fun restart() {
        for (i in 0 until FIELD_SIZE)
            for (j in 0 until FIELD_SIZE)
                emptyField[i][j] = ChipValue.EMPTY

        emptyField[3][3] = ChipValue.WHITE
        emptyField[4][4] = ChipValue.WHITE
        emptyField[3][4] = ChipValue.BLACK
        emptyField[4][3] = ChipValue.BLACK

        player.playerChip = ChipValue.BLACK
    }

    private val directions = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)

    fun trueDirections(x: Int, y: Int, player: Player): List<Boolean> {

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

    fun getAvailableCells(player: Player) {
        for (i in 0 until FIELD_SIZE)
            for (j in 0 until FIELD_SIZE) {
                if (trueDirections(i, j, player).isNotEmpty()) {
                    fieldArray[i][j] = ChipValue.OCCUPIABLE
                    player.playerCanMove = true
                }
            }
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

    fun makeTurn(x: Int, y: Int, player: Player) {
        val dirs = trueDirections(x, y, player)

        if (fieldArray[x][y] != ChipValue.OCCUPIABLE) throw IllegalArgumentException()
        if (dirs.isEmpty()) throw IllegalArgumentException()

        // добавил проверку на пропуск хода
        if (!player.playerCanMove) player.changePlayer()

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
    }
}

const val FIELD_SIZE = 8

