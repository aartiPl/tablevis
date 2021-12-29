package net.igsoft.tablevis

class Table<STYLE_SET : StyleSet<out Style>> internal constructor(val styleSet: STYLE_SET, val width: Int, val height: Int, val rows: List<Row>) {
    companion object {
        fun <STYLE: Style, STYLE_SET : StyleSet<STYLE>> using(styleSet: STYLE_SET, block: TableBuilder<STYLE>.() -> Unit = {}): Table<STYLE_SET> {
            return Builder.build(styleSet, TableBuilder(styleSet.baseStyle).apply(block))
        }
    }
}
