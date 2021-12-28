package net.igsoft.tablevis.text

import net.igsoft.tablevis.HorizontalAlignment
import net.igsoft.tablevis.Style
import net.igsoft.tablevis.VerticalAlignment

data class TextStyle(
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
}
