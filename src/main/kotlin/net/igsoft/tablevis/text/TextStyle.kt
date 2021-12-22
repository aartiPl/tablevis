package net.igsoft.tablevis.text

import net.igsoft.tablevis.Style

data class TextStyle(
    val horizontalLine: String,
    val verticalLine: String,
    override val layer: Int,
) : Style {
    override val horizontalLineWidth: Int get() = horizontalLine.length
    override val horizontalLineHeight: Int get() = 1
    override val verticalLineWidth: Int get() = verticalLine.length
    override val verticalLineHeight: Int get() = 1
}
