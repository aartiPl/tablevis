package net.igsoft.tablevis.style.text

class SimpleTextTableStyleSet(
    override val lineSeparator: String = System.lineSeparator(),
    val header: TextTableStyle = TextTableStyle(
        verticalBorder = verticalHeavyBoxBorder,
        horizontalBorder = horizontalHeavyBoxBorder,
    ),
    val row: TextTableStyle = TextTableStyle(
        verticalBorder = verticalBoxBorder,
        horizontalBorder = horizontalBoxBorder,
    ),
    val footer: TextTableStyle = TextTableStyle(
        verticalBorder = verticalHeavyFooterBoxBorder,
        horizontalBorder = horizontalHeavyFooterBoxBorder,
    ),
) : TextTableStyleSet<TextTableStyle> {
    override val baseStyle: TextTableStyle = row
    override fun resolveCrossSection(value: IntersectionMatrix): Char = '+'

    companion object {
        val horizontalBoxBorder = TextTableBorder("-", 1, 50)
        val verticalBoxBorder = TextTableBorder("|", 1, 50)

        val horizontalHeavyBoxBorder = TextTableBorder("=~=", 1, 100)
        val verticalHeavyBoxBorder = TextTableBorder("*", 1, 100)

        val horizontalHeavyFooterBoxBorder = TextTableBorder("~", 1, 75)
        val verticalHeavyFooterBoxBorder = TextTableBorder("|", 1, 75)

        val noBorder = TextTableBorder("", 0, 500)
    }
}
