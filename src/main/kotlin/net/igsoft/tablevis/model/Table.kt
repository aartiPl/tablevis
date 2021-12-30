package net.igsoft.tablevis.model

import net.igsoft.tablevis.Creator
import net.igsoft.tablevis.style.Style
import net.igsoft.tablevis.style.StyleSet
import net.igsoft.tablevis.builder.TableDef

class Table<STYLE_SET : StyleSet<out Style>> internal constructor(val styleSet: STYLE_SET, val width: Int, val height: Int, val rows: List<Row>) {
    companion object {
        fun <STYLE: Style, STYLE_SET : StyleSet<STYLE>> using(styleSet: STYLE_SET, block: TableDef<STYLE>.() -> Unit = {}): Table<STYLE_SET> {
            return Creator.create(styleSet, TableDef(styleSet.baseStyle).apply(block))
        }
    }
}
