package net.igsoft.tablevis.builder

import net.igsoft.tablevis.style.Style
import net.igsoft.tablevis.style.StyleSet
import net.igsoft.tablevis.visitor.Visitor

class TableDef<STYLE : Style, STYLE_SET : StyleSet<STYLE>>(val styleSet: STYLE_SET) :
    BaseDef<STYLE, TableProperties<STYLE>>(TableProperties(CommonStyle(styleSet.baseStyle))) {

    fun row(rowStyle: STYLE? = null, block: RowDef<STYLE>.() -> Unit = {}) {
        val calculatedStyle = if (rowStyle == null) properties.commonStyle else CommonStyle(rowStyle)
        properties.rows.add(RowDef(calculatedStyle).apply(block))
    }

    fun forId(vararg id: Any): IdOperation<STYLE> = IdOperation(id.toList(), properties.functions)

    internal fun <TABLE_RESULT, ROW_RESULT, CELL_RESULT> applyVisitor(visitor: Visitor<STYLE, TABLE_RESULT, ROW_RESULT, CELL_RESULT>) =
        visitor.visit(properties)
}
