package net.igsoft.tablevis.builder

import net.igsoft.tablevis.style.Style

class CellProperties<STYLE : Style>(style: STYLE) : CommonProperties<STYLE>(style) {
    var text: String = ""
    var lines: List<String> = emptyList()

    var naturalTextWidth: Int = 0
    var textWidth = 0

    var ids: List<Any> = emptyList()
}
