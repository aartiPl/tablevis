package net.igsoft.tablevis.style.text

import net.igsoft.tablevis.model.Intersection

class NoBorderTextTableStyleSet(
    override val lineSeparator: String = System.lineSeparator(),
    override val skipTransparentBorders: Boolean = true,

    val row: TextTableStyle = TextTableStyle(
        verticalBorder = verticalNoBorder,
        horizontalBorder = horizontalNoBorder,
    )
) : TextTableStyleSet<TextTableStyle> {
    override val baseStyle = row

    override fun resolveIntersection(intersection: Intersection): Char {
        return ' '
    }

    companion object {
        val horizontalNoBorder = TextTableBorder(" ", 0, 50)
        val verticalNoBorder = TextTableBorder(" ", 0, 50)
    }
}
