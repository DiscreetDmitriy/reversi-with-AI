package reversiTest

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*

class ReversiTest {
    @Test
    fun `Test of something`() {
        assertEquals(true,true)
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