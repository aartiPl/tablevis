package net.igsoft.tablevis.builder

import net.igsoft.tablevis.style.Style

class TableProperties<STYLE: Style>(style: STYLE): CommonProperties<STYLE>(style) {
    val functions = mutableMapOf<Any, MutableSet<(Set<CellDef<STYLE>>) -> Unit>>()
    val rows = mutableListOf<RowDef<STYLE>>()
}
