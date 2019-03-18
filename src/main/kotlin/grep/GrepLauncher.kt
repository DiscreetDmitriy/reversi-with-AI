@file:Suppress( "SpellCheckingInspection")

package grep

import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Argument
import org.kohsuke.args4j.Option

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
        } catch (e: Exception) {
            println(e.message)
        }
    }
}

fun main(args: Array<String>) =
    GrepLauncher().launch(args)