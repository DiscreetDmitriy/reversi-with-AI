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
    private val fieldClass = Field()
    private val field = fieldClass.field

    @Suppress("unused")
    fun printField(cells: List<List<Any>>) {
        println(cells[0].reversed())
        println(cells[1].reversed())
        println(cells[2].reversed())
        println(cells[3].reversed())
        println(cells[4].reversed())
        println(cells[5].reversed())
        println(cells[6].reversed())
        println(cells[7].reversed())
    }

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
                listOf(false, false, false, false, true, false, false, false),
                fieldClass.trueDirections(2, 4, Player(BLACK))
            )
            assertEquals(
                listOf(false, true, false, false, false, false, false, false),
                fieldClass.trueDirections(3, 5, Player(BLACK))
            )
            assertEquals(
                listOf(false, false, false, false, false, false, true, false),
                fieldClass.trueDirections(4, 2, Player(BLACK))
            )
            assertEquals(
                listOf(false, false, false, true, false, false, false, false),
                fieldClass.trueDirections(5, 3, Player(BLACK))
            )
            assertEquals(
                listOf(false, false, false, false, false, false, true, false),
                fieldClass.trueDirections(3, 2, Player(WHITE))
            )
            assertEquals(
                listOf(false, false, false, false, true, false, false, false),
                fieldClass.trueDirections(2, 3, Player(WHITE))
            )
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
        fun `behind available chip`() {
            field[4][5] = OCCUPIABLE
            assertEquals(listOf<Boolean>(), fieldClass.trueDirections(4, 6, Player(WHITE)))
            assertEquals(listOf<Boolean>(), fieldClass.trueDirections(4, 7, Player(WHITE)))
            fieldClass.restart()
        }

        @Test
        fun `on the chip`() =
            assertEquals(listOf<Boolean>(), fieldClass.trueDirections(3, 4, Player(BLACK)))
    }

    @Test
    fun `get free cells`() {
        val player = Player(BLACK)
        var freeCells = fieldClass.getFreeCells(player)

        assertEquals(true, freeCells[3][5])
        assertEquals(true, freeCells[2][4])
        assertEquals(true, freeCells[4][2])
        assertEquals(true, freeCells[5][3])

        freeCells = fieldClass.getFreeCells(Player(WHITE))

        for (i in 0..7)
            for (j in 0..7)
                if (i == 2 && j == 3 || i == 3 && j == 2 || i == 4 && j == 5 || i == 5 && j == 4)
                    assertEquals(true, freeCells[i][j])
                else
                    assertEquals(false, freeCells[i][j])
    }

    @Test
    fun `put chip`() {

        val player = Player(BLACK)
        fieldClass.getFreeCells(player)
        fieldClass.putChip(5, 3, player)

        assertEquals(BLACK, field[3][3])
        assertEquals(BLACK, field[4][3])
        assertEquals(BLACK, field[5][3])
        assertEquals(WHITE, field[3][4])
        assertEquals(BLACK, field[4][4])

        player.changePlayer()
        fieldClass.getFreeCells(player)
        fieldClass.putChip(5, 2, player)

        assertEquals(BLACK, field[3][3])
        assertEquals(BLACK, field[4][4])
        assertEquals(BLACK, field[5][3])
        assertEquals(WHITE, field[3][4])
        assertEquals(WHITE, field[4][3])
        assertEquals(WHITE, field[5][2])
        printField(field)
    }

//    @Test
//    fun `viewModel inject check`() {
//        ViewTest().test()
//        ViewTest().fieldModel.field
//    }
}

/*
class ViewTest : View() {
    val fieldModel: FieldModel by inject()
    fun test() {
        println(fieldModel.field.value.toString())
//        ReversiTest().printField(fieldModel.field.value.toString())
    }

    override val root = label("name")
}*/

