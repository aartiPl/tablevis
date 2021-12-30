package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.HorizontalAlignment
import net.igsoft.tablevis.Style
import net.igsoft.tablevis.VerticalAlignment

abstract class CommonProperties<STYLE: Style>(val style: STYLE) {
    var leftMargin: Int = style.leftMargin
    var topMargin: Int = style.topMargin
    var rightMargin: Int = style.rightMargin
    var bottomMargin: Int = style.bottomMargin

    var horizontalAlignment: HorizontalAlignment = style.horizontalAlignment
    var verticalAlignment: VerticalAlignment = style.verticalAlignment

    var naturalWidth: Int = 0

    var width: Int? = null
    var height: Int? = null
}
