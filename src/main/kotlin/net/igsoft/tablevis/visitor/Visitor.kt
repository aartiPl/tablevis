package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.*

interface Visitor<STYLE : Style> {
    fun visit(table: TableDef<STYLE>)
    fun visit(row: RowDef<STYLE>)
    fun visit(cell: CellDef<STYLE>)
}
