package net.igsoft.tablevis

import kotlin.math.max

class RowBuilder<STYLE: Style>(private val style: STYLE) {
    private var width: Int? = null
    private var height: Int? = null

    var minimalTextWidth = style.minimalTextWidth

    var leftMargin: Int = style.leftMargin
    var topMargin: Int = style.topMargin
    var rightMargin: Int = style.rightMargin
    var bottomMargin: Int = style.bottomMargin

    fun cell(cellStyle: STYLE = style, block: CellBuilder<STYLE>.() -> Unit = {}) {
        cells.add(CellBuilder(cellStyle).apply(block))
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

    internal fun build(): Row {
        return Row(width!!, height!!, style, cells.map { it.build() })
    }

    internal var naturalWidth = 0
    internal var assignedWidth = 0
    internal var minimalWidth = 0

    internal var verticalAlignment: VerticalAlignment = style.verticalAlignment
    internal var horizontalAlignment: HorizontalAlignment = style.horizontalAlignment

    private val cellsWithNoWidth = mutableListOf<CellBuilder<STYLE>>()
    val cells = mutableListOf<CellBuilder<STYLE>>()

    internal fun resolveTexts(cells: MutableMap<Any, MutableSet<CellBuilder<STYLE>>>) {
        //Make sure there is at least one cell in a row...
        if (this.cells.isEmpty()) {
            cell()
        }

        for (cell in this.cells) {
            cell.resolveTexts(cells)
        }
    }

    internal fun resolveWidth() {
        for (cell in cells) {
            cell.resolveWidth()

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
        var maxHeight = 0
        var calculatedWidth = 0

        for (cell in cells) {
            cell.adjustTexts()
            maxHeight = max(maxHeight, cell.height!!)
            calculatedWidth += cell.width!! + style.verticalLineWidth
        }

        height = height?: maxHeight
        width = calculatedWidth
    }
}
