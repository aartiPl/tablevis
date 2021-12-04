package net.igsoft.tablevis.text

import net.igsoft.tablevis.HorizontalAlignment
import net.igsoft.tablevis.Printer
import net.igsoft.tablevis.Row
import net.igsoft.tablevis.Table
import org.apache.commons.lang3.StringUtils

class TextTablePrinter : Printer<Table<out TextTableStyle>> {

    override fun print(table: Table<out TextTableStyle>): String {
        val sb = StringBuilder()

        val maxSize = 2 * table.rows.size + 1
        for (i in 0 until maxSize) {
            if (i % 2 == 0) {
                //line
                val previousRow = if (i == 0) null else table.rows[(i - 1) / 2]
                val nextRow = if (i == maxSize - 1) null else table.rows[(i + 1) / 2]
                drawHorizontalLine(sb, previousRow, nextRow, table.width, table.style.lineSeparator)
            } else {
                //row content
                val row = table.rows[i / 2]
                drawRow(sb, row.style as TextSectionStyle, row, table.style.lineSeparator)
            }
        }
        return sb.toString()
    }

    private fun drawHorizontalLine(
        sb: StringBuilder, previousRow: Row?, nextRow: Row?, width: Int, tableLineSeparator: String
    ) {
        require(previousRow != null || nextRow != null)

        val style = calculateStyle(previousRow, nextRow)

        val line = style.horizontalLine.repeat(width).substring(0, width) + tableLineSeparator
        sb.append(line)
    }

    private fun calculateStyle(previousRow: Row?, nextRow: Row?): TextSectionStyle {
        require(previousRow != null || nextRow != null)

        if (previousRow == null || nextRow == null) {
            return (previousRow ?: nextRow)!!.style as TextSectionStyle
        }

        return (if (previousRow.style.layer > nextRow.style.layer) previousRow.style else nextRow.style) as TextSectionStyle
    }

    private fun drawRow(sb: StringBuilder, textSectionStyle: TextSectionStyle, row: Row, tableLineSeparator: String) {
        for (line in 0..row.height) {
            sb.append(textSectionStyle.verticalLine)

            for (cell in row.cells) {
                sb.append(" ".repeat(cell.leftIndent))

                val text = if (line < cell.lines.size) cell.lines[line] else ""

                sb.append(
                    alignHorizontally(
                        cell.horizontalAlignment,
                        text,
                        cell.width - cell.leftIndent - cell.rightIndent
                    )
                )
                sb.append(" ".repeat(cell.rightIndent)).append(textSectionStyle.verticalLine)
            }

            sb.append(tableLineSeparator)
        }
    }

    private fun alignHorizontally(horizontalAlignment: HorizontalAlignment, text: String, width: Int): String {
        return when (horizontalAlignment) {
            HorizontalAlignment.Center -> StringUtils.center(text, width)
            HorizontalAlignment.Right -> StringUtils.leftPad(text, width)
            else -> StringUtils.rightPad(text, width)
        }
    }
}