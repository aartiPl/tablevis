package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.CellDef
import net.igsoft.tablevis.Style

class RowProperties<STYLE: Style>(style: STYLE): CommonProperties<STYLE>(style) {
    val cells = mutableListOf<CellDef<STYLE>>()
}
