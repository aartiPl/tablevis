package net.igsoft.tablevis

import kotlin.math.max

class TableBuilder<STYLE : Style>(val style: STYLE) {
    val functions = mutableMapOf<Any, MutableSet<(Set<CellBuilder<STYLE>>) -> Unit>>()

    val rows = mutableListOf<RowBuilder<STYLE>>()

    var minimalTextWidth = style.minimalTextWidth

    var width: Int? = null
    var height: Int? = null

    var leftMargin: Int = style.leftMargin
    var topMargin: Int = style.topMargin
    var rightMargin: Int = style.rightMargin
    var bottomMargin: Int = style.bottomMargin

    fun row(rowStyle: STYLE = style, block: RowBuilder<STYLE>.() -> Unit = {}) {
        rows.add(RowBuilder(rowStyle).apply(block))
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

    fun forId(vararg id: Any): IdOperation<STYLE> = IdOperation(id.toList(), functions)

    internal var verticalAlignment: VerticalAlignment = style.verticalAlignment
    internal var horizontalAlignment: HorizontalAlignment = style.horizontalAlignment
}
