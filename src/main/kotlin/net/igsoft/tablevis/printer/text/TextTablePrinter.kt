package net.igsoft.tablevis.printer.text

import net.igsoft.tablevis.model.*
import net.igsoft.tablevis.printer.Printer
import net.igsoft.tablevis.style.StyleSet
import net.igsoft.tablevis.style.text.TextTableBorder
import net.igsoft.tablevis.style.text.TextTableStyle
import net.igsoft.tablevis.style.text.TextTableStyleSet
import org.apache.commons.lang3.StringUtils

class TextTablePrinter : Printer<Table<out TextTableStyleSet<TextTableStyle>>> {

    override fun print(table: Table<out TextTableStyleSet<TextTableStyle>>): String {
        if (table.horizontalElements.isEmpty()) {
            return ""
        }

        val sb = StringBuilder()
        for (horizontalElement in table.horizontalElements) {
            @Suppress("UNCHECKED_CAST") when (horizontalElement) {
                is Line -> drawLine(
                    sb,
                    horizontalElement,
                    table.styleSet.lineSeparator,
                    table.styleSet::resolveIntersection
                )
                is Row<*> -> drawRow(sb, horizontalElement as Row<TextTableStyle>, table.styleSet.lineSeparator)
            }
        }

        return sb.toString()
    }

    private fun drawLine(
        sb: StringBuilder,
        line: Line,
        lineSeparator: String,
        resolveIntersection: (String) -> Char
    ) {
        if (line.maxSize == 0) {
            return
        }

        for (element in line.elements) {
            when (element) {
                is Section -> {
                    val border = element.border as TextTableBorder
                    sb.append(border.line.repeat(element.size).substring(0, element.size))
                }

                is Intersection -> {
                    val chars = element.matrix.joinToString(separator = "") { border ->
                        if (border == StyleSet.empty || border == StyleSet.noBorder) {
                            " "
                        } else  {
                            (border as TextTableBorder).line
                        }
                    }
                    sb.append(resolveIntersection(chars))
                }
            }
        }

        sb.append(lineSeparator)
    }

//    private fun drawHorizontalLine(
//        sb: StringBuilder, previousRow: Row?, nextRow: Row?, width: Int, tableStyle: TextTableStyleSet<TextTableStyle>
//    ) {
//        require(previousRow != null || nextRow != null)
//
//        val style = calculateStyle(previousRow, nextRow)
//        val matrices = mutableMapOf<Int, IntersectionMatrix>()
//
//        if (previousRow != null) {
//            val previousRowStyle = previousRow.style as TextTableStyle
//            var position = 0
//            matrices.getOrPut(position) { IntersectionMatrix() }
//                .setRight(style.horizontalLine[0])
//                .setTop(previousRowStyle.verticalLine[0])
//
//            for (cell in previousRow.cells.dropLast(1)) {
//                position += cell.width + style.verticalLineWidth
//                matrices.getOrPut(position) { IntersectionMatrix() }
//                    .setLeft(style.horizontalLine[0])
//                    .setRight(style.horizontalLine[0])
//                    .setTop(previousRowStyle.verticalLine[0])
//            }
//
//            position += previousRow.cells.last().width + previousRowStyle.verticalLineWidth
//            matrices.getOrPut(position) { IntersectionMatrix() }
//                .setLeft(style.horizontalLine[0])
//                .setTop(previousRowStyle.verticalLine[0])
//        }
//
//        if (nextRow != null) {
//            val nextRowStyle = nextRow.style as TextTableStyle
//            var position = 0
//            matrices.getOrPut(position) { IntersectionMatrix() }
//                .setRight(style.horizontalLine[0])
//                .setBottom(nextRowStyle.verticalLine[0])
//
//            for (cell in nextRow.cells.dropLast(1)) {
//                position += cell.width + style.verticalLineWidth
//                matrices.getOrPut(position) { IntersectionMatrix() }
//                    .setLeft(style.horizontalLine[0])
//                    .setRight(style.horizontalLine[0])
//                    .setBottom(nextRowStyle.verticalLine[0])
//            }
//
//            position += nextRow.cells.last().width + nextRowStyle.verticalLineWidth
//            matrices.getOrPut(position) { IntersectionMatrix() }
//                .setLeft(style.horizontalLine[0])
//                .setBottom(nextRowStyle.verticalLine[0])
//        }
//
//        val line = style.horizontalLine.repeat(width).substring(0, width).toCharArray()
//        for (entry in matrices.entries) {
//            line[entry.key] = tableStyle.resolveCrossSection(entry.value)
//        }
//
//        sb.append(String(line) + tableStyle.lineSeparator)
//    }

    //    private fun calculateStyle(previousRow: Row?, nextRow: Row?): TextTableStyle {
//        require(previousRow != null || nextRow != null)
//
//        if (previousRow == null || nextRow == null) {
//            return (previousRow ?: nextRow)!!.style as TextTableStyle
//        }
//
//        return (if (previousRow.style.layer > nextRow.style.layer) previousRow.style else nextRow.style) as TextTableStyle
//    }
//
    private fun drawRow(sb: StringBuilder, row: Row<TextTableStyle>, tableLineSeparator: String) {
        for (line in 0 until row.height) {

            for ((counter, verticalElement) in row.verticalElements.withIndex()) {
                when (verticalElement) {
                    is Section -> {
                        sb.append((verticalElement.border as TextTableBorder).line)
                    }
                    is Cell<*> -> {
                        sb.append(" ".repeat(verticalElement.style.leftMargin))
                        val text = if (line < verticalElement.lines.size) verticalElement.lines[line] else ""
                        sb.append(
                            alignHorizontally(
                                verticalElement.style.horizontalAlignment,
                                text,
                                verticalElement.width - verticalElement.style.leftMargin - verticalElement.style.rightMargin
                            )
                        )
                        sb.append(" ".repeat(verticalElement.style.rightMargin))
                    }
                }
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
