package net.igsoft.tablevis.builder

import net.igsoft.tablevis.style.Style

abstract class CommonProperties<STYLE : Style>(commonStyle: CommonStyle<STYLE>) {
    var commonStyle = CommonStyle(commonStyle)

    var minimalWidth: Int = 0
    var naturalWidth: Int = 0
    var assignedWidth: Int = 0

    var width: Int? = null
    var height: Int? = null
}
