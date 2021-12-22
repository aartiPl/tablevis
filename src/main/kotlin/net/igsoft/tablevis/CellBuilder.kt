package net.igsoft.tablevis

import kotlin.math.max

class CellBuilder<S: Style, T : StyleSet<S>>(private val rowBuilder: RowBuilder<S, T>) {
    var width: Int? = null
    var height: Int? = null

    var minimalTextWidth = rowBuilder.minimalTextWidth

    var leftMargin = rowBuilder.leftMargin
    var topMargin = rowBuilder.topMargin
    var rightMargin = rowBuilder.rightMargin
    var bottomMargin = rowBuilder.bottomMargin

    var text = ""

    fun id(vararg ids: Any) {
        ids.forEach { rowBuilder.tableBuilder.registerId(it, this) }
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
    internal var naturalTextWidth: Int = 0
    internal var naturalWidth = 0
    internal var minimalWidth = 0
    internal var textWidth = 0

    internal fun resolveTexts() {
        if (text.isEmpty()) {
            lines = listOf()
            naturalTextWidth = 0
        } else {
            text = Text.resolveTabs(text)
            lines = text.lines()
            naturalTextWidth = lines.maxOf { it.length }
        }

        naturalWidth = leftMargin + naturalTextWidth + rightMargin
    }

    internal fun resolveWidth() {
        minimalWidth = width ?: (leftMargin + (if (text.isEmpty()) 0 else minimalTextWidth) + rightMargin)
    }

    internal fun adjustTexts() {
        textWidth = (width ?: minimalWidth) - leftMargin - rightMargin

        //Choose strategy of splitting text
        lines = if (textWidth < 5) {
            //Cell is too small to do anything fancy...
            lines.flatMap { it.chunked(textWidth) }
        } else {
            //Split textually
            lines.flatMap { Text.splitLineTextually(it, textWidth) }
        }

        width = width ?: (leftMargin + naturalTextWidth + rightMargin)
        height = height ?: max(lines.size, 1)

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
            leftMargin,
            topMargin,
            rightMargin,
            bottomMargin,
            horizontalAlignment,
            verticalAlignment,
            lines
        )
    }
}
