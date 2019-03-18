package grep

import java.io.*
import java.lang.IllegalArgumentException

class Grep(inputName: String, val word: String) {
    val inputFile = File(inputName)

    @Throws(IOException::class)
    fun find(v: Boolean, i: Boolean, r: Boolean) {

        if (!r && !word.matches(Regex("^[a-zA-Zа-яА-ЯёЁ]+$")))
            throw IllegalArgumentException()

        for (str in inputFile.readLines()) {
            val regex =
                if (i) word.toRegex(RegexOption.IGNORE_CASE) else word.toRegex()

            val containsMatchIn =
                if (v) !regex.containsMatchIn(str) else regex.containsMatchIn(str)

            if (containsMatchIn) println(str)
        }
    }

}