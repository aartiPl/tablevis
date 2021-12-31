package net.igsoft.tablevis.style.text

class SimpleTextStyleSet(
    override val lineSeparator: String = System.lineSeparator(),
    val header: TextStyle = TextStyle("=~=", "*", 100),
    val row: TextStyle = TextStyle("-", "|", 50),
    val footer: TextStyle = TextStyle("~", "|", 75),
) : TextStyleSet<TextStyle> {
    override val baseStyle: TextStyle = row
    override fun resolveCrossSection(value: IntersectionMatrix): Char = '+'
}
