package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.CellDef
import net.igsoft.tablevis.RowDef
import net.igsoft.tablevis.Style

class TableProperties<STYLE: Style>(style: STYLE): CommonProperties<STYLE>(style) {
    val functions = mutableMapOf<Any, MutableSet<(Set<CellDef<STYLE>>) -> Unit>>()
    val rows = mutableListOf<RowDef<STYLE>>()
}
