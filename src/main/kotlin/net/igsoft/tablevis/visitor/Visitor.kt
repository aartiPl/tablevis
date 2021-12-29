package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.*

interface Visitor<STYLE : Style> {
    fun visit(table: TableBuilder<STYLE>)
    fun visit(row: RowBuilder<STYLE>)
    fun visit(cell: CellBuilder<STYLE>)
}
