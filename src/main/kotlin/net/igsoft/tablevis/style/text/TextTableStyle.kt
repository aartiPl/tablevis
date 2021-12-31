package net.igsoft.tablevis.style.text

import net.igsoft.tablevis.model.HorizontalAlignment
import net.igsoft.tablevis.model.VerticalAlignment
import net.igsoft.tablevis.style.Style
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.apache.commons.lang3.builder.ToStringBuilder

open class TextTableStyle(
    override val leftBorder: TextTableBorder,
    override val topBorder: TextTableBorder,
    override val rightBorder: TextTableBorder,
    override val bottomBorder: TextTableBorder,

    override val leftMargin: Int = 1,
    override val topMargin: Int = 0,
    override val rightMargin: Int = 1,
    override val bottomMargin: Int = 0,

    override val verticalAlignment: VerticalAlignment = VerticalAlignment.Middle,
    override val horizontalAlignment: HorizontalAlignment = HorizontalAlignment.Left,

    override val minimalTextWidth: Int = 1
) : Style {
    constructor(
        horizontalBorder: TextTableBorder,
        verticalBorder: TextTableBorder,

        leftMargin: Int = 1,
        topMargin: Int = 0,
        rightMargin: Int = 1,
        bottomMargin: Int = 0,

        verticalAlignment: VerticalAlignment = VerticalAlignment.Middle,
        horizontalAlignment: HorizontalAlignment = HorizontalAlignment.Left,

        minimalTextWidth: Int = 1
    ) : this(
        verticalBorder,
        horizontalBorder,
        verticalBorder,
        horizontalBorder,
        leftMargin,
        topMargin,
        rightMargin,
        bottomMargin,
        verticalAlignment,
        horizontalAlignment,
        minimalTextWidth
    )

    override fun equals(other: Any?): Boolean {
        return EqualsBuilder.reflectionEquals(this, other)
    }

    override fun hashCode(): Int {
        return HashCodeBuilder.reflectionHashCode(this)
    }

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this)
    }
}
