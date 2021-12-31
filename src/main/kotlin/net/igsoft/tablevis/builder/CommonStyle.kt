package net.igsoft.tablevis.builder

import net.igsoft.tablevis.model.HorizontalAlignment
import net.igsoft.tablevis.model.VerticalAlignment
import net.igsoft.tablevis.style.Style

class CommonStyle<STYLE : Style>(
    var baseStyle: STYLE,
    var layer: Int,
    var leftMargin: Int,
    var topMargin: Int,
    var rightMargin: Int,
    var bottomMargin: Int,

    var horizontalAlignment: HorizontalAlignment,
    var verticalAlignment: VerticalAlignment,

    var minimalTextWidth: Int,

    var horizontalLineWidth: Int,
    var horizontalLineHeight: Int,
    var verticalLineWidth: Int,
    var verticalLineHeight: Int
) {
    constructor(style: STYLE) : this(
        style,
        style.layer,
        style.leftMargin,
        style.topMargin,
        style.rightMargin,
        style.bottomMargin,
        style.horizontalAlignment,
        style.verticalAlignment,
        style.minimalTextWidth,
        style.horizontalLineWidth,
        style.horizontalLineHeight,
        style.verticalLineWidth,
        style.verticalLineHeight
    )

    constructor(commonStyle: CommonStyle<STYLE>) : this(
        commonStyle.baseStyle,
        commonStyle.layer,
        commonStyle.leftMargin,
        commonStyle.topMargin,
        commonStyle.rightMargin,
        commonStyle.bottomMargin,
        commonStyle.horizontalAlignment,
        commonStyle.verticalAlignment,
        commonStyle.minimalTextWidth,
        commonStyle.horizontalLineWidth,
        commonStyle.horizontalLineHeight,
        commonStyle.verticalLineWidth,
        commonStyle.verticalLineHeight
    )
}
