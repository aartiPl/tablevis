package net.igsoft.tablevis.builder

import net.igsoft.tablevis.model.HorizontalAlignment
import net.igsoft.tablevis.model.VerticalAlignment
import net.igsoft.tablevis.style.Border
import net.igsoft.tablevis.style.Style

class CommonStyle<STYLE : Style>(
    var baseStyle: STYLE,

    var leftMargin: Int, var topMargin: Int, var rightMargin: Int, var bottomMargin: Int,

    var horizontalAlignment: HorizontalAlignment, var verticalAlignment: VerticalAlignment,

    var minimalTextWidth: Int,

    var leftBorder: Border, var topBorder: Border, var rightBorder: Border, var bottomBorder: Border
) {
    constructor(style: STYLE) : this(
        style,
        style.leftMargin,
        style.topMargin,
        style.rightMargin,
        style.bottomMargin,
        style.horizontalAlignment,
        style.verticalAlignment,
        style.minimalTextWidth,
        style.leftBorder,
        style.topBorder,
        style.rightBorder,
        style.bottomBorder
    )

    constructor(commonStyle: CommonStyle<STYLE>) : this(
        commonStyle.baseStyle,
        commonStyle.leftMargin,
        commonStyle.topMargin,
        commonStyle.rightMargin,
        commonStyle.bottomMargin,
        commonStyle.horizontalAlignment,
        commonStyle.verticalAlignment,
        commonStyle.minimalTextWidth,
        commonStyle.leftBorder,
        commonStyle.topBorder,
        commonStyle.rightBorder,
        commonStyle.bottomBorder
    )
}
