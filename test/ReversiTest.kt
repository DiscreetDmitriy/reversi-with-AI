import model.Chip.*
import model.Field
import model.HumanPlayer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReversiTest {
    private val field = Field()

    @Test
    fun `Starting position`() {
        assertEquals(WHITE, field.chip(3, 3))
        assertEquals(WHITE, field.chip(4, 4))
        assertEquals(BLACK, field.chip(3, 4))
        assertEquals(BLACK, field.chip(4, 3))
        assertEquals(EMPTY, field.chip(0, 0))
        assertEquals(EMPTY, field.chip(7, 7))
        assertThrows(ArrayIndexOutOfBoundsException::class.java) { field.chip(-4, 0) }
        assertEquals(2 to 2, field.score())
    }

    @Nested
    inner class Dimensions {
        @Test
        fun `near the center`() {
            field.restart()
            assertEquals(
                listOf(false, false, false, false, true, false, false, false),
                field.correctDirections(2, 4, HumanPlayer(WHITE))
            )
            assertEquals(
                listOf(false, true, false, false, false, false, false, false),
                field.correctDirections(3, 5, HumanPlayer(WHITE))
            )
            assertEquals(
                listOf(false, false, false, false, false, false, true, false),
                field.correctDirections(4, 2, HumanPlayer(WHITE))
            )
            assertEquals(
                listOf(false, false, false, true, false, false, false, false),
                field.correctDirections(5, 3, HumanPlayer(WHITE))
            )
            assertEquals(
                listOf(false, false, false, false, false, false, true, false),
                field.correctDirections(3, 2, HumanPlayer(BLACK))
            )
            assertEquals(
                listOf(false, false, false, false, true, false, false, false),
                field.correctDirections(2, 3, HumanPlayer(BLACK))
            )
            assertEquals(
                listOf(false, true, false, false, false, false, false, false),
                field.correctDirections(4, 5, HumanPlayer(BLACK))
            )
            assertEquals(
                listOf(false, false, false, true, false, false, false, false),
                field.correctDirections(5, 4, HumanPlayer(BLACK))
            )
        }

        @Test
        fun `on the chip`() =
            assertEquals(listOf<Boolean>(), field.correctDirections(3, 4, HumanPlayer(BLACK)))
    }

    @Test
    fun `put chip`() {
        field.makeTurn(3, 2,field.currentPlayer())

        assertEquals(BLACK, field.chip(3, 2))
        assertEquals(BLACK, field.chip(3, 3))
        assertEquals(BLACK, field.chip(4, 3))
        assertEquals(BLACK, field.chip(3, 4))
        assertEquals(WHITE, field.chip(4, 4))

        field.makeTurn(2, 2, field.currentPlayer())

        assertEquals(BLACK, field.chip(3, 2))
        assertEquals(BLACK, field.chip(4, 3))
        assertEquals(BLACK, field.chip(3, 4))
        assertEquals(WHITE, field.chip(2, 2))
        assertEquals(WHITE, field.chip(3, 3))
        assertEquals(WHITE, field.chip(4, 4))
    }

    @Test
    fun `a few turns`() {
        field.makeTurn(5, 4,field.currentPlayer())
        field.makeTurn(5, 3,field.currentPlayer())
        field.makeTurn(4, 2,field.currentPlayer())

        assertEquals(BLACK, field.chip(4, 2))
        assertEquals(BLACK, field.chip(4, 3))
        assertEquals(BLACK, field.chip(4, 4))
        assertEquals(BLACK, field.chip(3, 4))
        assertEquals(BLACK, field.chip(5, 4))
        assertEquals(WHITE, field.chip(3, 3))
        assertEquals(WHITE, field.chip(5, 3))

        field.apply { restart() }
    }
}

