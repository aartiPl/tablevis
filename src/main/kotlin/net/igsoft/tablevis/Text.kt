package net.igsoft.tablevis

import kotlin.math.max

object Text {
    fun splitTextually(text: String, width: Int): List<String> {
        val newLines = mutableListOf<String>()

        for (line in text.lines()) {
            newLines += splitLineTextually(line, width)
        }

        return newLines
    }

    /**
     * Split text textually by width in such a way that split points are on whitespaces.
     * If splitting on whitespace is not possible "-" is added on split point.
     */
    fun splitLineTextually(line: String, width: Int): List<String> {
        //1. line.length < textWidth => take the whole line
        if (line.length <= width) {
            return listOf(line)
        }

        val newLines = mutableListOf<String>()
        //NOTE: Algorithm
        // width + 1 may contain space; that's correct splitting point - line will have size of width
        // max((4 * width / 5), width - 7) - more than 7 chars in lookup range is too much - text doesn't look good
        val lookupRange = width + 1 downTo max((4 * width / 5), width - 7)

        var rest = line
        loop@ while (rest.isNotEmpty()) {
            if (rest.length <= width) {
                newLines += rest
                rest = ""
                continue
            }

            //2. There is a whitespace in lookupRange => split by whitespace
            for (i in lookupRange) {
                if (rest[i - 1].isWhitespace()) {
                    newLines += rest.substring(0, i - 1).trimEnd()
                    rest = rest.substring(i - 1).trimStart()
                    continue@loop
                }
            }

            //3. There are only chars in lookupRange => split the word with dash and continue
            newLines += rest.substring(0, width - 1) + "-"
            rest = rest.substring(width - 1)
        }

        return newLines
    }

    fun resolveTabs(text: String, tabSize: Int = 4): String = text.replace("\t", " ".repeat(tabSize))
}
