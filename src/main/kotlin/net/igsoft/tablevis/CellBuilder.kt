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

    private var horizontalAlignment = rowBuilder.horizontalAlignment
    private var verticalAlignment = rowBuilder.verticalAlignment
    private var ids: List<Any> = emptyList()
    internal var naturalWidth = 0
    internal var minimalWidth = 0

    internal fun resolveMissingDimensions() {
        naturalWidth = leftIndent + text.lines().maxOf { it.length } + rightIndent
        minimalWidth = leftIndent + minimalTextWidth + rightIndent
    }
}
