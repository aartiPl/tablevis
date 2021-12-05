package net.igsoft.tablevis.text

import net.igsoft.tablevis.Section
import net.igsoft.tablevis.SectionStyle
import net.igsoft.tablevis.TableStyle

data class TextSectionStyle(
    val horizontalLine: String,
    val verticalLine: String,
    override val layer: Int,
) : SectionStyle {
    override val horizontalLineWidth: Int get() = horizontalLine.length
    override val horizontalLineHeight: Int get() = 1
    override val verticalLineWidth: Int get() = verticalLine.length
    override val verticalLineHeight: Int get() = 1
}

interface TextTableStyle : TableStyle {
    fun resolveCrossSection(value: IntersectionMatrix): Char
    val lineSeparator: String
    override val sections: Map<Section, TextSectionStyle>
}
