package reversiTest

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import reversi.model.ChipValue.*
import reversi.model.Field

class ReversiTest {
    @Test
    fun `Starting position`() {
        val field = Field()
        field.restart()
        val cells = field.cells

        assertEquals(BLACK, cells[3][4])
        assertEquals(BLACK, cells[4][3])
        assertEquals(WHITE, cells[3][3])
        assertEquals(WHITE, cells[4][4])
        assertEquals(EMPTY, cells[0][0])
        assertEquals(EMPTY, cells[7][7])
        assertThrows(ArrayIndexOutOfBoundsException::class.java) { cells[-4][10] }
        cells[3][5] = WHITE
        assertEquals(2 to 3, field.blackAndWhiteScore())
    }

    @Test
    fun `directions `() {

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