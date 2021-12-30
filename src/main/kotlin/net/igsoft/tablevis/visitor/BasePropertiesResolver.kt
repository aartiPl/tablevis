package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.Style
import net.igsoft.tablevis.Text

class BasePropertiesResolver<STYLE : Style> : Visitor<STYLE> {
    // Only cell can be resolved in first turn - other natural sizes depends on later stages of processing
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
