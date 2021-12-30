package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.Style
import net.igsoft.tablevis.Text

class BasePropertiesResolver<STYLE : Style> : Visitor<STYLE> {
    override fun visit(tableProperties: TableProperties<STYLE>): TableProperties<STYLE> {
        var naturalWidth = 0

        tableProperties.rows.forEach {
            val rowProperties = it.applyVisitor(this)
            naturalWidth = Math.max(naturalWidth, rowProperties.naturalWidth)
        }

        return tableProperties
    }

    override fun visit(rowProperties: RowProperties<STYLE>): RowProperties<STYLE> {
        var naturalWidth = rowProperties.cells.first().properties.style.verticalLineWidth

        rowProperties.cells.forEach {
            val cellProperties = it.applyVisitor(this)
            naturalWidth += cellProperties.naturalWidth + cellProperties.style.verticalLineWidth
        }

        rowProperties.naturalWidth = naturalWidth

        return rowProperties
    }

    override fun visit(cellProperties: CellProperties<STYLE>): CellProperties<STYLE> {
        if (cellProperties.text.isEmpty()) {
            cellProperties.lines = listOf()
            cellProperties.naturalTextWidth = 0
            cellProperties.minimalTextWidth = 0
        } else {
            val lines = Text.resolveTabs(cellProperties.text).lines()
            cellProperties.lines = lines
            cellProperties.naturalTextWidth = lines.maxOf { it.length }
            //minimalTextWidth is already assigned from style or from user
        }

        cellProperties.naturalWidth =
            cellProperties.leftMargin + cellProperties.naturalTextWidth + cellProperties.rightMargin

        return cellProperties
    }
}
