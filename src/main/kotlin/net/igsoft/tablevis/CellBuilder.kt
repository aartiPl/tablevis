package net.igsoft.tablevis

class CellBuilder<T : TableStyle>(private val rowBuilder: RowBuilder<T>) {
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
    private var naturalTextWidth: Int = 0
    internal var naturalWidth = 0
    internal var minimalWidth = 0
    internal var textWidth = 0

    internal fun resolveMissingDimensions() {
        text = text.replace("\t", "    ")
        lines = if (text.isEmpty()) listOf() else  text.lines()
        naturalTextWidth = lines.maxOf { it.length }
        naturalWidth = leftIndent + naturalTextWidth + rightIndent
        minimalWidth = width ?: (leftIndent + (if (text.isEmpty()) 0 else minimalTextWidth) + rightIndent)
    }

    internal fun adjustTexts() {
        textWidth = (width ?: minimalWidth) - leftIndent - rightIndent

        //Choose strategy of splitting text
        if (textWidth < 5) {
            //Cell is too small to do anything fancy...
            lines = lines.flatMap { it.chunked(textWidth) }
        } else {
            //Split using whitespaces and potentially dashes "-";
            //TODO:
            lines = lines.flatMap { it.chunked(textWidth) }
        }

        width = width ?: (leftIndent + naturalTextWidth + rightIndent)
        height = height ?: lines.size

//        if (cell.horizontalAlignment.contains(HorizontalAlignment.Justified)) {
//            val justificationThreshold = cell.cellTextWidth.get * 4 / 5)
//            cell.lines =
//                cell.lines map (line => Text.justifyLine(line, cell.cellTextWidth.get, justificationThreshold))
//        }
    }

    internal fun build(): Cell {

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
