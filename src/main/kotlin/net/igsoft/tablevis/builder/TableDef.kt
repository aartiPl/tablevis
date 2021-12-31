package net.igsoft.tablevis.builder

import net.igsoft.tablevis.style.Style
import net.igsoft.tablevis.visitor.Visitor

class TableDef<STYLE : Style>(commonStyle: CommonStyle<STYLE>) :
    BaseDef<STYLE, TableProperties<STYLE>>(TableProperties(commonStyle)) {
    fun row(rowStyle: STYLE? = null, block: RowDef<STYLE>.() -> Unit = {}) {
        val commonStyle = if (rowStyle == null) properties.commonStyle else CommonStyle(rowStyle)
        properties.rows.add(RowDef(commonStyle).apply(block))
    }

    fun forId(vararg id: Any): IdOperation<STYLE> = IdOperation(id.toList(), properties.functions)

    internal fun <TABLE_RESULT, ROW_RESULT, CELL_RESULT> applyVisitor(visitor: Visitor<STYLE, TABLE_RESULT, ROW_RESULT, CELL_RESULT>) =
        visitor.visit(properties)
}
