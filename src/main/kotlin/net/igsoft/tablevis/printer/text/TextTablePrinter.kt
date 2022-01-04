package net.igsoft.tablevis.printer.text

import net.igsoft.tablevis.model.*
import net.igsoft.tablevis.printer.Printer
import net.igsoft.tablevis.style.Border
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
                    sb, horizontalElement, table.styleSet.lineSeparator, table.styleSet::resolveIntersection
                )
                is Row<*> -> drawRow(sb, horizontalElement as Row<TextTableStyle>, table.styleSet.lineSeparator)
            }
        }

        return sb.toString()
    }

    private fun drawLine(
        sb: StringBuilder, line: Line, lineSeparator: String, resolveIntersection: (String) -> Char
    ) {
        if (line.maxSize == 0) {
            return
        }

        for (element in line.elements) {
            when (element) {
                is Section -> {
                    val line = borderToString(element.border)
                    sb.append(line.repeat(element.size).substring(0, element.size))
                }

                is Intersection -> {
                    val chars = element.matrix.joinToString(separator = "") { borderToString(it) }
                    sb.append(resolveIntersection(chars))
                }
            }
        }

        sb.append(lineSeparator)
    }

    private fun drawRow(sb: StringBuilder, row: Row<TextTableStyle>, tableLineSeparator: String) {
        for (line in 0 until row.height) {

            for (verticalElement in row.verticalElements) {
                when (verticalElement) {
                    is Section -> {
                        if (verticalElement.border.size > 0) {
                            sb.append(borderToString(verticalElement.border))
                        }
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

    private fun borderToString(border: Border): String {
        return if (border == Border.empty || border == Border.noBorder) {
            " "
        } else {
            (border as TextTableBorder).line
        }
    }
}
