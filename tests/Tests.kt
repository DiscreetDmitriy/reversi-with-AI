package tests

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import intPolynom.*

class Tests {

    @Test
    fun equals() {
        assertTrue(Polynom(0) == Polynom())
        assertTrue(
            Polynom(14, 13, -2, 6, -11, -10, -13, -7, -8) ==
                    Polynom(14, 13, -2, 6, -11, -10, -13, -7, -8)
        )
    }

    @Test
    fun plus() {
        assertEquals(Polynom(0), Polynom(0) + Polynom())
        assertEquals(Polynom(-1), Polynom(5) + Polynom(-6))
        assertEquals(Polynom(1, 1, 1, 1), Polynom(0) + Polynom(1, 1, 1, 1))
        assertEquals(Polynom(1, 1, 1, 2), Polynom(1) + Polynom(1, 1, 1, 1))
        assertEquals(Polynom(1, 1, 1, 1), Polynom(1, 1, 1, 1) + Polynom(0))
        assertEquals(Polynom(3, 2, 2, 2), Polynom(2, 1, 1, 1) + Polynom(1, 1, 1, 1))
        assertEquals(Polynom(1, 1, 3, 2, 2), Polynom(2, 1, 1) + Polynom(1, 1, 1, 1, 1))
    }

    @Test
    fun minus() {
        assertEquals(Polynom(), (Polynom() - Polynom()))
        assertEquals(Polynom(0), (Polynom(3) - Polynom(3)))
        assertEquals(Polynom(3, 2, 0, 1, 0), Polynom(3, 2, 2, 2, 1) - Polynom(2, 1, 1))
        assertEquals(Polynom(-2, 1, 1, 0, 1), Polynom(0, 2, 2, 2, 1) - Polynom(2, 1, 1, 2, 0))
        assertEquals(Polynom(-2, 1, 1, 0, 1), Polynom(2, 2, 2, 1) - Polynom(2, 1, 1, 2, 0))
    }

    @Test
    fun pow() {
        assertEquals(mapOf(2 to 1, 1 to 4, 0 to 5), Polynom(1, 4, 5).powerToConst())
        assertEquals(mapOf(0 to 0), Polynom(0).powerToConst())
        assertEquals(mapOf(0 to 1), Polynom(0, 0, 0, 0, 1).powerToConst())
        assertEquals(mapOf(2 to 3, 1 to 2, 0 to 1), Polynom(3, 2, 1).powerToConst())
        assertEquals(mapOf(2 to 1, 1 to 2, 0 to 3), Polynom(1, 2, 3).powerToConst())
    }

    @Test
    fun times() {
        assertEquals(Polynom(), Polynom() * Polynom())
        assertEquals(Polynom(3, 3, 3), Polynom(1, 1, 1) * Polynom(3))
        assertEquals(Polynom(1, 5, 6), Polynom(1, 2) * Polynom(1, 3))
        assertEquals(
            Polynom(14, 13, -2, 6, -11, -10, -13, -7, -8),
            Polynom(7, 3, -6, 1, -8) * Polynom(2, 1, 1, 1, 1)
        )
    }

    @Test
    fun divToMod() {
        assertEquals(Polynom(1, 4, -5, 18) to Polynom(), Polynom(1, 6, 3, 8, 36) divToMod Polynom(1, 2))
        assertEquals(Polynom(2, 0, 9, -5) to Polynom(1, -1, 1, 0, 23), Polynom(5, 3, 19, 8, 13) divToMod Polynom(2, 2))
        assertEquals(
            Polynom(7, 0, -8, 11, -8, -8, 9) to Polynom(-1, 0, 0, -1, 0, 1, -9, -26),
            Polynom(14, 13, -2, 6, -11, -10, -13, -7, -8) divToMod Polynom(2, 2, 2)
        )
        assertEquals(Polynom(2) to Polynom(1, 1, 1), Polynom(5, 5, 5) divToMod Polynom(2, 2, 2))
        assertEquals(Polynom() to Polynom(), Polynom() divToMod Polynom(0))
        assertEquals(Polynom(3) to Polynom(0), Polynom(0, 6) divToMod Polynom(0, 0, 0, 2))
        assertEquals(Polynom(1) to Polynom(0), Polynom(5, 0, 0, 0, 0) divToMod Polynom(5, 0, 0, 0, 0))
        assertEquals(Polynom(1, 2, 1) to Polynom(0), Polynom(1, 3, 3, 1) divToMod Polynom(1, 1))
        assertEquals(Polynom(1) to Polynom(2), Polynom(5) divToMod Polynom(3))
        assertEquals(Polynom(1, -2) to Polynom(), Polynom(1, 0, -4) divToMod Polynom(1, 2))
    }

    @Test
    fun div() {
        assertEquals(Polynom(), Polynom() / Polynom())
        assertEquals(Polynom(), Polynom(0, 0, 0, 4, 1) / Polynom(1, 3, 0, 0, 0))
        assertEquals(Polynom(2), Polynom(5, 5, 5, 5) / Polynom(2, 2, 2, 2))
        assertEquals(Polynom(1, 4, -5, 18), Polynom(1, 6, 3, 8, 36) / Polynom(1, 2))
    }

    @Test
    fun mod() {
        assertEquals(Polynom(), Polynom() % Polynom())
        assertEquals(Polynom(0), Polynom(1, 2, 1) % Polynom(1, 1))
        assertEquals(Polynom(5), Polynom(1, 6, 3, 8, 41) % Polynom(1, 2))
        assertEquals(Polynom(2, 1, -14, 6), Polynom(14, 13, -2, 6) % Polynom(3, 3, 3))
    }

    @Test
    fun count() {
        assertEquals(0, Polynom(0) count 200)
        assertEquals(8, Polynom(2, 0, 0) count 2)
        assertEquals(-5, Polynom(1, 1, 1, 1, 1, -10) count 1)
        assertEquals(-3, Polynom(7, 3, -6, 1, -8) count 1)
        assertEquals(-1345311418, Polynom(14, 13, -2, 6, -11, -10, -13, -7, -8) count 19)
    }

    @Test
    fun toStr() {
        assertEquals("0", Polynom().toString())
        assertEquals("1", Polynom(1).toString())
        assertEquals("8", Polynom(8).toString())
        assertEquals("0", Polynom(0).toString())
        assertEquals("x + 2", Polynom(1, 2).toString())
        assertEquals("3x^2 + x + 1", Polynom(3, 1, 1).toString())
        assertEquals("-1", Polynom(-1).toString())
        assertEquals("3x^2", Polynom(3, 0, 0).toString())
        assertEquals("2x^2 + x + 1", Polynom(0, 0, 0, 2, 1, 1).toString())
        assertEquals("7x^4 + 3x^3 - 6x^2 + x - 8", Polynom(7, 3, -6, 1, -8).toString())
        assertEquals(
            "3x^10 + 6x^7 + 3x^6 - x^4 + x^3 + x^2 + 2x + 1",
            Polynom(3, 0, 0, 6, 3, 0, -1, 1, 1, 2, 1).toString()
        )
        assertEquals(
            "14x^8 + 13x^7 - 2x^6 + 6x^5 - 11x^4 - 10x^3 - 13x^2 - 7x - 8",
            Polynom(14, 13, -2, 6, -11, -10, -13, -7, -8).toString()
        )
    }
}