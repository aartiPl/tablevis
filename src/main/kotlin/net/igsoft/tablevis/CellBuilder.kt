package net.igsoft.tablevis

class CellBuilder<T : TableStyle>(internal val rowBuilder: RowBuilder<T>) {
    var width: Int? = null
    var height: Int? = null

    var minimalTextWidth = rowBuilder.minimalTextWidth

    var leftIndent = rowBuilder.leftIndent
    var topIndent = rowBuilder.topIndent
    var rightIndent = rowBuilder.rightIndent
    var bottomIndent = rowBuilder.bottomIndent

    var text = ""

    fun id(vararg ids: Any) {
        this.ids = ids.toList()
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

    //------------------------------------------------------------------------------------------------------------------
    //Implementation code

    private lateinit var ids: List<Any>
    private lateinit var lines: List<String>
    private var horizontalAlignment = rowBuilder.horizontalAlignment
    private var verticalAlignment = rowBuilder.verticalAlignment
    internal var naturalWidth = 0
    internal var minimalWidth = 0
    internal var textWidth = 0

    internal fun resolveMissingDimensions() {
        naturalWidth = leftIndent + text.lines().maxOf { it.length } + rightIndent
        minimalWidth = width ?: (leftIndent + minimalTextWidth + rightIndent)
    }

    internal fun adjustTexts() {
        textWidth = (width ?: minimalWidth) - leftIndent - rightIndent

        lines = text.lines().flatMap { it.chunked(textWidth) }

//        if (cell.horizontalAlignment.contains(HorizontalAlignment.Justified)) {
//            val justificationThreshold = cell.cellTextWidth.get * 4 / 5)
//            cell.lines =
//                cell.lines map (line => Text.justifyLine(line, cell.cellTextWidth.get, justificationThreshold))
//        }


    }

    internal fun build(): Cell {
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
