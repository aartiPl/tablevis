package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.builder.CellDef
import net.igsoft.tablevis.builder.CellProperties
import net.igsoft.tablevis.builder.RowProperties
import net.igsoft.tablevis.builder.TableProperties
import net.igsoft.tablevis.style.Style
import kotlin.math.max

class WidthResolver<STYLE : Style> : Visitor<STYLE, TableProperties<STYLE>, RowProperties<STYLE>, CellProperties<STYLE>> {
    private var imposedWidth: Boolean = false

    override fun visit(tableProperties: TableProperties<STYLE>): TableProperties<STYLE> {
        //Calculate widths
        imposedWidth = (tableProperties.width != null)

        var naturalWidth = 0
        var minimalWidth = 0

        tableProperties.rows.forEach {
            val rowProperties = it.applyVisitor(this)

            naturalWidth = max(rowProperties.naturalWidth, naturalWidth)
            minimalWidth = max(rowProperties.minimalWidth, minimalWidth)
        }

        tableProperties.naturalWidth = naturalWidth
        tableProperties.minimalWidth = minimalWidth

        //If the overall width is not set, set it to naturalWidth...
        if (!imposedWidth) {
            tableProperties.width = naturalWidth
        }

        println("Calculated values: width=${tableProperties.width}, naturalWidth=$naturalWidth, minimalWidth=$minimalWidth")

        if (tableProperties.width!! < minimalWidth) {
            throw IllegalArgumentException("Constraint violation: table width [$tableProperties.width] is less than minimal table width [$minimalWidth]")
        }

        return tableProperties
    }

    override fun visit(rowProperties: RowProperties<STYLE>): RowProperties<STYLE> {
        var naturalWidth = 0
        var assignedWidth = 0
        var minimalWidth = 0

        val cellsWithNoWidth = mutableListOf<CellProperties<STYLE>>()

        var firstVerticalLineWidth = -1

        rowProperties.cells.forEach { cell ->
            val cellProperties = cell.applyVisitor(this)

            if (firstVerticalLineWidth == -1) {
                firstVerticalLineWidth = cellProperties.style.verticalLineWidth
            }

            naturalWidth += (cellProperties.width
                ?: cellProperties.naturalWidth) + cellProperties.style.verticalLineWidth

            assignedWidth += (cellProperties.width ?: 0) + cellProperties.style.verticalLineWidth
            minimalWidth += cellProperties.minimalWidth + cellProperties.style.verticalLineWidth

            if (cellProperties.width == null) {
                cellsWithNoWidth.add(cellProperties)
            }
        }

        naturalWidth += firstVerticalLineWidth
        assignedWidth += firstVerticalLineWidth
        minimalWidth += firstVerticalLineWidth

        rowProperties.naturalWidth = naturalWidth
        rowProperties.assignedWidth = assignedWidth
        rowProperties.minimalWidth = minimalWidth

        rowProperties.cellsWithNoWidth = cellsWithNoWidth

        return rowProperties
    }

    override fun visit(cellProperties: CellProperties<STYLE>): CellProperties<STYLE> {
        cellProperties.minimalWidth =
            cellProperties.width
                ?: (cellProperties.leftMargin + cellProperties.minimalTextWidth + cellProperties.rightMargin)

        if (!imposedWidth) {
            cellProperties.width = cellProperties.width ?: cellProperties.naturalWidth
        }

        return cellProperties
    }
}
