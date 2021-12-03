package net.igsoft.tablevis

class RowBuilder<T : TableStyle>(internal val tableBuilder: TableBuilder<T>, internal val style: SectionStyle) {
    internal val cells = mutableListOf<CellBuilder<T>>()

    var height: Int? = null

    var leftIndent: Int = tableBuilder.leftIndent
    var topIndent: Int = tableBuilder.topIndent
    var rightIndent: Int = tableBuilder.rightIndent
    var bottomIndent: Int = tableBuilder.bottomIndent
    var vertical: Vertical = tableBuilder.vertical
    var horizontal: Horizontal = tableBuilder.horizontal

    fun addCell(block: CellBuilder<T>.() -> Unit = {}) {
        cells.add(CellBuilder(this).apply(block))
    }

    fun build(): Row {
        return Row(0, 0, cells.map { it.build() })
    }
}
