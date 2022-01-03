package net.igsoft.tablevis.style.text

class NoBorderTextTableStyleSet(
    override val lineSeparator: String = System.lineSeparator(),

    val row: TextTableStyle = TextTableStyle(
        verticalBorder = verticalNoBorder,
        horizontalBorder = horizontalNoBorder,
    )
) : TextTableStyleSet<TextTableStyle> {
    override val baseStyle = row

    override fun resolveIntersection(value: String): Char {
        return ' '
    }

    companion object {
        val horizontalNoBorder = TextTableBorder(" ", 0, 50)
        val verticalNoBorder = TextTableBorder(" ", 0, 50)
    }
}
