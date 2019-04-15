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
    private val field = Field().field
    val freeCells = Field().getFreeCells(Player(BLACK))

    @Test
    fun `Starting position`() {
        assertEquals(BLACK, field[3][4])
        assertEquals(BLACK, field[4][3])
        assertEquals(WHITE, field[3][3])
        assertEquals(WHITE, field[4][4])
        assertEquals(EMPTY, field[0][0])
        assertEquals(EMPTY, field[7][7])
        assertThrows(ArrayIndexOutOfBoundsException::class.java) { field[-4][10] }
        assertEquals(2 to 2, Field().blackAndWhiteScore())
    }

    @Test
    fun `directions `() {
        assertEquals(listOf<Boolean>(), Field().trueDirections(5,4 , Player(BLACK)))
        for (i in 0..7) {
            for (j in 0..7) {
                if (i == 2 && j == 3 || i == 3 && j == 2 || i == 4 && j == 5 || i == 5 && j == 4) {
                    assertEquals(true, freeCells[i][j])
                } else {
                    assertEquals(false, freeCells[i][j])
                }
            }
        }
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