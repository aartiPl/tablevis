package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.Style

class CellProperties<STYLE : Style>(style: STYLE) : CommonProperties<STYLE>(style) {
    var text: String = ""
    var lines: List<String> = emptyList()

    var naturalTextWidth: Int = 0
    var minimalTextWidth: Int = 0
}
