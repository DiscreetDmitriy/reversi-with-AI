package grepTest

import grep.Grep
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.io.File

class GrepTest {

    private fun assertFileContent(fileName: String, expectedContent: String) {
        val file = File(fileName)
        val content = file.readLines().joinToString("\n")
        assertEquals(expectedContent, content)
    }

    @Test
    fun flag000() {
        Grep("files/input.txt", "a").find(v = false, i = false, r = false)
        assertFileContent("files/output.txt", "a 1")
    }

    @Test
    fun flag001() {
        Grep("files/input.txt", "[1,2]").find(v = false, i = false, r = true)
        assertFileContent(
            "files/output.txt", """a 1
            |b 2""".trimMargin()
        )
    }

    @Test
    fun flag010() {
        Grep("files/input.txt", "a").find(v = false, i = true, r = false)
        assertFileContent(
            "files/output.txt", """a 1
            |A 5""".trimMargin()
        )
    }

    @Test
    fun flagThrows() {
        assertThrows(IllegalArgumentException::class.java) {
            Grep("files/input.txt", "[1,2]").find(v = true, i = false, r = false)
        }
    }

    @Test
    fun flag100() {
        Grep("files/input.txt", "a").find(v = true, i = false, r = false)
        assertFileContent(
            "files/output.txt", """b 2
                |c 3
                |d 4
                |A 5
            """.trimMargin()
        )
    }


    @Test
    fun flag101() {
        Grep("files/input.txt", "[1,2]").find(v = true, i = false, r = true)
        assertFileContent(
            "files/output.txt",
            """c 3
            |d 4
            |A 5""".trimMargin()
        )
    }

    @Test
    fun flag110() {
        Grep("files/input.txt", "a").find(v = true, i = true, r = false)
        assertFileContent(
            "files/output.txt",
            """b 2
            |c 3
            |d 4""".trimMargin()
        )
    }

    @Test
    fun flag011() {
        Grep("files/input.txt", "z").find(v = false, i = true, r = true)
        assertFileContent("files/output.txt", "")
    }

    @Test
    fun flag111() {
        Grep("files/input.txt", "a").find(v = true, i = true, r = true)
        assertFileContent(
            "files/output.txt",
            """b 2
            |c 3
            |d 4""".trimMargin()
        )
    }

}


