package net.igsoft.tablevis.builder

import net.igsoft.tablevis.style.Style

class RowProperties<STYLE: Style>(commonStyle: CommonStyle<STYLE>): CommonProperties<STYLE>(commonStyle) {
    val cells = mutableListOf<CellDef<STYLE>>()
    var cellsWithNoWidth: MutableList<CellProperties<STYLE>> = mutableListOf()
}
