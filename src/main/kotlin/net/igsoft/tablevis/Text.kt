package net.igsoft.tablevis

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
        //1. line.length < textWidth
        if (line.length <= width) {
            return listOf(line)
        }

        val newLines = mutableListOf<String>()

        var rest = line
        loop@ while (rest.isNotEmpty()) {
            if (rest.length <= width) {
                newLines += rest
                rest = ""
                continue
            }

            //2. Between cell's textWidth  + 1 (since if the next char is whitespace then splitting is also okay to split)
            //   and 4/5 of line length (lastIndexOf) is whitespace
            for (i in width + 1 downTo (4 * width / 5)) {
                if (rest[i - 1].isWhitespace()) {
                    newLines += rest.substring(0, i - 1).trimEnd()
                    rest = rest.substring(i - 1).trimStart()
                    continue@loop
                }
            }

            //3. put dash in split point and continue
            newLines += rest.substring(0, width - 1) + "-"
            rest = rest.substring(width - 1)
        }

        return newLines
    }

    fun resolveTabs(text: String, tabSize: Int = 4): String = text.replace("\t", " ".repeat(tabSize))
}
