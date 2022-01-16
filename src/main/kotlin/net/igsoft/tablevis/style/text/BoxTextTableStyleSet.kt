package net.igsoft.tablevis.style.text

class BoxTextTableStyleSet(
    override val lineSeparator: String = System.lineSeparator(),
    override val skipTransparentBorders: Boolean = false,
    private val roundCorners: Boolean = false,

    val header: TextTableStyle = TextTableStyle(
        horizontalBorder = horizontalHeavyBorder,
        verticalBorder = verticalHeavyBorder,
    ),
    val row: TextTableStyle = TextTableStyle(
        horizontalBorder = horizontalLightBorder,
        verticalBorder = verticalLightBorder,
    ),
    val footer: TextTableStyle = TextTableStyle(
        horizontalBorder = horizontalQuadrupleHeavyDashedBorder,
        verticalBorder = verticalQuadrupleHeavyDashedBorder,
    ),
) : TextTableStyleSet<TextTableStyle> {
    override val baseStyle = row

    override fun resolveIntersection(value: String): Char {
        return BoxTextTableIntersection1.intersections[value]
            ?: (if (roundCorners) BoxTextTableIntersection1.roundedLightCornerIntersection[value] else BoxTextTableIntersection1.straightLightCornerIntersection[value])
            ?: '?'
    }

    companion object {
        // Light borders...
        val horizontalDoubleLightDashedBorder = TextTableBorder("╌", 1, 1000)
        val verticalDoubleLightDashedBorder = TextTableBorder("╎", 1, 1000)

        val horizontalTripleLightDashedBorder = TextTableBorder("┄", 1, 1100)
        val verticalTripleLightDashedBorder = TextTableBorder("┆", 1, 1100)

        val horizontalQuadrupleLightDashedBorder = TextTableBorder("┈", 1, 1200)
        val verticalQuadrupleLightDashedBorder = TextTableBorder("┊", 1, 1200)

        val horizontalLightBorder = TextTableBorder("─", 1, 1300)
        val verticalLightBorder = TextTableBorder("│", 1, 1300)

        // Heavy borders...
        val horizontalDoubleHeavyDashedBorder = TextTableBorder("╍", 1, 1400)
        val verticalDoubleHeavyDashedBorder = TextTableBorder("╏", 1, 1400)

        val horizontalTripleHeavyDashedBorder = TextTableBorder("┅", 1, 1500)
        val verticalTripleHeavyDashedBorder = TextTableBorder("┇", 1, 1500)

        val horizontalQuadrupleHeavyDashedBorder = TextTableBorder("┉", 1, 1600)
        val verticalQuadrupleHeavyDashedBorder = TextTableBorder("┋", 1, 1600)

        val horizontalHeavyBorder = TextTableBorder("━", 1, 1700)
        val verticalHeavyBorder = TextTableBorder("┃", 1, 1700)

        val horizontalDoubleBorder = TextTableBorder("═", 1, 1800)
        val verticalDoubleBorder = TextTableBorder("║", 1, 1800)


    }
}
