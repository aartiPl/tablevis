package net.igsoft.tablevis.util

import java.util.regex.Pattern
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

    fun justifyLine(line: String, width: Int, threshold: Int = width * 4 / 5): String {
        if (line.length < threshold) {
            return line
        }

        var wordsAndDelimiters = mutableListOf<String>()
        wordsAndDelimiters.addAll(splitPreservingWhitespaces(line))

        var justified = ""

        //Skip whitespaces at beginning (whitespaces are preserved)
        if (Character.isWhitespace(wordsAndDelimiters.first().codePointAt(0))) {
            justified = wordsAndDelimiters.first()
            wordsAndDelimiters = wordsAndDelimiters.drop(1).toMutableList()
        }

        //Remove whitespaces at end
        if (Character.isWhitespace(wordsAndDelimiters.last().codePointAt(0))) {
            wordsAndDelimiters = wordsAndDelimiters.dropLast(1).toMutableList()
        }

        if (wordsAndDelimiters.isEmpty()) {
            return justified
        }

        //Calculate missing spaces
        val missing = width - line.length
        val gaps = wordsAndDelimiters.size / 2

        if (gaps == 0) {
            //Add gaps at the beginning
            justified += " ".repeat(missing) + wordsAndDelimiters.first()
            return justified
        }

        val spacesPerGap = missing / gaps
        var additionalSpaces = missing % gaps

        if (spacesPerGap > 0) {
            //Distribute spaces evenly...
            for (i in 1 until wordsAndDelimiters.size) {
                if (i % 2 != 0) {
                    wordsAndDelimiters[i] += " ".repeat(spacesPerGap)
                }
            }
        }

        if (additionalSpaces > 0) {
            //Distribute remaining spaces randomly
            for (i in 1 until wordsAndDelimiters.size) {
                if (i % 2 != 0) {
                    var additionalSpace = 0

                    if (additionalSpaces > 0) {
                        additionalSpace = 1
                        additionalSpaces -= 1
                    }

                    val spaces = additionalSpace

                    wordsAndDelimiters[i] += " ".repeat(spaces)
                }
            }
        }

        return justified + wordsAndDelimiters.joinToString("")
    }

    private val whitespacesOrWord = Pattern.compile("\\s+|\\S+")

    fun splitPreservingWhitespaces(text: String): List<String> {
        val result = mutableListOf<String>()

        var tail = text

        while (tail.isNotEmpty()) {
            val matcher = whitespacesOrWord.matcher(tail)

            //NOTE: it will always match - non-empty string has either
            //whitespace or not whitespace characters at the beginning
            matcher.lookingAt()

            val matched = matcher.group()
            result += matched
            tail = tail.substring(matched.length)
        }

        return result
    }
}
