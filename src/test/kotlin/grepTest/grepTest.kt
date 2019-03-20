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
    fun flagTests() {
        Grep("files/input.txt", "a").find(v = false, i = false, r = false)
        assertFileContent("output.txt", "a 1")

        Grep("files/input.txt", "[1,2]").find(v = false, i = false, r = true)
        assertFileContent(
            "output.txt", """a 1
            |b 2""".trimMargin()
        )

        Grep("files/input.txt", "a").find(v = false, i = true, r = false)
        assertFileContent(
            "output.txt", """a 1
            |A 5""".trimMargin()
        )

        assertThrows(IllegalArgumentException::class.java) {
            Grep("files/input.txt", "[1,2]").find(v = true, i = false, r = false)
        }

        Grep("files/input.txt", "a").find(v = true, i = false, r = false)
        assertFileContent(
            "output.txt", """b 2
                |c 3
                |d 4
                |A 5
            """.trimMargin()
        )


        Grep("files/input.txt", "[1,2]").find(v = true, i = false, r = true)
        assertFileContent(
            "output.txt",
            """c 3
            |d 4
            |A 5""".trimMargin()
        )

        Grep("files/input.txt", "a").find(v = true, i = true, r = false)
        assertFileContent(
            "output.txt",
            """b 2
            |c 3
            |d 4""".trimMargin()
        )

        Grep("files/input.txt", "z").find(v = false, i = true, r = true)
        assertFileContent("output.txt", "")

        Grep("files/input.txt", "a").find(v = true, i = true, r = true)
        assertFileContent(
            "output.txt",
            """b 2
            |c 3
            |d 4""".trimMargin()
        )
        File("output.txt").delete()
    }
}

