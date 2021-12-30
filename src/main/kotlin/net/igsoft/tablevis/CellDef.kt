package net.igsoft.tablevis

import net.igsoft.tablevis.visitor.CellProperties
import net.igsoft.tablevis.visitor.Visitor
import kotlin.math.max

class CellDef<STYLE : Style>(style: STYLE) : BaseDef<STYLE, CellProperties<STYLE>>(CellProperties(style), style) {
    fun id(vararg ids: Any) {
        properties.ids = ids.toList()
    }

    var text: String
        get() = properties.text
        set(value) {
            properties.text = value
        }

    internal fun adjustTexts() {
        properties.textWidth = (properties.width ?: properties.minimalWidth) - leftMargin - rightMargin

        //Choose strategy of splitting text
        properties.lines = if (properties.textWidth < 5) {
            //Cell is too small to do anything fancy...
            properties.lines.flatMap { it.chunked(properties.textWidth) }
        } else {
            //Split textually
            properties.lines.flatMap { Text.splitLineTextually(it, properties.textWidth) }
        }

        properties.height = properties.height ?: max(properties.lines.size, 1)

        //TODO: justify text in Printer
//        if (cell.horizontalAlignment.contains(HorizontalAlignment.Justified)) {
//            val justificationThreshold = cell.cellTextWidth.get * 4 / 5)
//            cell.lines =
//                cell.lines map (line => Text.justifyLine(line, cell.cellTextWidth.get, justificationThreshold))
//        }
    }



    internal fun build(): Cell {

        return Cell(
            properties.width!!,
            properties.height!!,
            leftMargin,
            topMargin,
            rightMargin,
            bottomMargin,
            properties.horizontalAlignment,
            properties.verticalAlignment,
            properties.lines
        )
    }

    internal fun applyVisitor(visitor: Visitor<STYLE>) = visitor.visit(properties)
}
