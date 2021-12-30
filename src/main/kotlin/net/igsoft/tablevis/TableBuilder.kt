package net.igsoft.tablevis

import net.igsoft.tablevis.builder.CellDef
import net.igsoft.tablevis.builder.TableDef
import net.igsoft.tablevis.model.Table
import net.igsoft.tablevis.style.Style
import net.igsoft.tablevis.style.StyleSet
import net.igsoft.tablevis.visitor.BasePropertiesResolver
import net.igsoft.tablevis.visitor.TextAdjustingResolver
import net.igsoft.tablevis.visitor.WidthResolver

class TableBuilder<STYLE : Style, STYLE_SET : StyleSet<STYLE>>(
    private val styleSet: STYLE_SET, private val block: TableDef<STYLE>.() -> Unit = {}
) {
    fun build(): Table<STYLE_SET> {
        val table = TableDef(styleSet.baseStyle).apply(block)

        //Make sure there is at least one cell in a row...
        for (row in table.properties.rows) {
            if (row.properties.cells.isEmpty()) {
                row.cell()
            }
        }

        //Map cellIds to cells... Add default row and col names (col-1, row-1, etc.)
        val cells = mutableMapOf<Any, MutableSet<CellDef<STYLE>>>()
        var rowCounter = 1
        for (row in table.properties.rows) {
            var colCounter = 1

            for (cell in row.properties.cells) {
                cell.properties.ids.forEach {
                    val cellSet = cells.getOrPut(it) { mutableSetOf() }
                    cellSet.add(cell)
                }

                val rowCellSet = cells.getOrPut("row-$rowCounter") { mutableSetOf() }
                rowCellSet.add(cell)

                val colCellSet = cells.getOrPut("col-$colCounter") { mutableSetOf() }
                colCellSet.add(cell)

                colCounter++
            }

            rowCounter++
        }

        //Do minimal calculations on texts and resolution of cells...
        table.applyVisitor(BasePropertiesResolver())

        //Execute deferred functions...
        for (entry in cells.entries) {
            val cellsToApply = entry.value
            val functionsToExecute = table.properties.functions[entry.key] ?: emptySet()
            functionsToExecute.forEach { it(cellsToApply) }
        }

        //Calculate widths
        val tableProperties = table.applyVisitor(WidthResolver())

        val calculatedWidth = tableProperties.width!!
        val naturalWidth = tableProperties.naturalWidth
        val minimalWidth = tableProperties.minimalWidth

        println("Calculated values: width=$calculatedWidth, naturalWidth=$naturalWidth, minimalWidth=$minimalWidth")

        if (calculatedWidth < minimalWidth) {
            throw IllegalArgumentException("Constraint violation: table width [$calculatedWidth] is less than minimal table width [$minimalWidth]")
        }

        //Calculate cell sizes so that they match table size
        table.applyVisitor(TextAdjustingResolver())

        //TODO: calculate height
        table.properties.height = table.properties.height ?: 0

        return Table(styleSet, calculatedWidth, table.properties.height!!, table.properties.rows.map { it.build() })
    }

//        //Calculate row height
//        for (row < -headerWithRows if row.height <= 0) {
//            val rowHeight = row.cells.maxBy(cell => cell . lines . size).lines.size
//            row.height = if (rowHeight > 0) rowHeight else 1
//        }
//
}
