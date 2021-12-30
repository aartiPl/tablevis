package net.igsoft.tablevis

import net.igsoft.tablevis.visitor.TableProperties
import net.igsoft.tablevis.visitor.Visitor

class TableDef<STYLE : Style>(style: STYLE) : DefBase<STYLE, TableProperties<STYLE>>(TableProperties(style), style){
    val functions = mutableMapOf<Any, MutableSet<(Set<CellDef<STYLE>>) -> Unit>>()

    var minimalTextWidth = style.minimalTextWidth

    var width: Int? = null
    var height: Int? = null

    fun row(rowStyle: STYLE = style, block: RowDef<STYLE>.() -> Unit = {}) {
        properties.rows.add(RowDef(rowStyle).apply(block))
    }

    fun forId(vararg id: Any): IdOperation<STYLE> = IdOperation(id.toList(), functions)

    internal fun applyVisitor(visitor: Visitor<STYLE>) = visitor.visit(properties)
}
