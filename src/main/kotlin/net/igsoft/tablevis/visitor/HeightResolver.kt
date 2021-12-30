package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.builder.CellProperties
import net.igsoft.tablevis.builder.RowProperties
import net.igsoft.tablevis.builder.TableProperties
import net.igsoft.tablevis.style.Style

class HeightResolver<STYLE : Style> :
    Visitor<STYLE, TableProperties<STYLE>, RowProperties<STYLE>, CellProperties<STYLE>> {
    private var imposedWidth: Boolean = false

    override fun visit(tableProperties: TableProperties<STYLE>): TableProperties<STYLE> {
        tableProperties.height = tableProperties.height ?: 0

        //TODO: calculate height
//        //Calculate row height
//        for (row < -headerWithRows if row.height <= 0) {
//            val rowHeight = row.cells.maxBy(cell => cell . lines . size).lines.size
//            row.height = if (rowHeight > 0) rowHeight else 1
//        }

        return tableProperties
    }

    override fun visit(rowProperties: RowProperties<STYLE>): RowProperties<STYLE> {

        return rowProperties
    }

    override fun visit(cellProperties: CellProperties<STYLE>): CellProperties<STYLE> {

        return cellProperties
    }
}
