package net.igsoft.tablevis.builder

import net.igsoft.tablevis.style.Style
import net.igsoft.tablevis.visitor.Visitor

class TableDef<STYLE : Style>(style: STYLE) : BaseDef<STYLE, TableProperties<STYLE>>(TableProperties(style), style) {
    fun row(rowStyle: STYLE = style, block: RowDef<STYLE>.() -> Unit = {}) {
        properties.rows.add(RowDef(rowStyle).apply(block))
    }

    fun forId(vararg id: Any): IdOperation<STYLE> = IdOperation(id.toList(), properties.functions)

    internal fun <TABLE_RESULT, ROW_RESULT, CELL_RESULT> applyVisitor(visitor: Visitor<STYLE, TABLE_RESULT, ROW_RESULT, CELL_RESULT>) =
        visitor.visit(properties)
}
