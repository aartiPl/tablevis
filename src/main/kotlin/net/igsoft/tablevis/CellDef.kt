package net.igsoft.tablevis

import net.igsoft.tablevis.visitor.CellProperties
import net.igsoft.tablevis.visitor.Visitor
import kotlin.math.max

class CellDef<STYLE : Style>(private val style: STYLE) {
    private val properties = CellProperties()

//    var test: Int
//        get() = properties.naturalWidth
//        set(value) {
//            properties.naturalWidth = value
//        }

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

    internal fun resolveTexts() {
        if (text.isEmpty()) {
            lines = listOf()
            naturalTextWidth = 0
        } else {
            lines = Text.resolveTabs(text).lines()
            naturalTextWidth = lines.maxOf { it.length }
        }

        naturalWidth = leftMargin + naturalTextWidth + rightMargin
    }

    internal fun resolveWidth(imposedWidth: Boolean) {
        minimalWidth = width ?: (leftMargin + (if (text.isEmpty()) 0 else minimalTextWidth) + rightMargin)

        if (!imposedWidth) {
            width = width ?: naturalWidth
        }
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

    internal fun applyVisitor(visitor: Visitor<STYLE>) = visitor.visit(this, properties)
}
