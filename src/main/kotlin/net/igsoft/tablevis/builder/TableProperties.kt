package net.igsoft.tablevis.builder

import net.igsoft.tablevis.style.Style

class TableProperties<STYLE : Style>(commonStyle: CommonStyle<STYLE>) : CommonProperties<STYLE>(commonStyle) {
    val globalOperations = mutableSetOf<(TableProperties<STYLE>) -> Unit>()
    val idOperations = mutableMapOf<Any, MutableSet<(Set<CellProperties<STYLE>>) -> Unit>>()
    val cellsPerId = mutableMapOf<Any, MutableSet<CellProperties<STYLE>>>()
    val rows = mutableListOf<RowDef<STYLE>>()
}
