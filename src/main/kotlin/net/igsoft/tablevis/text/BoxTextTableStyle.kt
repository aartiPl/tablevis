package net.igsoft.tablevis.text

import net.igsoft.tablevis.HorizontalAlignment
import net.igsoft.tablevis.Section
import net.igsoft.tablevis.VerticalAlignment

class BoxTextTableStyle(
    override val lineSeparator: String = System.lineSeparator(),

    override val leftIndent: Int = 1,
    override val topIndent: Int = 0,
    override val rightIndent: Int = 1,
    override val bottomIndent: Int = 0,

    override val verticalAlignment: VerticalAlignment = VerticalAlignment.Middle,
    override val horizontalAlignment: HorizontalAlignment = HorizontalAlignment.Left,

    headerSectionStyle: TextSectionStyle = TextSectionStyle("─", "│", 100),
    rowSectionStyle: TextSectionStyle = TextSectionStyle("─", "│", 50),
    footerSectionStyle: TextSectionStyle = TextSectionStyle("─", "│", 75),
) : TextTableStyle {
    private val map = mutableMapOf<String, Char>()

    init {
        //@formatter:off
        addEntry("   ",
                 " ┌─",
                 " │ ")

        addEntry("   ",
                 "─┐ ",
                 " │ ")

        addEntry(" │ ",
                 " ├─",
                 " │ ")

        addEntry(" │ ",
                 "─┤ ",
                 " │ ")

        addEntry("   ",
                 "─┬─",
                 " │ ")

        addEntry(" │ ",
                 "─┴─",
                 "   ")

        addEntry(" │ ",
                 "─┼─",
                 " │ ")

        addEntry(" │ ",
                 "─┘ ",
                 "   ")

        addEntry(" │ ",
                 " └─",
                 "   ")

        //@formatter:on
    }

    override fun resolveCrossSection(value: IntersectionMatrix): Char {
        return map[value.toString()] ?: '?'
    }

    override val sections: Map<Section, TextSectionStyle> = mapOf(
        Section.Header to headerSectionStyle, Section.Row to rowSectionStyle, Section.Footer to footerSectionStyle
    )

    private fun addEntry(r1: String, r2: String, r3: String) {
        val value = r2[1]
        val key = r1 + r2[0] + ' ' + r2[2] + r3
        map[key] = value
    }
}
