package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.builder.CellProperties
import net.igsoft.tablevis.builder.RowProperties
import net.igsoft.tablevis.builder.TableProperties
import net.igsoft.tablevis.style.Style
import net.igsoft.tablevis.util.Text

class BasePropertiesResolver<STYLE : Style> :
    Visitor<STYLE, TableProperties<STYLE>, RowProperties<STYLE>, CellProperties<STYLE>> {
    override fun visit(tableProperties: TableProperties<STYLE>): TableProperties<STYLE> {
        tableProperties.rows.forEach { row ->
            row.applyVisitor(this)
        }

        return tableProperties
    }

    override fun visit(rowProperties: RowProperties<STYLE>): RowProperties<STYLE> {
        rowProperties.cells.forEach { cell ->
            cell.applyVisitor(this)
        }

        return rowProperties
    }

    // Only cell can be resolved in first turn - other natural sizes depends on later stages of processing
    override fun visit(cellProperties: CellProperties<STYLE>): CellProperties<STYLE> {
        val stringValue = cellProperties.value.toString()

        if (stringValue.isEmpty()) {
            cellProperties.lines = listOf()
            cellProperties.naturalTextWidth = 0
            cellProperties.commonStyle.minimalTextWidth = 0
        } else {
            val lines = Text.resolveTabs(stringValue).lines()
            cellProperties.lines = lines
            cellProperties.naturalTextWidth = lines.maxOf { it.length }
            //minimalTextWidth is already assigned from style or from user
        }

        cellProperties.naturalWidth =
            cellProperties.commonStyle.leftMargin + cellProperties.naturalTextWidth + cellProperties.commonStyle.rightMargin

        return cellProperties
    }
}
