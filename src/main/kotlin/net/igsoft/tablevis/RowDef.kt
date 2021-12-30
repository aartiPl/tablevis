package net.igsoft.tablevis

import net.igsoft.tablevis.visitor.RowProperties
import net.igsoft.tablevis.visitor.Visitor

class RowDef<STYLE : Style>(style: STYLE) : BaseDef<STYLE, RowProperties<STYLE>>(RowProperties(style), style) {

    fun cell(cellStyle: STYLE = style, block: CellDef<STYLE>.() -> Unit = {}) {
        properties.cells.add(CellDef(cellStyle).apply(block))
    }

    internal fun build(): Row {
        return Row(properties.width!!, properties.height!!, style, properties.cells.map { it.build() })
    }

    internal fun applyVisitor(visitor: Visitor<STYLE>) = visitor.visit(properties)
}
