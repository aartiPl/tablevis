package net.igsoft.tablevis
/*
class RowDef<S: Style, T : StyleSet<S>>(internal val tableDef: TableDef<S, T>, private val style: S) {
    private val cells = mutableListOf<CellDef<S, T>>()
    private var width: Int? = null
    private var height: Int? = null

    var minimalTextWidth = tableDef.minimalTextWidth

    var leftMargin: Int = tableDef.leftMargin
    var topMargin: Int = tableDef.topMargin
    var rightMargin: Int = tableDef.rightMargin
    var bottomMargin: Int = tableDef.bottomMargin

    fun cell(block: CellBuilder<S, T>.() -> Unit = {}) {
        cells.add(CellBuilder(this).apply(block))
    }

    internal var verticalAlignment: VerticalAlignment = style.verticalAlignment
    internal var horizontalAlignment: HorizontalAlignment = style.horizontalAlignment

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
}
*/
