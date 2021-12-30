package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.builder.CellProperties
import net.igsoft.tablevis.builder.RowProperties
import net.igsoft.tablevis.builder.TableProperties
import net.igsoft.tablevis.style.Style

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
