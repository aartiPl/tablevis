package net.igsoft.tablevis

import kotlin.math.max
/*
class TableDef<STYLE : Style, STYLE_SET : StyleSet<STYLE>>(private val styleSet: STYLE_SET) {
    private val rows = mutableListOf<RowDef<STYLE, STYLE_SET>>()

    var minimalTextWidth = 1

    var width: Int? = null
    var height: Int? = null

    var leftMargin: Int = styleSet.leftMargin
    var topMargin: Int = styleSet.topMargin
    var rightMargin: Int = styleSet.rightMargin
    var bottomMargin: Int = styleSet.bottomMargin

    internal var verticalAlignment: VerticalAlignment = styleSet.verticalAlignment
    internal var horizontalAlignment: HorizontalAlignment = styleSet.horizontalAlignment

    fun row(style: STYLE = styleSet.baseStyle, block: RowBuilder<STYLE, STYLE_SET>.() -> Unit = {}) {
        rows.add(RowDef(this, style).apply(block))
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

    fun forId(vararg id: Any): IdOperation<STYLE, STYLE_SET> = IdOperation(this, id.toList())
}
*/
