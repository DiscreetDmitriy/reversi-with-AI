package reversiTest

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reversi.model.ChipValue.*
import reversi.model.Field
import reversi.model.Player

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReversiTest {
    private val field = Field()
    private val fieldArray = field.fieldArray

    @Suppress("unused")
    fun printField(cells: List<List<Any>>) {
        println(cells[0])
        println(cells[1])
        println(cells[2])
        println(cells[3])
        println(cells[4])
        println(cells[5])
        println(cells[6])
        println(cells[7])
    }

    @Test
    fun `Starting position`() {
        assertEquals(WHITE, fieldArray[3][3])
        assertEquals(WHITE, fieldArray[4][4])
        assertEquals(BLACK, fieldArray[3][4])
        assertEquals(BLACK, fieldArray[4][3])
        assertEquals(EMPTY, fieldArray[0][0])
        assertEquals(EMPTY, fieldArray[7][7])
        assertThrows(ArrayIndexOutOfBoundsException::class.java) { fieldArray[-4][10] }
        assertEquals(2 to 2, field.blackAndWhiteScore())
    }

    @Nested
    inner class Dimensions {
        @Test
        fun `corner of the screen`() {
            fieldArray[7][6] = BLACK
            fieldArray[7][5] = WHITE
            assertEquals(
                listOf(false, true, false, false, false, false, false, false),
                field.trueDirections(7, 7, Player(WHITE))
            )
            field.restart()
        }

        @Test
        fun `near the center`() {
            assertEquals(
                listOf(false, false, false, false, true, false, false, false),
                field.trueDirections(2, 4, Player(WHITE))
            )
            assertEquals(
                listOf(false, true, false, false, false, false, false, false),
                field.trueDirections(3, 5, Player(WHITE))
            )
            assertEquals(
                listOf(false, false, false, false, false, false, true, false),
                field.trueDirections(4, 2, Player(WHITE))
            )
            assertEquals(
                listOf(false, false, false, true, false, false, false, false),
                field.trueDirections(5, 3, Player(WHITE))
            )
            assertEquals(
                listOf(false, false, false, false, false, false, true, false),
                field.trueDirections(3, 2, Player(BLACK))
            )
            assertEquals(
                listOf(false, false, false, false, true, false, false, false),
                field.trueDirections(2, 3, Player(BLACK))
            )
            assertEquals(
                listOf(false, true, false, false, false, false, false, false),
                field.trueDirections(4, 5, Player(BLACK))
            )
            assertEquals(
                listOf(false, false, false, true, false, false, false, false),
                field.trueDirections(5, 4, Player(BLACK))
            )
        }

        @Test
        fun `behind available chip`() {
            fieldArray[4][5] = OCCUPIABLE
            assertEquals(listOf<Boolean>(), field.trueDirections(4, 6, Player(WHITE)))
            assertEquals(listOf<Boolean>(), field.trueDirections(4, 7, Player(WHITE)))
            field.restart()
        }

        @Test
        fun `on the chip`() =
            assertEquals(listOf<Boolean>(), field.trueDirections(3, 4, Player(BLACK)))
    }

    @Test
    fun `put chip`() {

        val player = Player(BLACK)
        field.getAvailableCells(player)
        field.makeTurn(3, 2, player)

        assertEquals(BLACK, fieldArray[3][2])
        assertEquals(BLACK, fieldArray[3][3])
        assertEquals(BLACK, fieldArray[4][3])
        assertEquals(BLACK, fieldArray[3][4])
        assertEquals(WHITE, fieldArray[4][4])

        field.getAvailableCells(player)
        field.makeTurn(2, 2, player)

        assertEquals(BLACK, fieldArray[3][2])
        assertEquals(BLACK, fieldArray[4][3])
        assertEquals(BLACK, fieldArray[3][4])
        assertEquals(WHITE, fieldArray[2][2])
        assertEquals(WHITE, fieldArray[3][3])
        assertEquals(WHITE, fieldArray[4][4])
    }

    @Test
    fun `a few turns`() {
        val p = Player(BLACK)
        field.getAvailableCells(p)
        field.makeTurn(5, 4, p)
        field.getAvailableCells(p)
        field.makeTurn(5, 3, p)
        field.getAvailableCells(p)
        field.makeTurn(4, 2, p)

        assertEquals(BLACK, fieldArray[4][2])
        assertEquals(BLACK, fieldArray[4][3])
        assertEquals(BLACK, fieldArray[4][4])
        assertEquals(BLACK, fieldArray[3][4])
        assertEquals(BLACK, fieldArray[5][4])
        assertEquals(WHITE, fieldArray[3][3])
        assertEquals(WHITE, fieldArray[5][3])

        field.getAvailableCells(p)
        field.makeTurn(3, 5, p)
        printField(fieldArray)

        field.apply { restart() }
    }

    @Test
    fun `player can move`() {
        val p = Player(BLACK)
        field.getAvailableCells(p)
        assertTrue(p.playerCanMove)
    }
}

