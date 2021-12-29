package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.*

interface Visitor<STYLE : Style> {
    fun visit(table: TableDef<STYLE>, properties: TableProperties) {
        table.rows.forEach {
            it.applyVisitor(this)
        }
    }

    fun visit(row: RowDef<STYLE>, properties: RowProperties) {
        row.cells.forEach {
            it.applyVisitor(this)
        }
    }

    fun visit(cell: CellDef<STYLE>, properties: CellProperties)
}
