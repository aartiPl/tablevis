package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.builder.CellProperties
import net.igsoft.tablevis.builder.RowProperties
import net.igsoft.tablevis.style.Style
import net.igsoft.tablevis.builder.TableProperties
import net.igsoft.tablevis.util.Text
import net.igsoft.tablevis.util.Utils
import kotlin.math.max

class TextAdjustingResolver<STYLE : Style> : Visitor<STYLE> {
    private var width = 0
    override fun visit(tableProperties: TableProperties<STYLE>): TableProperties<STYLE> {
        width = tableProperties.width!!

        tableProperties.rows.forEach {
            it.applyVisitor(this)
        }

        return tableProperties
    }

    override fun visit(rowProperties: RowProperties<STYLE>): RowProperties<STYLE> {
        val remainingSpace = width - rowProperties.assignedWidth

        if (remainingSpace > 0) {
            if (rowProperties.cellsWithNoWidth.isNotEmpty()) {
                //Distribute remaining space to cells with no width
                val widths = Utils.distributeEvenly(rowProperties.cellsWithNoWidth.size, remainingSpace)

                for ((cell, width) in rowProperties.cellsWithNoWidth.zip(widths)) {
                    cell.properties.width = width
                }
            } else {
                //Distribute remaining space to cells with width already assigned
                val weights = rowProperties.cells.map { c -> c.properties.width!! }
                val widths = Utils.distributeProportionally(rowProperties.assignedWidth, weights, remainingSpace)

                for ((cell, width) in rowProperties.cells.zip(widths)) {
                    cell.properties.width = cell.properties.width!! + width
                }
            }
        }

        var maxHeight = 0
        var calculatedWidth = 0

        rowProperties.cells.forEach {
            //Split text so that it can be put in one cell
            val cellProperties = it.applyVisitor(this)

            maxHeight = max(maxHeight, cellProperties.height!!)
            calculatedWidth += cellProperties.width!! + cellProperties.style.verticalLineWidth
        }

        rowProperties.height = rowProperties.height?: maxHeight
        rowProperties.width = calculatedWidth

        return rowProperties
    }

    override fun visit(cellProperties: CellProperties<STYLE>): CellProperties<STYLE> {
        cellProperties.textWidth = (cellProperties.width ?: cellProperties.minimalWidth) - cellProperties.leftMargin - cellProperties.rightMargin

        //Choose strategy of splitting text
        cellProperties.lines = if (cellProperties.textWidth < 5) {
            //Cell is too small to do anything fancy...
            cellProperties.lines.flatMap { it.chunked(cellProperties.textWidth) }
        } else {
            //Split textually
            cellProperties.lines.flatMap { Text.splitLineTextually(it, cellProperties.textWidth) }
        }

        cellProperties.height = cellProperties.height ?: max(cellProperties.lines.size, 1)

        //TODO: justify text in Printer
//        if (cell.horizontalAlignment.contains(HorizontalAlignment.Justified)) {
//            val justificationThreshold = cell.cellTextWidth.get * 4 / 5)
//            cell.lines =
//                cell.lines map (line => Text.justifyLine(line, cell.cellTextWidth.get, justificationThreshold))
//        }

        return cellProperties
    }
}
