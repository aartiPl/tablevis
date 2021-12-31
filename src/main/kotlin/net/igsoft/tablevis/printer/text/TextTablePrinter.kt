package net.igsoft.tablevis.printer.text

import net.igsoft.tablevis.model.HorizontalAlignment
import net.igsoft.tablevis.model.Row
import net.igsoft.tablevis.model.Table
import net.igsoft.tablevis.printer.Printer
import net.igsoft.tablevis.style.text.IntersectionMatrix
import net.igsoft.tablevis.style.text.TextTableStyle
import net.igsoft.tablevis.style.text.TextTableStyleSet
import org.apache.commons.lang3.StringUtils

class TextTablePrinter : Printer<Table<out TextTableStyleSet<TextTableStyle>>> {

    override fun print(table: Table<out TextTableStyleSet<TextTableStyle>>): String {
        if (table.rows.isEmpty()) {
            return ""
        }

        val sb = StringBuilder()

        val maxSize = 2 * table.rows.size
        for (i in 0..maxSize) {
            if (i % 2 == 0) {
                //line
                val previousRow = if (i == 0) null else table.rows[(i - 1) / 2]
                val nextRow = if (i == maxSize) null else table.rows[(i + 1) / 2]
                drawHorizontalLine(sb, previousRow, nextRow, table.width, table.styleSet)
            } else {
                //row content
                val row = table.rows[i / 2]
                drawRow(sb, row.style as TextTableStyle, row, table.styleSet.lineSeparator)
            }
        }
        return sb.toString()
    }

    private fun drawHorizontalLine(
        sb: StringBuilder, previousRow: Row?, nextRow: Row?, width: Int, tableStyle: TextTableStyleSet<TextTableStyle>
    ) {
        require(previousRow != null || nextRow != null)

        val style = calculateStyle(previousRow, nextRow)
        val matrices = mutableMapOf<Int, IntersectionMatrix>()

        if (previousRow != null) {
            val previousRowStyle = previousRow.style as TextTableStyle
            var position = 0
            matrices.getOrPut(position) { IntersectionMatrix() }
                .setRight(style.horizontalLine[0])
                .setTop(previousRowStyle.verticalLine[0])

            for (cell in previousRow.cells.dropLast(1)) {
                position += cell.width + style.verticalLineWidth
                matrices.getOrPut(position) { IntersectionMatrix() }
                    .setLeft(style.horizontalLine[0])
                    .setRight(style.horizontalLine[0])
                    .setTop(previousRowStyle.verticalLine[0])
            }

            position += previousRow.cells.last().width + previousRowStyle.verticalLineWidth
            matrices.getOrPut(position) { IntersectionMatrix() }
                .setLeft(style.horizontalLine[0])
                .setTop(previousRowStyle.verticalLine[0])
        }

        if (nextRow != null) {
            val nextRowStyle = nextRow.style as TextTableStyle
            var position = 0
            matrices.getOrPut(position) { IntersectionMatrix() }
                .setRight(style.horizontalLine[0])
                .setBottom(nextRowStyle.verticalLine[0])

            for (cell in nextRow.cells.dropLast(1)) {
                position += cell.width + style.verticalLineWidth
                matrices.getOrPut(position) { IntersectionMatrix() }
                    .setLeft(style.horizontalLine[0])
                    .setRight(style.horizontalLine[0])
                    .setBottom(nextRowStyle.verticalLine[0])
            }

            position += nextRow.cells.last().width + nextRowStyle.verticalLineWidth
            matrices.getOrPut(position) { IntersectionMatrix() }
                .setLeft(style.horizontalLine[0])
                .setBottom(nextRowStyle.verticalLine[0])
        }

        val line = style.horizontalLine.repeat(width).substring(0, width).toCharArray()
        for (entry in matrices.entries) {
            line[entry.key] = tableStyle.resolveCrossSection(entry.value)
        }

        sb.append(String(line) + tableStyle.lineSeparator)
    }

    private fun calculateStyle(previousRow: Row?, nextRow: Row?): TextTableStyle {
        require(previousRow != null || nextRow != null)

        if (previousRow == null || nextRow == null) {
            return (previousRow ?: nextRow)!!.style as TextTableStyle
        }

        return (if (previousRow.style.layer > nextRow.style.layer) previousRow.style else nextRow.style) as TextTableStyle
    }

    private fun drawRow(sb: StringBuilder, textSectionStyle: TextTableStyle, row: Row, tableLineSeparator: String) {
        for (line in 0 until row.height) {
            sb.append(textSectionStyle.verticalLine)

            for (cell in row.cells) {
                sb.append(" ".repeat(cell.leftIndent))

                val text = if (line < cell.lines.size) cell.lines[line] else ""

                sb.append(
                    alignHorizontally(
                        cell.horizontalAlignment, text, cell.width - cell.leftIndent - cell.rightIndent
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
