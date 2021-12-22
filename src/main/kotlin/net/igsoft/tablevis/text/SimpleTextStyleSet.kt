package net.igsoft.tablevis.text

import net.igsoft.tablevis.HorizontalAlignment
import net.igsoft.tablevis.VerticalAlignment

class SimpleTextStyleSet(
    override val lineSeparator: String = System.lineSeparator(),

    override val leftMargin: Int = 1,
    override val topMargin: Int = 0,
    override val rightMargin: Int = 1,
    override val bottomMargin: Int = 0,

    override val verticalAlignment: VerticalAlignment = VerticalAlignment.Middle,
    override val horizontalAlignment: HorizontalAlignment = HorizontalAlignment.Left,

    val header: TextStyle = TextStyle("=~=", "*", 100),
    val row: TextStyle = TextStyle("-", "|", 50),
    val footer: TextStyle = TextStyle("~", "|", 75),
) : TextStyleSet<TextStyle> {
    override val baseStyle: TextStyle = row

    override fun resolveCrossSection(value: IntersectionMatrix): Char = '+'
}
