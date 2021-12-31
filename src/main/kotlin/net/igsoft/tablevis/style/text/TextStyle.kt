package net.igsoft.tablevis.style.text

import net.igsoft.tablevis.model.HorizontalAlignment
import net.igsoft.tablevis.model.VerticalAlignment
import net.igsoft.tablevis.style.Style
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.apache.commons.lang3.builder.ToStringBuilder

open class TextStyle(
    val horizontalLine: String,
    val verticalLine: String,
    override val layer: Int,
    override val leftMargin: Int = 1,
    override val topMargin: Int = 0,
    override val rightMargin: Int = 1,
    override val bottomMargin: Int = 0,
    override val verticalAlignment: VerticalAlignment = VerticalAlignment.Middle,
    override val horizontalAlignment: HorizontalAlignment = HorizontalAlignment.Left,
    override val minimalTextWidth: Int = 1
) : Style {
    override val horizontalLineWidth: Int get() = horizontalLine.length
    override val horizontalLineHeight: Int get() = 1
    override val verticalLineWidth: Int get() = verticalLine.length
    override val verticalLineHeight: Int get() = 1

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
