package net.igsoft.tablevis.builder

import net.igsoft.tablevis.style.Style
import net.igsoft.tablevis.style.StyleSet
import net.igsoft.tablevis.visitor.Visitor

class TableDef<STYLE : Style, STYLE_SET : StyleSet<STYLE>>(val styleSet: STYLE_SET) :
    BaseDef<STYLE, TableProperties<STYLE>>(TableProperties(CommonStyle(styleSet.baseStyle))) {
    private var globalOperation: GlobalOperation<STYLE>? = null

    var baseStyle: STYLE
        get() = properties.commonStyle.baseStyle
        set(value) {
            properties.commonStyle = CommonStyle(value)
        }

    fun row(rowStyle: STYLE? = null, block: RowDef<STYLE>.() -> Unit = {}) {
        val calculatedStyle = if (rowStyle == null) properties.commonStyle else CommonStyle(rowStyle)
        properties.rows.add(RowDef(calculatedStyle).apply(block))
    }

    fun syncColumns() {
        globalOperation = globalOperation ?: GlobalOperation(properties.globalOperations, properties.idOperations)
        globalOperation!!.syncColumns()
    }

    fun forId(vararg id: Any): IdOperation<STYLE> = IdOperation(id.toList(), properties.idOperations)

    internal fun <TABLE_RESULT, ROW_RESULT, CELL_RESULT> applyVisitor(visitor: Visitor<STYLE, TABLE_RESULT, ROW_RESULT, CELL_RESULT>) =
        visitor.visit(properties)
}
