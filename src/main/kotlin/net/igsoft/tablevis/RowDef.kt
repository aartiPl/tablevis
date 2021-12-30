package net.igsoft.tablevis

import net.igsoft.tablevis.visitor.RowProperties
import net.igsoft.tablevis.visitor.Visitor
import kotlin.math.max

class RowDef<STYLE: Style>(style: STYLE) : DefBase<STYLE, RowProperties<STYLE>>(RowProperties(style), style) {

    private var width: Int? = null
    private var height: Int? = null

    var minimalTextWidth = style.minimalTextWidth


    fun cell(cellStyle: STYLE = style, block: CellDef<STYLE>.() -> Unit = {}) {
        cells.add(CellDef(cellStyle).apply(block))
    }

    //------------------------------------------------------------------------------------------------------------------

    internal fun build(): Row {
        return Row(width!!, height!!, style, cells.map { it.build() })
    }

    internal var naturalWidth = 0
    internal var assignedWidth = 0
    internal var minimalWidth = 0

    private val cellsWithNoWidth = mutableListOf<CellDef<STYLE>>()
    val cells = mutableListOf<CellDef<STYLE>>()

    internal fun resolveTexts() {
        for (cell in cells) {
            cell.resolveTexts()
        }
    }

    internal fun resolveWidth(imposedWidth: Boolean) {
        for (cell in cells) {
            cell.resolveWidth(imposedWidth)

            naturalWidth += (cell.width ?: cell.naturalWidth) + style.verticalLineWidth
            assignedWidth += (cell.width ?: 0) + style.verticalLineWidth
            minimalWidth += cell.minimalWidth + style.verticalLineWidth

            if (cell.width == null) {
                cellsWithNoWidth.add(cell)
            }
        }

        naturalWidth += style.verticalLineWidth
        assignedWidth += style.verticalLineWidth
        minimalWidth += style.verticalLineWidth
    }

    internal fun distributeRemainingSpace(remainingSpace: Int) {
        if (remainingSpace > 0) {
            if (cellsWithNoWidth.isNotEmpty()) {
                //Distribute remaining space to cells with no width
                val widths = Utils.distributeEvenly(cellsWithNoWidth.size, remainingSpace)

                for ((cell, width) in cellsWithNoWidth.zip(widths)) {
                    cell.width = width
                }
            } else {
                //Distribute remaining space to cells with width already assigned
                val weights = cells.map { c -> c.width!! }
                val widths = Utils.distributeProportionally(assignedWidth, weights, remainingSpace)

                for ((cell, width) in cells.zip(widths)) {
                    cell.width = cell.width!! + width
                }
            }
        }
    }

    internal fun adjustTexts() {
        //Split text so that it can be put in one cell
        var maxHeight = 0
        var calculatedWidth = 0

        for (cell in cells) {
            cell.adjustTexts()
            maxHeight = max(maxHeight, cell.height!!)
            calculatedWidth += cell.width!! + style.verticalLineWidth
        }

        height = height?: maxHeight
        width = calculatedWidth
    }

    internal fun applyVisitor(visitor: Visitor<STYLE>) = visitor.visit(properties)
}
