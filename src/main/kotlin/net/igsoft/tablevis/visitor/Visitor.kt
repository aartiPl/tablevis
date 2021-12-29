package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.*

interface Visitor<STYLE : Style> {
    fun visit(table: TableDef<STYLE>, style: STYLE, properties: TypedProperties) {
        table.rows.forEach {
            it.applyVisitor(this)
        }
    }

    fun visit(row: RowDef<STYLE>, style: STYLE, properties: TypedProperties) {
        row.cells.forEach {
            it.applyVisitor(this)
        }
    }

    fun visit(cell: CellDef<STYLE>, style: STYLE, properties: TypedProperties)
}
