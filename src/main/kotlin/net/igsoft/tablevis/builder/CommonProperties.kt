package net.igsoft.tablevis.builder

import net.igsoft.tablevis.model.HorizontalAlignment
import net.igsoft.tablevis.model.VerticalAlignment
import net.igsoft.tablevis.style.Style

abstract class CommonProperties<STYLE : Style>(val style: STYLE) {
    var leftMargin: Int = style.leftMargin
    var topMargin: Int = style.topMargin
    var rightMargin: Int = style.rightMargin
    var bottomMargin: Int = style.bottomMargin

    var horizontalAlignment: HorizontalAlignment = style.horizontalAlignment
    var verticalAlignment: VerticalAlignment = style.verticalAlignment

    var minimalTextWidth: Int = style.minimalTextWidth

    var minimalWidth: Int = 0
    var naturalWidth: Int = 0
    var assignedWidth: Int = 0

    var width: Int? = null
    var height: Int? = null
}
