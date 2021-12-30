package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.RowDef
import net.igsoft.tablevis.Style

class TableProperties<STYLE: Style>(style: STYLE): CommonProperties<STYLE>(style) {
    val rows = mutableListOf<RowDef<STYLE>>()
}
