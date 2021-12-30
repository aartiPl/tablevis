package net.igsoft.tablevis.builder

import net.igsoft.tablevis.style.Style

class RowProperties<STYLE: Style>(style: STYLE): CommonProperties<STYLE>(style) {
    val cells = mutableListOf<CellDef<STYLE>>()
    var cellsWithNoWidth: MutableList<CellProperties<STYLE>> = mutableListOf()
}
