package net.igsoft.tablevis

class TableBuilder<T : TableStyle>(private val style: T) {
    private val headers = mutableListOf<RowBuilder<T>>()
    private val rows = mutableListOf<RowBuilder<T>>()
    private val footers = mutableListOf<RowBuilder<T>>()

    var width: Int? = null
    var height: Int? = null

    var leftIndent: Int = style.leftIndent
    var topIndent: Int = style.topIndent
    var rightIndent: Int = style.rightIndent
    var bottomIndent: Int = style.bottomIndent

    var verticalAlignment: VerticalAlignment = style.verticalAlignment
    var horizontalAlignment: HorizontalAlignment = style.horizontalAlignment

    fun addHeader(block: RowBuilder<T>.() -> Unit = {}) {
        headers.add(RowBuilder(this).apply(block))
    }

    fun addRow(block: RowBuilder<T>.() -> Unit = {}) {
        rows.add(RowBuilder(this).apply(block))
    }

    fun addFooter(block: RowBuilder<T>.() -> Unit = {}) {
        footers.add(RowBuilder(this).apply(block))
    }

    fun build(): Table<T> {
        val allRows = headers + rows + footers

        var maxRowWidth = 0
        for (row in allRows) {
            //Make sure there is at least one cell in a row...
            if (row.cells.isEmpty()) {
                row.addCell()
            }


        }

//        //Execute deferred functions...
//        for (id  in operationRegistry.keySet) {
//            val operations = operationRegistry.get(id)
//            val set: Set<TextCellBuilder> = Set.from(idRegistry.get(id))
//            operations.forEach(operation => operation (set))
//        }

        //Now try to make sense of different options set on different levels.... :-)

        width = width ?: (headers + rows + footers).maxOf { it.width ?: 0 }
        height = height ?: (headers + rows + footers).sumOf { it.height ?: 0 }

        return Table(
            style,
            width!!,
            height!!,
            headers.map { it.build() },
            rows.map { it.build() },
            footers.map { it.build() })
    }

    //
//    private val idRegistry = mutable.MultiDict.empty[Any, TextCellBuilder]
//    private val operationRegistry = mutable.MultiDict.empty[Any, Function[Set[TextCellBuilder], Unit]]
//    private var headerHorizontalLineChar = "="
//    private var horizontalLineChar = "-"
//    private var verticalLineChar = "|"
//    private var tableLineSeparator = System.lineSeparator
//
//    private var defaultIndent = Indent(leftIndent = 1, rightIndent = 1)
//    private var defaultAlignment = Alignment(horizontal = HorizontalAlignment.Left, vertical = VerticalAlignment.Middle)
//

//    fun noBorders(): TextTableBuilder = apply {
//        this.headerHorizontalLineChar = ""
//        this.horizontalLineChar = ""
//        this.verticalLineChar = ""
//        this.defaultIndent = Indent(leftIndent = 0, topIndent = 0, rightIndent = 1, bottomIndent = 0)
//    }
//
//    fun forId(id: Any): IdOperationHelper = apply {
//        new IdOperationHelper (this, id)
//    }
//
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
//        for (row < -headerWithRows; cell < -row.cells) {
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

//    private fun calculateMaximumRowWidth(row: TextRow): Int {
//        var rowSize = 0
//
//        for (cell in row.cells) {
//
//            if (cell.cellTextWidth.isEmpty) {
//                cell.cellTextWidth = Utils.maxLineSizeBasedOnText(cell.cellText))
//            }
//
//            rowSize += cell.cellTextWidth.get + cell.cellLeftIndent.getOrElse(defaultIndent.leftIndent) + cell.cellRightIndent.getOrElse(
//                defaultIndent.rightIndent
//            ) + verticalLineChar.size
//        }
//
//        rowSize += verticalLineChar.size
//
//        return rowSize
//    }
}
