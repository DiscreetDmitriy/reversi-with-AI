@file:Suppress("SpellCheckingInspection")

package grep

import org.kohsuke.args4j.Argument
import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import java.io.File

// вариант 3
class GrepLauncher {
    @Option(name = "-v", usage = "Invert filter condition flag")
    val v: Boolean = false

    @Option(name = "-i", usage = "Ignore word case flag")
    val i: Boolean = false

    @Option(name = "-r", usage = "Regex pattern flag")
    val r: Boolean = false

    @Argument(required = true, metaVar = "word", usage = "word you need to find")
    val word: String = ""

    @Argument(required = true, metaVar = "fileName", index = 1, usage = "File name")
    val fileName: String = ""

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
            Grep(fileName, word).find(v, i, r)
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
        } catch (e: java.io.IOException) {
            println("File error")
            println(e.message)
        } catch (e: java.lang.IllegalArgumentException) {
            println("Illegal Argument")
            println(e.message)
        }
    }
}

fun main(args: Array<String>) =
    GrepLauncher().launch(args)

