package net.igsoft.tablevis

import net.igsoft.tablevis.visitor.TableProperties
import net.igsoft.tablevis.visitor.Visitor

class TableDef<STYLE : Style>(style: STYLE) : DefBase<STYLE, TableProperties<STYLE>>(TableProperties(style), style){
    var minimalTextWidth = style.minimalTextWidth

    fun row(rowStyle: STYLE = style, block: RowDef<STYLE>.() -> Unit = {}) {
        properties.rows.add(RowDef(rowStyle).apply(block))
    }

    fun forId(vararg id: Any): IdOperation<STYLE> = IdOperation(id.toList(), properties.functions)

    internal fun applyVisitor(visitor: Visitor<STYLE>) = visitor.visit(properties)
}
