package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.builder.CellDef
import net.igsoft.tablevis.builder.CellProperties
import net.igsoft.tablevis.builder.RowProperties
import net.igsoft.tablevis.builder.TableProperties
import net.igsoft.tablevis.style.Style

class CellIdResolver<STYLE : Style> :
    Visitor<STYLE, TableProperties<STYLE>, RowProperties<STYLE>, CellProperties<STYLE>> {
    private lateinit var cellsPerId: MutableMap<Any, MutableSet<CellProperties<STYLE>>>

    private var colCounter = 1
    private var rowCounter = 1

    override fun visit(tableProperties: TableProperties<STYLE>): TableProperties<STYLE> {
        cellsPerId = tableProperties.cellsPerId

        tableProperties.rows.forEach {
            it.applyVisitor(this)
            rowCounter++
        }

        return tableProperties
    }

    override fun visit(rowProperties: RowProperties<STYLE>): RowProperties<STYLE> {
        //Make sure there is at least one cell in a row...
        if (rowProperties.cells.isEmpty()) {
            rowProperties.cells.add(CellDef(rowProperties.commonStyle))
        }

        val rowCellSet = cellsPerId.getOrPut("row-$rowCounter") { mutableSetOf() }

        colCounter = 1
        rowProperties.cells.forEach {
            val cellProperties = it.applyVisitor(this)

            rowCellSet.add(cellProperties)

            val colCellSet = cellsPerId.getOrPut("col-$colCounter") { mutableSetOf() }
            colCellSet.add(cellProperties)

            colCounter++
        }

        return rowProperties
    }

    override fun visit(cellProperties: CellProperties<STYLE>): CellProperties<STYLE> {
        cellProperties.ids.forEach {
            val cellSet = cellsPerId.getOrPut(it) { mutableSetOf() }
            cellSet.add(cellProperties)
        }

        return cellProperties
    }
}
