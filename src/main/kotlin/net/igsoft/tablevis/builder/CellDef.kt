package net.igsoft.tablevis.builder

import net.igsoft.tablevis.style.Style
import net.igsoft.tablevis.visitor.Visitor

class CellDef<STYLE : Style>(commonStyle: CommonStyle<STYLE>) :
    BaseDef<STYLE, CellProperties<STYLE>>(CellProperties(commonStyle)) {
    fun id(vararg ids: Any) {
        properties.ids = ids.toList()
    }

    var value: Any
        get() = properties.value
        set(value) {
            properties.value = value
        }

    internal fun <TABLE_RESULT, ROW_RESULT, CELL_RESULT> applyVisitor(visitor: Visitor<STYLE, TABLE_RESULT, ROW_RESULT, CELL_RESULT>) =
        visitor.visit(properties)
}
