package net.igsoft.tablevis

import net.igsoft.tablevis.visitor.TypedProperties
import net.igsoft.tablevis.visitor.Visitor

class TableDef<STYLE : Style>(val style: STYLE) {
    private val properties = TypedProperties()

    val functions = mutableMapOf<Any, MutableSet<(Set<CellDef<STYLE>>) -> Unit>>()

    val rows = mutableListOf<RowDef<STYLE>>()

    var minimalTextWidth = style.minimalTextWidth

    var width: Int? = null
    var height: Int? = null

    var leftMargin: Int = style.leftMargin
    var topMargin: Int = style.topMargin
    var rightMargin: Int = style.rightMargin
    var bottomMargin: Int = style.bottomMargin

    fun row(rowStyle: STYLE = style, block: RowDef<STYLE>.() -> Unit = {}) {
        rows.add(RowDef(rowStyle).apply(block))
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

    var verticalAlignment: VerticalAlignment = style.verticalAlignment
    var horizontalAlignment: HorizontalAlignment = style.horizontalAlignment

    internal fun applyVisitor(visitor: Visitor<STYLE>) = visitor.visit(this, properties)
}
