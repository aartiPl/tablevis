package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.CellDef
import net.igsoft.tablevis.Style
import net.igsoft.tablevis.Text

class BaseCellPropertiesVisitor<STYLE: Style> : Visitor<STYLE> {
    override fun visit(cell: CellDef<STYLE>, properties: CellProperties) {
        if (cell.text.isEmpty()) {
            properties.lines = listOf()
            properties.naturalTextWidth = 0
        } else {
            properties.lines = Text.resolveTabs(cell.text).lines()
            properties.naturalTextWidth = properties.lines.maxOf { it.length }
        }

        properties.naturalWidth = cell.leftMargin + properties.naturalTextWidth + cell.rightMargin
    }
}
