package grep

import java.io.File
import java.io.IOException

class Grep(inputName: String, private val word: String) {
    private val inputFile = File(inputName)

    @Throws(IOException::class, IllegalArgumentException::class)
    fun find(v: Boolean, i: Boolean, r: Boolean) {
        val writer = File("grepFiles/output.txt").bufferedWriter()

        if (!r && !word.matches(Regex("^[a-zA-Zа-яА-ЯёЁ]+$")))
            throw IllegalArgumentException()

        for (line in inputFile.readLines()) {
            val regex =
                if (i) word.toRegex(RegexOption.IGNORE_CASE) else word.toRegex()

            val containsMatchIn =
                if (v) !regex.containsMatchIn(line) else regex.containsMatchIn(line)

            if (containsMatchIn) writer.write("$line\n")
        }
        writer.close()
    }

}

