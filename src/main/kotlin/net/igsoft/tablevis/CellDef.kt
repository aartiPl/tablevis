package net.igsoft.tablevis

import net.igsoft.tablevis.visitor.CellProperties
import net.igsoft.tablevis.visitor.Visitor
import kotlin.math.max

class CellDef<STYLE : Style>(style: STYLE) : DefBase<STYLE, CellProperties<STYLE>>(CellProperties(style), style) {
    var text: String = ""

    var width: Int? = null
    var height: Int? = null

    var minimalTextWidth = style.minimalTextWidth

    fun id(vararg ids: Any) {
        this.ids = ids.toList()
    }

    //------------------------------------------------------------------------------------------------------------------
    //Implementation code

    var ids: List<Any> = emptyList()
    private var lines: List<String> = emptyList()
    internal var naturalTextWidth: Int = 0
    internal var naturalWidth = 0
    internal var minimalWidth = 0
    internal var textWidth = 0

    internal fun resolveTexts() {
        if (text.isEmpty()) {
            lines = listOf()
            naturalTextWidth = 0
            minimalTextWidth = 0
        } else {
            lines = Text.resolveTabs(text).lines()
            naturalTextWidth = lines.maxOf { it.length }
        }

        naturalWidth = leftMargin + naturalTextWidth + rightMargin
    }

    internal fun resolveWidth(imposedWidth: Boolean) {
        minimalWidth = width ?: (leftMargin + minimalTextWidth + rightMargin)

        if (!imposedWidth) {
            width = width ?: naturalWidth
        }
    }

    internal fun adjustTexts() {
        textWidth = (width ?: minimalWidth) - leftMargin - rightMargin

        //Choose strategy of splitting text
        lines = if (textWidth < 5) {
            //Cell is too small to do anything fancy...
            lines.flatMap { it.chunked(textWidth) }
        } else {
            //Split textually
            lines.flatMap { Text.splitLineTextually(it, textWidth) }
        }

        height = height ?: max(lines.size, 1)

//        if (cell.horizontalAlignment.contains(HorizontalAlignment.Justified)) {
//            val justificationThreshold = cell.cellTextWidth.get * 4 / 5)
//            cell.lines =
//                cell.lines map (line => Text.justifyLine(line, cell.cellTextWidth.get, justificationThreshold))
//        }
    }

    internal fun build(): Cell {

        return Cell(
            width!!,
            height!!,
            leftMargin,
            topMargin,
            rightMargin,
            bottomMargin,
            properties.horizontalAlignment,
            properties.verticalAlignment,
            lines
        )
    }

    internal fun applyVisitor(visitor: Visitor<STYLE>) = visitor.visit(properties)
}
