package reversiTest

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reversi.model.ChipValue.*
import reversi.model.Field
import reversi.model.Player

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReversiTest {
    private val fieldClass = Field().apply { restart() }
    private val field = fieldClass.field

    @Test
    fun `Starting position`() {
        assertEquals(WHITE, field[3][4])
        assertEquals(WHITE, field[4][3])
        assertEquals(BLACK, field[3][3])
        assertEquals(BLACK, field[4][4])
        assertEquals(EMPTY, field[0][0])
        assertEquals(EMPTY, field[7][7])
        assertThrows(ArrayIndexOutOfBoundsException::class.java) { field[-4][10] }
        assertEquals(2 to 2, fieldClass.blackAndWhiteScore())
    }

    @Nested
    inner class Dimensions {
        @Test
        fun `corner of the screen`() {
            field[7][6] = BLACK
            field[7][5] = WHITE
            assertEquals(
                listOf(false, true, false, false, false, false, false, false),
                fieldClass.trueDirections(7, 7, Player(WHITE))
            )
            fieldClass.restart()
        }

        @Test
        fun `near the center`() {
            assertEquals(
                listOf(false, true, false, false, false, false, false, false),
                fieldClass.trueDirections(4, 5, Player(WHITE))
            )
            assertEquals(
                listOf(false, false, false, true, false, false, false, false),
                fieldClass.trueDirections(5, 4, Player(WHITE))
            )
        }

        @Test
        fun `on the chip`() =
            assertEquals(listOf<Boolean>(), fieldClass.trueDirections(3, 4, Player(BLACK)))
    }

    @Test
    fun `get free cells`() {
        val player = Player(BLACK)
        val freeCells = fieldClass.getFreeCells(player)
        println(freeCells[7])
        println(freeCells[6])
        println(freeCells[5])
        println(freeCells[4])
        println(freeCells[3])
        println(freeCells[2])
        println(freeCells[1])
        println(freeCells[0])
        assertEquals(true, freeCells[3][2])
        assertEquals(true, freeCells[2][3])
        assertEquals(true, freeCells[4][5])
        assertEquals(true, freeCells[5][4])
//        for (i in 0..7)
//            for (j in 0..7)
//                if (i == 2 && j == 3 || i == 3 && j == 2 || i == 4 && j == 5 || i == 5 && j == 4) {
//                    assertEquals(true, fieldClass.getFreeCells(player)[j][i])
//                    println("$i, $j, ${field[i][j]}")
//                } else {
//                    assertEquals(false, fieldClass.getFreeCells(player)[j][i])
//                    println("$i, $j, ${field[i][j]}")
//                }
    }

    @Nested
    inner class TestofFeature {
        @Test
        fun `1`() {
            assert(true)
        }

        @Test
        fun `2`() {
            assert(true)
        }
    }
}

