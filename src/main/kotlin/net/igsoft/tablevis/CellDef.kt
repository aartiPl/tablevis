package net.igsoft.tablevis

import kotlin.math.max

class CellDef<STYLE : Style>(private val style: STYLE) {
    var width: Int? = null
    var height: Int? = null

    var minimalTextWidth = style.minimalTextWidth

    var leftMargin = style.leftMargin
    var topMargin = style.topMargin
    var rightMargin = style.rightMargin
    var bottomMargin = style.bottomMargin

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

    var ids: List<Any> = emptyList()
    private var lines: List<String> = emptyList()
    private var horizontalAlignment = style.horizontalAlignment
    private var verticalAlignment = style.verticalAlignment
    internal var naturalTextWidth: Int = 0
    internal var naturalWidth = 0
    internal var minimalWidth = 0
    internal var textWidth = 0

    internal fun resolveTexts(cells: MutableMap<Any, MutableSet<CellDef<STYLE>>>) {
        ids.forEach {
            val cellSet = cells.getOrPut(it) { mutableSetOf() }
            cellSet.add(this)
        }

        if (text.isEmpty()) {
            lines = listOf()
            naturalTextWidth = 0
        } else {
            lines = Text.resolveTabs(text).lines()
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
