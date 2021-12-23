package net.igsoft.tablevis

class Table<S: Style, T : StyleSet<S>> internal constructor(val style: T, val width: Int, val height: Int, val rows: List<Row>) {
    companion object {
        fun <S: Style, T : StyleSet<S>> using(style: T, block: TableBuilder<S, T>.() -> Unit = {}): Table<S, T> {
            return TableBuilder(style).apply(block).build()
        }
    }
}
