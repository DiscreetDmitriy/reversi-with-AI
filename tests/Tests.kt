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
        assertEquals(Polynom(3, 2, 2, 2, 2), (Polynom(2, 1, 1, 1, 1) + Polynom(1, 1, 1, 1, 1)))
    }

    @Test
    fun minus() {
        assertEquals(Polynom(), (Polynom() - Polynom()))
        assertEquals(Polynom(0), (Polynom(3) - Polynom(3)))
        assertEquals(Polynom(1, 1, 1, 0, 1), (Polynom(3, 2, 2, 2, 1) - Polynom(2, 1, 1, 2, 0)))
        assertEquals(Polynom(-2, 1, 1, 0, 1), (Polynom(0, 2, 2, 2, 1) - Polynom(2, 1, 1, 2, 0)))
        assertEquals(Polynom(-2, 1, 1, 0, 1), (Polynom( 2, 2, 2, 1) - Polynom(2, 1, 1, 2, 0)))
    }

    @Test
    fun pow() {
        println(Polynom(1, 4, 5).powerToConst())
    }

    @Test
    fun times() {
        assertEquals(Polynom(1, 5, 6), Polynom(1, 2) * Polynom(1, 3))
        assertEquals(
            Polynom(14, 13, -2, 6, -11, -10, -13, -7, -8),
            Polynom(7, 3, -6, 1, -8) * Polynom(2, 1, 1, 1, 1)
        )
    }

      @Test
      fun div() {
          assertEquals(Polynom(0), Polynom(1,6,3,8,36) / Polynom(1,2))
      }

    @Test
    fun count() {
        assertEquals(0, Polynom(0) count 200)
        assertEquals(8, Polynom(2, 0, 0) count 2)
        assertEquals(-5, Polynom(1, 1, 1, 1, 1, -10) count 1)
        assertEquals(-3, Polynom(7, 3, -6, 1, -8) count 1)
    }

    @Test
    fun toStr() {
        println(Polynom().toString())
        println(Polynom(0).toString())
        println(Polynom(1, 2).toString())
        println(Polynom(3, 1, 1).toString())
        println(Polynom(-1).toString())
        println(Polynom(3, 0, 0).toString())
        println(Polynom(3, 0, 0, 6, 3, 0, -1, 1, 1, 2, 1).toString())
        println(Polynom(0, 0, 0, 2, 1, 1).toString())
        println(Polynom(7, 3, -6, 1, -8).toString())
        println(Polynom(14, 13, -2, 6, -11, -10, -13, -7, -8).toString())
    }
}