package net.igsoft.tablevis

class Table<STYLE_SET : StyleSet<out Style>> internal constructor(val styleSet: STYLE_SET, val width: Int, val height: Int, val rows: List<Row>) {
    companion object {
        fun <STYLE: Style, STYLE_SET : StyleSet<STYLE>> using(style: STYLE_SET, block: TableBuilder<STYLE, STYLE_SET>.() -> Unit = {}): Table<STYLE_SET> {
            return TableBuilder(style).apply(block).build()
        }
    }
}
