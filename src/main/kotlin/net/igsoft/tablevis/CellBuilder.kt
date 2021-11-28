package net.igsoft.tablevis

class CellBuilder<T : TableStyle>(internal val rowBuilder: RowBuilder<T>) {
    var width: Int? = null
    var height: Int? = null

    var leftIndent = rowBuilder.leftIndent
    var topIndent = rowBuilder.topIndent
    var rightIndent = rowBuilder.rightIndent
    var bottomIndent = rowBuilder.bottomIndent

    var verticalAlignment = rowBuilder.verticalAlignment
    var horizontalAlignment = rowBuilder.horizontalAlignment

    var text = ""

    fun build(): Cell {
        val lines = text.lines()


        width = width ?: lines.maxOf { it.length }
        height = height ?: lines.size

        return Cell(
            width!!,
            height!!,
            leftIndent,
            topIndent,
            rightIndent,
            bottomIndent,
            horizontalAlignment,
            verticalAlignment,
            lines
        )
    }
}

//
//    internal val row: TextRowBuilder) {
//    private var lines = mutableListOf<String>()
//    private var cellText: String = ""
//    private var cellId: Any = ""
//    private var horizontalAlignment: HorizontalAlignment? = null
//    private var verticalAlignment: VerticalAlignment? = null
//    private var cellTextWidth: Int? = null
//
//    private var cellLeftIndent: Int? = null
//    private var cellTopIndent: Int? = null
//    private var cellRightIndent: Int? = null
//    private var cellBottomIndent: Int? = null
//
//    fun text(cellText: String): TextCellBuilder = apply {
//        this.cellText = cellText
//    }
//
//    fun id(cellId: Any): TextCellBuilder = apply {
//        this.cellId = cellId
//        row.table.addId(cellId, this)
//    }
//
//    fun textWidth(cellTextWidth: Int): TextCellBuilder = apply {
//        this.cellTextWidth = cellTextWidth
//    }
//
//    fun leftIndent(leftIndent: Int): TextCellBuilder = apply {
//        this.cellLeftIndent = leftIndent
//    }
//
//    fun rightIndent(rightIndent: Int): TextCellBuilder = apply {
//        this.cellRightIndent = rightIndent
//    }
//
//    fun topIndent(topIndent: Int): TextCellBuilder = apply {
//        this.cellTopIndent = topIndent
//    }
//
//    fun bottomIndent(bottomIndent: Int): TextCellBuilder = apply {
//        this.cellBottomIndent = bottomIndent
//    }
//
//    fun alignCenter(): TextCellBuilder = apply {
//        this.horizontalAlignment = HorizontalAlignment.Center
//    }
//
//    fun alignLeft(): TextCellBuilder = apply {
//        this.horizontalAlignment = HorizontalAlignment.Left
//    }
//
//    fun alignRight(): TextCellBuilder = apply {
//        this.horizontalAlignment = HorizontalAlignment.Right
//    }
//
//    fun justify(): TextCellBuilder = apply {
//        this.horizontalAlignment = HorizontalAlignment.Justified
//    }
//
//    fun alignTop(): TextCellBuilder = apply {
//        this.verticalAlignment = VerticalAlignment.Top
//    }
//
//    fun alignMiddle(): TextCellBuilder = apply {
//        this.verticalAlignment = VerticalAlignment.Middle
//    }
//
//    fun alignBottom(): TextCellBuilder = apply {
//        this.verticalAlignment = VerticalAlignment.Bottom
//    }
//}
