@file:Suppress("SpellCheckingInspection")

package grep

import org.kohsuke.args4j.*
import java.io.File
import java.io.IOException
import java.lang.IllegalArgumentException

// вариант 3
class GrepLauncher {
    @Option(name = "-v", usage = "Invert filter condition")
    var v: Boolean = false

    @Option(name = "-i", usage = "Ignore word case")
    var i: Boolean = false

    @Option(name = "-r", usage = "Regex")
    var r: Boolean = false

    @Argument(required = true, metaVar = "word", usage = "word")
    var word: String = ""

    @Argument(required = true, metaVar = "inputname", index = 1, usage = "Text file")
    var inputName: String = ""

    fun launch(args: Array<String>) {
        val parser = CmdLineParser(this)

        try {
            parser.parseArgument(*args)
        } catch (e: CmdLineException) {
            println(e.message)
            println("grep [-v] [-i] [-r] word inputname.txt")
            println(parser.printUsage(System.err))
            return
        }

        try {
            Grep(inputName, word).find(v, i, r)
            File("output.txt").run {
                bufferedReader()
                    .readLines()
                    .forEach(::println)
                delete()
            }
            /*with(File("output.txt")) {
                bufferedReader()                        // interesting function.
                    .readLines()                        // I wonder which one is better to use here?
                    .forEach(::println)
                delete()
            }*/
        } catch (e: IOException) {
            println("File error")
            println(e.message)
        } catch (e: IllegalArgumentException) {
            println("Illegal Argument")
            println(e.message)
        }
    }
}

fun main(args: Array<String>) =
    GrepLauncher().launch(args)

