package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.builder.CellProperties
import net.igsoft.tablevis.builder.RowProperties
import net.igsoft.tablevis.builder.TableProperties
import net.igsoft.tablevis.style.Style

interface Visitor<STYLE : Style, TABLE_RESULT, ROW_RESULT, CELL_RESULT> {
    fun visit(tableProperties: TableProperties<STYLE>): TABLE_RESULT

    fun visit(rowProperties: RowProperties<STYLE>): ROW_RESULT

    fun visit(cellProperties: CellProperties<STYLE>): CELL_RESULT
}
