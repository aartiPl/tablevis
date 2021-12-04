package net.igsoft.tablevis.text

import net.igsoft.tablevis.*
import org.apache.commons.lang3.StringUtils

class TextTablePrinter : Printer<Table<out TextTableStyle>> {

    override fun print(table: Table<out TextTableStyle>): String {
        val sb = StringBuilder()

        //Headers
        if (table.headers.isNotEmpty()) {
            drawHorizontalLine(sb, table.style.headerSectionStyle, table.width, table.style.lineSeparator)
            drawInBetween(sb, table.headers, table.style.headerSectionStyle, table.style.lineSeparator, table.width)
            drawHorizontalLine(sb, table.style.headerSectionStyle, table.width, table.style.lineSeparator)
        }

        //Rows
        if (table.rows.isNotEmpty()) {
            if (table.headers.isEmpty()) {
                drawHorizontalLine(sb, table.style.rowSectionStyle, table.width, table.style.lineSeparator)
            }

            drawInBetween(sb, table.rows, table.style.rowSectionStyle, table.style.lineSeparator, table.width)

            if (table.footers.isEmpty()) {
                drawHorizontalLine(sb, table.style.rowSectionStyle, table.width, table.style.lineSeparator)
            }
        }

        //Footers
        if (table.footers.isNotEmpty()) {
            drawHorizontalLine(sb, table.style.footerSectionStyle, table.width, table.style.lineSeparator)
            drawInBetween(sb, table.footers, table.style.footerSectionStyle, table.style.lineSeparator, table.width)
            drawHorizontalLine(sb, table.style.footerSectionStyle, table.width, table.style.lineSeparator)
        }

        return sb.toString()
    }

    private fun drawInBetween(sb: StringBuilder, rows: List<Row>, style: TextSectionStyle, lineSeparator: String, width: Int) {
        for (row in rows.dropLast(1)) {
            drawRow(sb, style, row, lineSeparator)
            drawHorizontalLine(sb, style, width, lineSeparator)
        }
        drawRow(sb, style, rows.last(), lineSeparator)
    }

    private fun drawHorizontalLine(
        sb: StringBuilder, textSectionStyle: TextSectionStyle, width: Int, tableLineSeparator: String
    ) {
        val line = textSectionStyle.horizontalLine.repeat(width).substring(0, width) + tableLineSeparator
        sb.append(line)
    }

    private fun drawRow(sb: StringBuilder, textSectionStyle: TextSectionStyle, row: Row, tableLineSeparator: String) {
        for (line in 0..row.height) {
            sb.append(textSectionStyle.verticalLine)

            for (cell in row.cells) {
                sb.append(" ".repeat(cell.leftIndent))

                val text = if (line < cell.lines.size) cell.lines[line] else ""

                sb.append(alignHorizontally(cell.horizontalAlignment, text, cell.width - cell.leftIndent - cell.rightIndent))
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
