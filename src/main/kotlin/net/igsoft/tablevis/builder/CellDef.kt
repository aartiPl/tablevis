package net.igsoft.tablevis.builder

import net.igsoft.tablevis.model.Cell
import net.igsoft.tablevis.style.Style
import net.igsoft.tablevis.visitor.Visitor

class CellDef<STYLE : Style>(style: STYLE) : BaseDef<STYLE, CellProperties<STYLE>>(CellProperties(style), style) {
    fun id(vararg ids: Any) {
        properties.ids = ids.toList()
    }

    var text: String
        get() = properties.text
        set(value) {
            properties.text = value
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

    internal fun <TABLE_RESULT, ROW_RESULT, CELL_RESULT> applyVisitor(visitor: Visitor<STYLE, TABLE_RESULT, ROW_RESULT, CELL_RESULT>) =
        visitor.visit(properties)
}
