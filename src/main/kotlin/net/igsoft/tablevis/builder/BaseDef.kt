package net.igsoft.tablevis.builder

import net.igsoft.tablevis.model.HorizontalAlignment
import net.igsoft.tablevis.model.VerticalAlignment
import net.igsoft.tablevis.style.Border
import net.igsoft.tablevis.style.Style

open class BaseDef<STYLE : Style, PROPERTIES : CommonProperties<STYLE>>(val properties: PROPERTIES) {
    var width: Int?
        get() = properties.width
        set(value) {
            properties.width = value
        }

    var height: Int?
        get() = properties.height
        set(value) {
            properties.height = value
        }

    var leftMargin: Int
        get() = properties.commonStyle.leftMargin
        set(value) {
            properties.commonStyle.leftMargin = value
        }

    var topMargin: Int
        get() = properties.commonStyle.topMargin
        set(value) {
            properties.commonStyle.topMargin = value
        }

    var rightMargin: Int
        get() = properties.commonStyle.rightMargin
        set(value) {
            properties.commonStyle.rightMargin = value
        }

    var bottomMargin: Int
        get() = properties.commonStyle.bottomMargin
        set(value) {
            properties.commonStyle.bottomMargin = value
        }

    var leftBorder: Border
        get() = properties.commonStyle.leftBorder
        set(value) {
            properties.commonStyle.leftBorder = value
        }

    var topBorder: Border
        get() = properties.commonStyle.topBorder
        set(value) {
            properties.commonStyle.topBorder = value
        }

    var rightBorder: Border
        get() = properties.commonStyle.rightBorder
        set(value) {
            properties.commonStyle.rightBorder = value
        }

    var bottomBorder: Border
        get() = properties.commonStyle.bottomBorder
        set(value) {
            properties.commonStyle.bottomBorder = value
        }

    fun center() = apply {
        properties.commonStyle.horizontalAlignment = HorizontalAlignment.Center
    }

    fun left() = apply {
        properties.commonStyle.horizontalAlignment = HorizontalAlignment.Left
    }

    fun right() = apply {
        properties.commonStyle.horizontalAlignment = HorizontalAlignment.Right
    }

    fun justify() = apply {
        properties.commonStyle.horizontalAlignment = HorizontalAlignment.Justified
    }

    fun top() = apply {
        properties.commonStyle.verticalAlignment = VerticalAlignment.Top
    }

    fun middle() = apply {
        properties.commonStyle.verticalAlignment = VerticalAlignment.Middle
    }

    fun bottom() = apply {
        properties.commonStyle.verticalAlignment = VerticalAlignment.Bottom
    }
}
