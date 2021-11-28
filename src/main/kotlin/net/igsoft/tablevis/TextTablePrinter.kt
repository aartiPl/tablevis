package net.igsoft.tablevis

import org.apache.commons.lang3.StringUtils

class TextTablePrinter : Printer<Table<out TextTableStyle>> {

    override fun print(table: Table<out TextTableStyle>): String {
        val sb = StringBuilder()

        if (table.headers.isNotEmpty()) {
            drawHorizontalLine(sb, table.style.headerHorizontalLineChar, table.width)
            table.headers.forEach {
                drawRow(sb, table.style, it)
            }
            drawHorizontalLine(sb, table.style.headerHorizontalLineChar, table.width)
        } else {
            if (table.rows.isNotEmpty()) {
                drawHorizontalLine(sb, table.style.horizontalLineChar, table.width)
            }
        }

        for (row in table.rows) {
            drawRow(sb, table.style, row)
            drawHorizontalLine(sb, table.style.horizontalLineChar, table.width)
        }

        for (row in table.footers) {
            drawRow(sb, table.style, row)
            drawHorizontalLine(sb, table.style.horizontalLineChar, table.width)
        }

        return sb.toString()
    }

    //    override fun print(table: TextTable): String {
//        val sb = StringBuilder()
//
//        if (table.header != null) {
//            sb.append(drawHorizontalLine(headerHorizontalLineChar, table.width))
//            sb.append(drawRow(table.header.get))
//            sb.append(drawHorizontalLine(headerHorizontalLineChar, table.width))
//        } else {
//            if (table.rows.isNotEmpty()) {
//                sb.append(drawHorizontalLine(horizontalLineChar, table.width))
//            }
//        }
//
//        for (row in table.rows) {
//            sb.append(drawRow(row))
//            sb.append(drawHorizontalLine(horizontalLineChar, table.width))
//        }
//
//        return sb.toString()
//    }
//
    private fun drawHorizontalLine(sb: StringBuilder, character: String, width: Int) {
        val line = character.repeat(width).substring(0, width) + "\n"
        sb.append(line)
    }

    private fun drawRow(sb: StringBuilder, style: TextTableStyle, row: Row) {
        for (line in 0..row.height) {
            sb.append(style.verticalLineChar)

            for (cell in row.cells) {
                sb.append(" ".repeat(cell.leftIndent))

                val text = if (line < cell.lines.size) cell.lines[line] else ""

                sb.append(alignHorizontally(cell.horizontal, text, cell.width))
                sb.append(" ".repeat(cell.rightIndent)).append(style.verticalLineChar)
            }

            sb.append(style.tableLineSeparator)
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
