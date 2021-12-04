package net.igsoft.tablevis

class RowBuilder<T : TableStyle>(internal val tableBuilder: TableBuilder<T>, internal val style: SectionStyle) {
    var height: Int? = null

    var minimalTextWidth = tableBuilder.minimalTextWidth

    var leftIndent: Int = tableBuilder.leftIndent
    var topIndent: Int = tableBuilder.topIndent
    var rightIndent: Int = tableBuilder.rightIndent
    var bottomIndent: Int = tableBuilder.bottomIndent

    fun addCell(block: CellBuilder<T>.() -> Unit = {}) {
        cells.add(CellBuilder(this).apply(block))
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

    internal fun build(): Row {
        return Row(0, 0, cells.map { it.build() })
    }

    internal var naturalWidth = 0
    internal var assignedWidth = 0
    internal var minimalWidth = 0

    internal var verticalAlignment: VerticalAlignment = tableBuilder.verticalAlignment
    internal var horizontalAlignment: HorizontalAlignment = tableBuilder.horizontalAlignment

    private val cellsWithNoWidth = mutableListOf<CellBuilder<T>>()
    private val cells = mutableListOf<CellBuilder<T>>()

    internal fun resolveMissingDimensions() {
        //Make sure there is at least one cell in a row...
        if (cells.isEmpty()) {
            addCell()
        }

        for (cell in cells) {
            cell.resolveMissingDimensions()

            naturalWidth += (cell.width ?: cell.naturalWidth) + style.verticalLineWidth
            assignedWidth += (cell.width ?: 0) + style.verticalLineWidth
            minimalWidth += cell.minimalWidth + style.verticalLineWidth

            if (cell.width == null) {
                cellsWithNoWidth.add(cell)
            }
        }

        naturalWidth += style.verticalLineWidth
        assignedWidth += style.verticalLineWidth
        minimalWidth += style.verticalLineWidth
    }

    internal fun distributeRemainingSpace(remainingSpace: Int) {
        if (remainingSpace > 0) {
            if (cellsWithNoWidth.isNotEmpty()) {
                //Distribute remaining space to cells with no width
                val widths = Utils.distributeEvenly(cellsWithNoWidth.size, remainingSpace)

                for ((cell, width) in cellsWithNoWidth.zip(widths)) {
                    cell.width = width
                }
            } else {
                //Distribute remaining space to cells with width already assigned
                val weights = cells.map { c -> c.width!! }
                val widths = Utils.distributeProportionally(assignedWidth, weights, remainingSpace)

                for ((cell, width) in cells.zip(widths)) {
                    cell.width = cell.width!! + width
                }
            }
        }
    }

    internal fun adjustTexts() {
        //Split text so that it can be put in one cell
        for (cell in cells) {
            cell.adjustTexts()
        }

    }
}
