package net.igsoft.tablevis.text

import net.igsoft.tablevis.HorizontalAlignment
import net.igsoft.tablevis.Section
import net.igsoft.tablevis.VerticalAlignment

class SimpleTextTableStyle(
    override val lineSeparator: String = System.lineSeparator(),

    override val leftMargin: Int = 1,
    override val topMargin: Int = 0,
    override val rightMargin: Int = 1,
    override val bottomMagin: Int = 0,

    override val verticalAlignment: VerticalAlignment = VerticalAlignment.Middle,
    override val horizontalAlignment: HorizontalAlignment = HorizontalAlignment.Left,

    headerSectionStyle: TextSectionStyle = TextSectionStyle("=~=", "*", 100),
    rowSectionStyle: TextSectionStyle = TextSectionStyle("-", "|", 50),
    footerSectionStyle: TextSectionStyle = TextSectionStyle("~", "|", 75),
) : TextTableStyle {
    override fun resolveCrossSection(value: IntersectionMatrix): Char {
        return '+'
    }

    override val sections: Map<Section, TextSectionStyle> = mapOf(
        Section.Header to headerSectionStyle, Section.Row to rowSectionStyle, Section.Footer to footerSectionStyle
    )
}
