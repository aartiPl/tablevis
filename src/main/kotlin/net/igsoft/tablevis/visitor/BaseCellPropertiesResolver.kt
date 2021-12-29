package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.CellDef
import net.igsoft.tablevis.Style
import net.igsoft.tablevis.Text

class BaseCellPropertiesResolver<STYLE : Style> : Visitor<STYLE> {
    override fun visit(cell: CellDef<STYLE>, properties: TypedProperties) {
        if (properties[Property.text].isNullOrEmpty()) {
            properties[Property.lines] = listOf()
            properties[Property.naturalTextWidth] = 0
            properties[Property.minimalTextWidth] = 0
        } else {
            val lines = Text.resolveTabs(properties.getValue(Property.text)).lines()
            properties[Property.lines] = lines
            properties[Property.naturalTextWidth] = lines.maxOf { it.length }
        }

        properties[Property.naturalWidth] =
            properties.getValue(Property.leftMargin) + properties.getValue(Property.naturalTextWidth) + properties.getValue(
                Property.rightMargin
            )
    }
}
