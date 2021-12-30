package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.*
import kotlin.math.max

class WidthResolver<STYLE : Style> : Visitor<STYLE> {
    private var imposedWidth: Boolean = false

    override fun visit(tableProperties: TableProperties<STYLE>): TableProperties<STYLE> {
        //Calculate widths
//        imposedWidth = tableProperties.containsKey(Property.width)
//
//        var naturalWidth = 0
//        var minimalWidth = 0
//
//        table.rows.forEach {
//            val rowProperties = it.applyVisitor(this)
//
//            naturalWidth = max(rowProperties.getValue(Property.naturalWidth), naturalWidth)
//            minimalWidth = max(rowProperties.getValue(Property.minimalWidth), minimalWidth)
//        }
//
//        tableProperties[Property.naturalWidth] = naturalWidth
//        tableProperties[Property.minimalWidth] = minimalWidth
//
//        //If the overall width is not set, set it to naturalWidth...
//        if (!imposedWidth) {
//            tableProperties[Property.width] = naturalWidth
//        }

        return tableProperties
    }

    override fun visit(rowProperties: RowProperties<STYLE>): RowProperties<STYLE> {
//        var naturalWidth = 0
//        var assignedWidth = 0
//        var minimalWidth = 0
//
//        val cellsWithNoWidth = mutableListOf<CellDef<STYLE>>()
//
//        row.cells.forEach { cell ->
//            val cellProperties = cell.applyVisitor(this)
//
//            naturalWidth += cellProperties.getOrDefault(
//                Property.width, cellProperties.getValue(Property.naturalWidth)
//            ) + style.verticalLineWidth
//
//            assignedWidth += cellProperties.getOrDefault(Property.width, 0) + style.verticalLineWidth
//            minimalWidth += cellProperties.getValue(Property.minimalWidth) + style.verticalLineWidth
//
//            if (!cellProperties.containsKey(Property.width)) {
//                cellsWithNoWidth.add(cell)
//            }
//        }
//
//        naturalWidth += style.verticalLineWidth
//        assignedWidth += style.verticalLineWidth
//        minimalWidth += style.verticalLineWidth
//
//        rowProperties[Property.naturalWidth] = naturalWidth
//        rowProperties[Property.assignedWidth] = assignedWidth
//        rowProperties[Property.minimalWidth] = minimalWidth
//
//        rowProperties[cellsWithNoWidthProperty] = cellsWithNoWidth

        return rowProperties
    }

    override fun visit(cellProperties: CellProperties<STYLE>): CellProperties<STYLE> {
//        cellProperties[Property.minimalWidth] = cellProperties.getOrDefault(
//            Property.width,
//            cellProperties.getValue(Property.leftMargin) + cellProperties.getValue(Property.minimalTextWidth) + cellProperties.getValue(
//                Property.rightMargin
//            )
//        )
//
//
//        if (!imposedWidth) {
//            cellProperties[Property.width] =
//                cellProperties.getOrDefault(Property.width, cellProperties.getValue(Property.naturalWidth))
//        }

        return cellProperties
    }
}
