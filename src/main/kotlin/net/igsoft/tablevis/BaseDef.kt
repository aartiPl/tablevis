package net.igsoft.tablevis

import net.igsoft.tablevis.visitor.CommonProperties

open class BaseDef<STYLE : Style, PROPERTIES: CommonProperties<STYLE>>(val properties: PROPERTIES, protected val style: STYLE) {
    var leftMargin: Int
        get() = properties.leftMargin
        set(value) {
            properties.leftMargin = value
        }

    var topMargin: Int
        get() = properties.topMargin
        set(value) {
            properties.topMargin = value
        }

    var rightMargin: Int
        get() = properties.rightMargin
        set(value) {
            properties.rightMargin = value
        }

    var bottomMargin: Int
        get() = properties.bottomMargin
        set(value) {
            properties.bottomMargin = value
        }

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

    fun alignCenter() = apply {
        properties.horizontalAlignment = HorizontalAlignment.Center
    }

    fun alignLeft() = apply {
        properties.horizontalAlignment = HorizontalAlignment.Left
    }

    fun alignRight() = apply {
        properties.horizontalAlignment = HorizontalAlignment.Right
    }

    fun justify() = apply {
        properties.horizontalAlignment = HorizontalAlignment.Justified
    }

    fun alignTop() = apply {
        properties.verticalAlignment = VerticalAlignment.Top
    }

    fun alignMiddle() = apply {
        properties.verticalAlignment = VerticalAlignment.Middle
    }

    fun alignBottom() = apply {
        properties.verticalAlignment = VerticalAlignment.Bottom
    }
}
