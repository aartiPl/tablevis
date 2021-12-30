package net.igsoft.tablevis

import net.igsoft.tablevis.visitor.CellProperties
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

    internal fun applyVisitor(visitor: Visitor<STYLE>) = visitor.visit(properties)
}
