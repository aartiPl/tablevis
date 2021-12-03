package net.igsoft.tablevis

data class TextSectionStyle(
    val horizontalLine: String,
    val verticalLine: String,
    val leftTopCorner: String,
    val rightTopCorner: String,
    val rightBottomCorner: String,
    val leftBottomCorner: String,
) : SectionStyle {
    override val horizontalLineWidth: Int get() = horizontalLine.length
    override val horizontalLineHeight: Int get() = 1
    override val verticalLineWidth: Int get() = verticalLine.length
    override val verticalLineHeight: Int get() = 1
}

interface TextTableStyle : TableStyle {
    val lineSeparator: String

    override val headerSectionStyle: TextSectionStyle
    override val rowSectionStyle: TextSectionStyle
    override val footerSectionStyle: TextSectionStyle
}
