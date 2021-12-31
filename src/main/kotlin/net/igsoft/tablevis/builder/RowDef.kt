package net.igsoft.tablevis.builder

import net.igsoft.tablevis.style.Style
import net.igsoft.tablevis.visitor.Visitor

class RowDef<STYLE : Style>(commonStyle: CommonStyle<STYLE>) :
    BaseDef<STYLE, RowProperties<STYLE>>(RowProperties(commonStyle)) {

    fun cell(cellStyle: STYLE? = null, block: CellDef<STYLE>.() -> Unit = {}) {
        val commonStyle = if (cellStyle == null) properties.commonStyle else CommonStyle(cellStyle)
        properties.cells.add(CellDef(commonStyle).apply(block))
    }

    internal fun <TABLE_RESULT, ROW_RESULT, CELL_RESULT> applyVisitor(visitor: Visitor<STYLE, TABLE_RESULT, ROW_RESULT, CELL_RESULT>) =
        visitor.visit(properties)
}
