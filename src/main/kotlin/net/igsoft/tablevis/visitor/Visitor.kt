package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.*

interface Visitor<STYLE : Style> {
    fun visit(tableProperties: TableProperties<STYLE>): TableProperties<STYLE> {
        tableProperties.rows.forEach {
            it.applyVisitor(this)
        }

        return tableProperties
    }

    fun visit(rowProperties: RowProperties<STYLE>): RowProperties<STYLE> {
        rowProperties.cells.forEach {
            it.applyVisitor(this)
        }

        return rowProperties
    }

    fun visit(cellProperties: CellProperties<STYLE>): CellProperties<STYLE>
}
