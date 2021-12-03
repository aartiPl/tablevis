package net.igsoft.tablevis

class Table<T : TableStyle> internal constructor(
    val style: T, val width: Int, val height: Int, val headers: List<Row>, val rows: List<Row>, val footers: List<Row>
) {
    companion object {
        fun <T : TableStyle> using(style: T, block: TableBuilder<T>.() -> Unit = {}) =
            TableBuilder(style).apply(block).build()
    }
}
