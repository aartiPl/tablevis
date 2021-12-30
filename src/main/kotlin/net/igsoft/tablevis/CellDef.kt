package net.igsoft.tablevis

import net.igsoft.tablevis.visitor.CellProperties
import net.igsoft.tablevis.visitor.Visitor
import kotlin.math.max

class CellDef<STYLE : Style>(style: STYLE) : DefBase<STYLE, CellProperties<STYLE>>(CellProperties(style), style) {
    fun id(vararg ids: Any) {
        this.ids = ids.toList()
    }

    var text: String
        get() = properties.text
        set(value) {
            properties.text = value
        }

    //------------------------------------------------------------------------------------------------------------------
    //Implementation code

    var ids: List<Any> = emptyList()
    internal var minimalWidth = 0
    internal var textWidth = 0

    internal fun resolveWidth(imposedWidth: Boolean) {
        minimalWidth = properties.width ?: (leftMargin + properties.minimalTextWidth + rightMargin)

        if (!imposedWidth) {
            properties.width = properties.width ?: properties.naturalWidth
        }
    }

    internal fun adjustTexts() {
        textWidth = (properties.width ?: minimalWidth) - leftMargin - rightMargin

        //Choose strategy of splitting text
        properties.lines = if (textWidth < 5) {
            //Cell is too small to do anything fancy...
            properties.lines.flatMap { it.chunked(textWidth) }
        } else {
            //Split textually
            properties.lines.flatMap { Text.splitLineTextually(it, textWidth) }
        }

        properties.height = properties.height ?: max(properties.lines.size, 1)

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
