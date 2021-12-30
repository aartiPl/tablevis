package net.igsoft.tablevis

import net.igsoft.tablevis.visitor.BasePropertiesResolver
import net.igsoft.tablevis.visitor.WidthResolver
import kotlin.math.max

object Creator {
    fun <STYLE : Style, STYLE_SET : StyleSet<STYLE>> create(styleSet: STYLE_SET, table: TableDef<STYLE>): Table<STYLE_SET> {
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

            for(cell in row.properties.cells) {
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
        for (row in table.properties.rows) {
            val remainingSpace = calculatedWidth - row.properties.assignedWidth
            row.distributeRemainingSpace(remainingSpace)
            row.adjustTexts()
        }

        table.properties.height = table.properties.height ?: 0

        return Table(styleSet, calculatedWidth, table.properties.height!!, table.properties.rows.map { it.build() })
    }

//    fun build(): TextTable {
//        // Set default values if necessary...
//        val headerWithRows: mutable.Buffer[TextRowBuilder] = if (header.isDefined) header.get+: rows else rows
//
//        //Execute functions...
//        for (id < -operationRegistry.keySet) {
//            //FIXME: jeśli nie ma wierszy, a wywoła się forId to tu wylatuje exception
//            val operations = operationRegistry.get(id)
//            val set: Set[TextCellBuilder] = Set.from(idRegistry.get(id))
//            operations.foreach(operation => operation (set))
//        }
//
//        if (width.isEmpty) {
//            //Calculated max width of row; 0 means no limits for table width
//            var maxRowSize = 0
//            for (row < -headerWithRows) {
//                val rowSize = calculateMaximumRowWidth(row)
//                maxRowSize = if (rowSize > maxRowSize) rowSize else maxRowSize
//            }
//
//            width = maxRowSize)
//        }
//
//        //Calculate cell sizes so that they match table size
//        for (row < -headerWithRows) {
//            val cellsWithNoWidth = mutable.Buffer[TextCellBuilder]()
//            var assignedSize = 0
//
//            for (cell < -row.cells) {
//            assignedSize += cell.cellLeftIndent.getOrElse(defaultIndent.leftIndent) + cell.cellRightIndent.getOrElse(
//                    defaultIndent.rightIndent
//                ) + verticalLineChar.size
//
//            if (cell.cellTextWidth.isEmpty) {
//                cellsWithNoWidth += cell
//            } else {
//                assignedSize += cell.cellTextWidth.get
//            }
//        }
//
//            assignedSize += verticalLineChar.size
//
//            val remainingSpace = width.get - assignedSize
//
//            if (remainingSpace > 0) {
//                if (cellsWithNoWidth.nonEmpty) {
//                    //Distribute remaining space to cells with no width
//                    val widths = Utils.distributeEvenly(cellsWithNoWidth.size, remainingSpace)
//
//                    for ((cell, width) < -cellsWithNoWidth.zip(widths)) {
//                        cell.cellTextWidth = width)
//                    }
//                } else {
//                    //Distribute remaining space to cells with width assigned
//                    val weights = row.cells.map(c => c . cellTextWidth . get).toList
//                    val widths = Utils.distributeProportionally(assignedSize, weights, remainingSpace)
//
//                    for ((cell, width) < -row.cells.zip(widths)) {
//                        cell.cellTextWidth = cell.cellTextWidth.get + width)
//                    }
//                }
//            }
//        }
//
//        //Split text so that it can be put in one cell
//        for (row  in headerWithRows; cell in row.cells) {
//            cell.lines = Text.splitByWidth(cell.cellText, cell.cellTextWidth.get, 8)
//
//            if (cell.horizontalAlignment.contains(HorizontalAlignment.Justified)) {
//                val justificationThreshold = cell.cellTextWidth.get * 4 / 5)
//                cell.lines =
//                    cell.lines map (line => Text.justifyLine(line, cell.cellTextWidth.get, justificationThreshold))
//            }
//        }
//
//        //Calculate row height
//        for (row < -headerWithRows if row.height <= 0) {
//            val rowHeight = row.cells.maxBy(cell => cell . lines . size).lines.size
//            row.height = if (rowHeight > 0) rowHeight else 1
//        }
//
//        val newHeader = header.map(h => mapRow (h))
//        val newRows = rows.map(r => mapRow (r))
//
//        val tablePrinter =
//            new TextTablePrinter (headerHorizontalLineChar, horizontalLineChar, verticalLineChar, tableLineSeparator)
//
//        TextTable(tablePrinter, width.get, newHeader, newRows.toList)
//    }
//
//    private fun mapRow(row: TextRowBuilder): Row = {
//        val newCells = ListBuffer[Cell]()
//
//        for (cell < -row.cells) {
//        val indent = Indent(
//            leftIndent = cell.cellLeftIndent.getOrElse(defaultIndent.leftIndent),
//            topIndent = cell.cellTopIndent.getOrElse(defaultIndent.topIndent),
//            rightIndent = cell.cellRightIndent.getOrElse(defaultIndent.rightIndent),
//            bottomIndent = cell.cellBottomIndent.getOrElse(defaultIndent.bottomIndent)
//        )
//
//        val alignment = Alignment(
//            horizontal = cell.horizontalAlignment.getOrElse(defaultAlignment.horizontal),
//            vertical = cell.verticalAlignment.getOrElse(defaultAlignment.vertical)
//        )
//
//        newCells += Cell(cell.cellTextWidth.get, indent, alignment, cell.lines)
//    }
//
//        Row(row.height, newCells.toList)
//    }
//
}
