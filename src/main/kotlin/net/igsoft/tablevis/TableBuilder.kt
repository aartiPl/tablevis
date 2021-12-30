package net.igsoft.tablevis

import net.igsoft.tablevis.builder.TableDef
import net.igsoft.tablevis.model.Table
import net.igsoft.tablevis.style.Style
import net.igsoft.tablevis.style.StyleSet
import net.igsoft.tablevis.visitor.BasePropertiesResolver
import net.igsoft.tablevis.visitor.CellIdResolver
import net.igsoft.tablevis.visitor.TextAdjustingResolver
import net.igsoft.tablevis.visitor.WidthResolver

class TableBuilder<STYLE : Style, STYLE_SET : StyleSet<STYLE>>(
    private val styleSet: STYLE_SET, private val block: TableDef<STYLE>.() -> Unit = {}
) {
    fun build(): Table<STYLE_SET> {
        val table = TableDef(styleSet.baseStyle).apply(block)

        //Make sure there is at least one cell in a row...
        //Map cellIds to cells... Add default row and col names (col-1, row-1, etc.)
        table.applyVisitor(CellIdResolver())

        //Do minimal calculations on texts and resolution of cells...
        table.applyVisitor(BasePropertiesResolver())

        //Execute deferred functions...
        for (entry in table.properties.cellsPerId.entries) {
            val cellsToApply = entry.value
            val functionsToExecute = table.properties.functions[entry.key] ?: emptySet()
            functionsToExecute.forEach { it(cellsToApply) }
        }

        //Calculate widths
        val tableProperties = table.applyVisitor(WidthResolver())

        //Calculate cell sizes so that they match table size
        table.applyVisitor(TextAdjustingResolver())

        //TODO: calculate height
        table.properties.height = table.properties.height ?: 0

        return Table(styleSet, tableProperties.width!!, table.properties.height!!, table.properties.rows.map { it.build() })
    }

//        //Calculate row height
//        for (row < -headerWithRows if row.height <= 0) {
//            val rowHeight = row.cells.maxBy(cell => cell . lines . size).lines.size
//            row.height = if (rowHeight > 0) rowHeight else 1
//        }
//
}
