package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.Style
import net.igsoft.tablevis.Text

class BaseCellPropertiesResolver<STYLE : Style> : Visitor<STYLE> {
    override fun visit(properties: CellProperties<STYLE>): CellProperties<STYLE> {
        if (properties.text.isEmpty()) {
            properties.lines = listOf()
            properties.naturalTextWidth = 0
            properties.minimalTextWidth = 0
        } else {
            val lines = Text.resolveTabs(properties.text).lines()
            properties.lines = lines
            properties.naturalTextWidth = lines.maxOf { it.length }
        }

        properties.naturalWidth = properties.leftMargin + properties.naturalTextWidth + properties.rightMargin

        return properties
    }
}
