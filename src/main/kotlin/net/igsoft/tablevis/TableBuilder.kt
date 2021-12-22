package net.igsoft.tablevis

import kotlin.math.max

class TableBuilder<STYLE : Style, STYLE_SET : StyleSet<STYLE>>(private val styleSet: STYLE_SET) {
    private val functions = mutableMapOf<Any, MutableSet<(Set<CellBuilder<STYLE, STYLE_SET>>) -> Unit>>()
    private val cells = mutableMapOf<Any, MutableSet<CellBuilder<STYLE, STYLE_SET>>>()

    private val rows = mutableListOf<RowBuilder<STYLE, STYLE_SET>>()

    var minimalTextWidth = 1

    var width: Int? = null
    var height: Int? = null

    var leftMargin: Int = styleSet.leftMargin
    var topMargin: Int = styleSet.topMargin
    var rightMargin: Int = styleSet.rightMargin
    var bottomMargin: Int = styleSet.bottomMargin

    fun row(style: STYLE = styleSet.baseStyle, block: RowBuilder<STYLE, STYLE_SET>.() -> Unit = {}) {
        rows.add(RowBuilder(this, style).apply(block))
    }

    fun alignCenter() = apply {
        this.horizontalAlignment = HorizontalAlignment.Center
    }

    fun alignLeft() = apply {
        this.horizontalAlignment = HorizontalAlignment.Left
    }

    fun alignRight() = apply {
        this.horizontalAlignment = HorizontalAlignment.Right
    }

    fun justify() = apply {
        this.horizontalAlignment = HorizontalAlignment.Justified
    }

    fun alignTop() = apply {
        this.verticalAlignment = VerticalAlignment.Top
    }

    fun alignMiddle() = apply {
        this.verticalAlignment = VerticalAlignment.Middle
    }

    fun alignBottom() = apply {
        this.verticalAlignment = VerticalAlignment.Bottom
    }

    fun forId(vararg id: Any): IdOperation<STYLE, STYLE_SET> = IdOperation(this, id.toList())

    internal fun build(): Table<STYLE, STYLE_SET> {
        //Do minimal calculations on texts...
        for (row in rows) {
            row.resolveTexts()
        }

        //Execute deferred functions...
        for (entry in cells.entries) {
            val cellsToApply = entry.value
            val functionsToExecute = functions[entry.key] ?: emptySet()
            functionsToExecute.forEach { it(cellsToApply) }
        }

        var naturalWidth = 0
        var minimalWidth = 0

        for (row in rows) {
            row.resolveWidth()

            naturalWidth = max(row.naturalWidth, naturalWidth)
            minimalWidth = max(row.minimalWidth, minimalWidth)
        }

        //If the overall width is not set, set it to naturalWidth...
        val calculatedWidth = width ?: naturalWidth

        println("Calculated values: width=$calculatedWidth, naturalWidth=$naturalWidth, minimalWidth=$minimalWidth")

        if (calculatedWidth < minimalWidth) {
            throw IllegalArgumentException("Constraint violation: table width [$calculatedWidth] is less than minimal table width [$minimalWidth]")
        }

        //Calculate cell sizes so that they match table size
        for (row in rows) {
            val remainingSpace = calculatedWidth - row.assignedWidth
            row.distributeRemainingSpace(remainingSpace)
            row.adjustTexts()
        }

        height = height ?: 0

        return Table(styleSet, calculatedWidth, height!!, this.rows.map { it.build() })
    }

    internal var verticalAlignment: VerticalAlignment = styleSet.verticalAlignment
    internal var horizontalAlignment: HorizontalAlignment = styleSet.horizontalAlignment

    internal fun addOperation(id: Any, fn: (Set<CellBuilder<STYLE, STYLE_SET>>) -> Unit) {
        val functionsSet = functions.getOrPut(id) { mutableSetOf() }
        functionsSet.add(fn)
    }

    internal fun registerId(id: Any, cell: CellBuilder<STYLE, STYLE_SET>) {
        val cellsSet = cells.getOrPut(id) { mutableSetOf() }
        cellsSet.add(cell)
    }

    //
//    private val idRegistry = mutable.MultiDict.empty[Any, TextCellBuilder]
//    private val operationRegistry = mutable.MultiDict.empty[Any, Function[Set[TextCellBuilder], Unit]]

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
//    private fun addId(id: Any, cell: TextCellBuilder): Unit = {
//        idRegistry.addOne(id -> cell)
//    }
//
//    private fun addOperation(id: Any, fn: Function[Set[TextCellBuilder], Unit]): Unit =
//    {
//        operationRegistry.addOne(id -> fn)
//    }

}
