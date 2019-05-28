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
    private val field = Field()

    @Test
    fun `Starting position`() {
        assertEquals(WHITE, field.getCell(3, 3))
        assertEquals(WHITE, field.getCell(4, 4))
        assertEquals(BLACK, field.getCell(3, 4))
        assertEquals(BLACK, field.getCell(4, 3))
        assertEquals(EMPTY, field.getCell(0, 0))
        assertEquals(EMPTY, field.getCell(7, 7))
        assertThrows(ArrayIndexOutOfBoundsException::class.java) { field.getCell(-4, 0) }
        assertEquals(2 to 2, field.blackAndWhiteScore())
    }

    @Nested
    inner class Dimensions {
        @Test
        fun `near the center`() {
            field.restart()
            assertEquals(
                listOf(false, false, false, false, true, false, false, false),
                field.correctDirections(2, 4, Player(WHITE))
            )
            assertEquals(
                listOf(false, true, false, false, false, false, false, false),
                field.correctDirections(3, 5, Player(WHITE))
            )
            assertEquals(
                listOf(false, false, false, false, false, false, true, false),
                field.correctDirections(4, 2, Player(WHITE))
            )
            assertEquals(
                listOf(false, false, false, true, false, false, false, false),
                field.correctDirections(5, 3, Player(WHITE))
            )
            assertEquals(
                listOf(false, false, false, false, false, false, true, false),
                field.correctDirections(3, 2, Player(BLACK))
            )
            assertEquals(
                listOf(false, false, false, false, true, false, false, false),
                field.correctDirections(2, 3, Player(BLACK))
            )
            assertEquals(
                listOf(false, true, false, false, false, false, false, false),
                field.correctDirections(4, 5, Player(BLACK))
            )
            assertEquals(
                listOf(false, false, false, true, false, false, false, false),
                field.correctDirections(5, 4, Player(BLACK))
            )
        }

        @Test
        fun `on the chip`() =
            assertEquals(listOf<Boolean>(), field.correctDirections(3, 4, Player(BLACK)))
    }

    @Test
    fun `put chip`() {
        field.makeTurn(3, 2)

        assertEquals(BLACK, field.getCell(3, 2))
        assertEquals(BLACK, field.getCell(3, 3))
        assertEquals(BLACK, field.getCell(4, 3))
        assertEquals(BLACK, field.getCell(3, 4))
        assertEquals(WHITE, field.getCell(4, 4))

        field.makeTurn(2, 2)

        assertEquals(BLACK, field.getCell(3, 2))
        assertEquals(BLACK, field.getCell(4, 3))
        assertEquals(BLACK, field.getCell(3, 4))
        assertEquals(WHITE, field.getCell(2, 2))
        assertEquals(WHITE, field.getCell(3, 3))
        assertEquals(WHITE, field.getCell(4, 4))
    }

    @Test
    fun `a few turns`() {
        field.makeTurn(5, 4)
        field.makeTurn(5, 3)
        field.makeTurn(4, 2)

        assertEquals(BLACK, field.getCell(4, 2))
        assertEquals(BLACK, field.getCell(4, 3))
        assertEquals(BLACK, field.getCell(4, 4))
        assertEquals(BLACK, field.getCell(3, 4))
        assertEquals(BLACK, field.getCell(5, 4))
        assertEquals(WHITE, field.getCell(3, 3))
        assertEquals(WHITE, field.getCell(5, 3))

        field.apply { restart() }
    }
}

