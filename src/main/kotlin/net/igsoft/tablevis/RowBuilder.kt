package net.igsoft.tablevis

class RowBuilder<T : TableStyle>(internal val tableBuilder: TableBuilder<T>) {
    internal val cells = mutableListOf<CellBuilder<T>>()

    var width: Int? = null
    var height: Int? = null

    var leftIndent: Int = tableBuilder.leftIndent
    var topIndent: Int = tableBuilder.topIndent
    var rightIndent: Int = tableBuilder.rightIndent
    var bottomIndent: Int = tableBuilder.bottomIndent
    var verticalAlignment: VerticalAlignment = tableBuilder.verticalAlignment
    var horizontalAlignment: HorizontalAlignment = tableBuilder.horizontalAlignment

    fun addCell(block: CellBuilder<T>.() -> Unit = {}) {
        cells.add(CellBuilder(this).apply(block))
    }

    fun build(): Row {
        return Row(width!!, height!!, cells.map { it.build() })
    }
}
