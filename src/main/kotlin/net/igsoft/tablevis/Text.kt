package net.igsoft.tablevis

import java.util.regex.Pattern

object Text {
  /*
  private val TAB_SIZE = 4

  /**
    * This functions takes input text ant gets 'width' number of characters from it.
    * If the initial text must be split in the middle of some word, then it tries to shorten
    * text by max. tolerance value, so that it splits only on whitespace. If it is not possible
    * then it just adds '-' (dash) and split text in the middle of the word.
    *
    * @param text      - initial text
    * @param width     - maximal width of the line
    * @param tolerance - maximal tolerance - the number of letters by which resulting string can be shorten
    * @return (head : String, tail : String) - tuple (grabbed line, rest of string or null if there is nothing more to grab)
    */
  fun grabLine(text: String, width: Int, tolerance: Int): (String, String) = {
    var offset = 0
    var chars = 0
    var finishLine = false
    var result = ""
    var lastViableOffset = -1

    var codepoint: Int = -1
    while (offset < text.length && chars <= width && !finishLine) {
      codepoint = text.codePointAt(offset)

      if (codepoint == '\r' && text.length > offset + 1 && text.charAt(offset + 1) == '\n') {
        // \r\n
        offset += 1 //Second character is handled normally
        finishLine = true
      } else if (codepoint == '\r' || codepoint == '\n') {
        // endline characters
        finishLine = true
      } else if (codepoint == '\t') {
        //tabs
        if (chars + TAB_SIZE < width) {
          result += " " * TAB_SIZE
          chars += TAB_SIZE
        } else {
          finishLine = true
        }
      }

      if (!finishLine) {
        if (codepoint > 0xFFFF) {
          // Unicode character - leave it as it is
          result += text.substring(offset, offset + Character.charCount(codepoint))
          chars += 1
        } else {
          result += text.charAt(offset)
          chars += 1
        }

        if (chars >= (width - tolerance)) {
          //possible split
          if (Character.isWhitespace(codepoint)) {
            lastViableOffset = offset
          }

          if (chars == width - 1 && !isWhitespaceAhead(3, offset, text) && lastViableOffset == -1) {
            result += '-'
            finishLine = true
          }

          if (chars == width && isWhitespaceAhead(1, offset + 1, text)) {
            finishLine = true
          }
        }
      }

      offset += Character.charCount(codepoint)

      if (chars == width && isEndlineAhead(offset, text)) {
        //Split not because of endline character, but because of size
        //but we know that next character is endline
        //Because of that we should remove endline character from string
        //TODO:
      }
    }

    if (!finishLine && lastViableOffset > 0) {
      offset = lastViableOffset
      result = result.substring(0, lastViableOffset)
    }

    var remainingText = if (offset >= text      .length && codepoint != '\r' && codepoint != '\n') null else text.substring(offset)

    //Remove first new line character from beginning of the remaining text
    if (remainingText != null) {
      if (remainingText.startsWith("\r\n")) {
        remainingText = remainingText.substring(2)
      } else if (remainingText.startsWith("\r") || remainingText.startsWith("\n")) {
        remainingText = remainingText.substring(1)
      }

      // Don't start new line from whitespace
      while (remainingText.nonEmpty && Character.isWhitespace(remainingText(0)) && remainingText(0) != '\r' && remainingText(0) != '\n') {
        remainingText = remainingText.substring(1)
      }
    }

    //Remove whitespaces from end of line
    (result.replaceAll("\\s+$", ""), remainingText)
  }

  /**
    * This functions takes input text ant splits it using grabLine(), with all its conditions.
    *
    * @param text      - initial text
    * @param width     - maximal width of the line
    * @param tolerance - maximal tolerance - the number of letters by which resulting string can be shorten
    * @return Seq<String> - list of lines
    */
  fun splitByWidth(text: String, width: Int, tolerance: Int = 8): List<String> {
    val lines = mutable.Buffer<String>()
    var currentText = text

    do {
      val (line, rest) = grabLine(currentText, width, tolerance)
      lines += line
      currentText = rest
    } while (currentText != null)

    return lines
  }

  private fun isWhitespaceAhead(maxChecks: Int, offset: Int, text: String): Boolean {
    var pos: Int = 0

    while (pos < maxChecks && offset + pos < text.size) {
      val codepoint = text.codePointAt(offset + pos)

      if (Character.isWhitespace(codepoint)) {
        return true
      }

      pos += Character.charCount(codepoint)
    }

    if (offset + pos == text.size) {
      return true
    }

    return false
  }

  private fun isEndlineAhead(offset: Int, text: String): Boolean {
    if (offset >= text.size) {
        return true
    }

    val codepoint = text.codePointAt(offset)

    var isEndline = false //Check: \r\n
    if (codepoint == '\r' && text.size > offset + 1 && text.charAt(offset + 1) == '\n') {
      isEndline = true //Check: \r or \n
    } else if (codepoint == '\r' || codepoint == '\n') {
      isEndline = true
    }

    return isEndline
  }

  fun justifyLine(line: String, width: Int, threshold: Option<Int> = None): String  {
    if (line.isEmpty || (threshold.isDefined && line.size < threshold.get)) {
      return line
    }

    var wordsAndDelimiters = splitPreservingWhitespaces(line)
    var justified = ""

    //Skip whitespaces at beginning (whitespaces are preserved)
    if (Character.isWhitespace(wordsAndDelimiters.head.codePointAt(0))) {
      justified = wordsAndDelimiters.head
      wordsAndDelimiters = wordsAndDelimiters.tail
    }

    //Remove whitespaces at end
    if (Character.isWhitespace(wordsAndDelimiters.last.codePointAt(0))) {
      wordsAndDelimiters = wordsAndDelimiters.dropRight(1)
    }

    if (wordsAndDelimiters.isEmpty) {
      return justified
    }

    //Calculate missing spaces
    val missing = width - line.size
    val gaps = wordsAndDelimiters.size / 2

    if (gaps == 0) {
      //Add gaps at the beginning
      justified += " " * missing + wordsAndDelimiters.head
      return justified
    }

    val spacesPerGap = missing / gaps
    var additionalSpaces = missing % gaps //val everyNthGap = gaps / Math.max(additionalSpaces, 1)
    if (spacesPerGap > 0 || additionalSpaces > 0) {
      //Distribute gaps evenly...
      for (i <- 1 until wordsAndDelimiters.size if i % 2 != 0) {
        var additionalSpace = 0

        //println(s"everyNthGap: $everyNthGap; additionalSpaces: $additionalSpaces; gaps: $gaps")
        if (additionalSpaces > 0 /*&& (i / 2) % everyNthGap == 0*/ ) {
          additionalSpace = 1
          additionalSpaces -= 1
        }

        val spaces = spacesPerGap + additionalSpace

        wordsAndDelimiters(i) += " " * spaces
      }
    }

    return justified + wordsAndDelimiters.mkString
  }

  private val WhitespacesOrWord = Pattern.compile("\\s+|\\S+")

  fun splitPreservingWhitespaces(text: String): Array<String>  {
    val result: ArrayBuffer<String> = ArrayBuffer<String>()

    var tail = text

    while (!tail.isEmpty) {
      val matcher = WhitespacesOrWord.matcher(tail)

      //NOTE: it will always match - non empty string has either
      //whitespace or not whitespace characters at the beginning
      matcher.lookingAt()

      var matched = matcher.group
      result += matched
      tail = tail.substring(matched.size)
    }

    return result.toArray
  }

   */
}
