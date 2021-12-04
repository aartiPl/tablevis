package net.igsoft.tablevis

class Table<T : TableStyle> internal constructor(val style: T, val width: Int, val height: Int, val rows: List<Row>) {
    companion object {
        fun <T : TableStyle> using(style: T, block: TableBuilder<T>.() -> Unit = {}) =
            TableBuilder(style).apply(block).build()
    }
}
