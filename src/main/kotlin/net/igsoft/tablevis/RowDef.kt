package net.igsoft.tablevis

import net.igsoft.tablevis.visitor.RowProperties
import net.igsoft.tablevis.visitor.Visitor
import kotlin.math.max

class RowDef<STYLE: Style>(style: STYLE) : BaseDef<STYLE, RowProperties<STYLE>>(RowProperties(style), style) {

    fun cell(cellStyle: STYLE = style, block: CellDef<STYLE>.() -> Unit = {}) {
        properties.cells.add(CellDef(cellStyle).apply(block))
    }

    //------------------------------------------------------------------------------------------------------------------

    internal fun build(): Row {
        return Row(properties.width!!, properties.height!!, style, properties.cells.map { it.build() })
    }

    internal fun distributeRemainingSpace(remainingSpace: Int) {
        if (remainingSpace > 0) {
            if (properties.cellsWithNoWidth.isNotEmpty()) {
                //Distribute remaining space to cells with no width
                val widths = Utils.distributeEvenly(properties.cellsWithNoWidth.size, remainingSpace)

                for ((cell, width) in properties.cellsWithNoWidth.zip(widths)) {
                    cell.properties.width = width
                }
            } else {
                //Distribute remaining space to cells with width already assigned
                val weights = properties.cells.map { c -> c.properties.width!! }
                val widths = Utils.distributeProportionally(properties.assignedWidth, weights, remainingSpace)

                for ((cell, width) in properties.cells.zip(widths)) {
                    cell.properties.width = cell.properties.width!! + width
                }
            }
        }
    }

    internal fun adjustTexts() {
        //Split text so that it can be put in one cell
        var maxHeight = 0
        var calculatedWidth = 0

        for (cell in properties.cells) {
            cell.adjustTexts()
            maxHeight = max(maxHeight, cell.properties.height!!)
            calculatedWidth += cell.properties.width!! + style.verticalLineWidth
        }

        properties.height = properties.height?: maxHeight
        properties.width = calculatedWidth
    }

    internal fun applyVisitor(visitor: Visitor<STYLE>) = visitor.visit(properties)
}
