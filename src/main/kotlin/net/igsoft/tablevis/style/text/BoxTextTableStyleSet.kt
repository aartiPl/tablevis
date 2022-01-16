package net.igsoft.tablevis.style.text

import net.igsoft.tablevis.model.Intersection
import net.igsoft.tablevis.style.Border

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

    override fun resolveIntersection(intersection: Intersection): Char {
        val chars =
            intersection.matrix.joinToString(separator = "") { if (it == Border.empty) " " else (it as TextTableBorder).borderStyle }

        return BoxTextTableIntersection.intersections[chars]
            ?: (if (roundCorners) BoxTextTableIntersection.roundedLightCornerIntersection[chars] else BoxTextTableIntersection.straightLightCornerIntersection[chars])
            ?: '?'
    }

    companion object {
        // Light borders...
        val horizontalDoubleLightDashedBorder = TextTableBorder("╌", "L", 1, 1000)
        val verticalDoubleLightDashedBorder = TextTableBorder("╎", "L", 1, 1000)

        val horizontalTripleLightDashedBorder = TextTableBorder("┄", "L", 1, 1100)
        val verticalTripleLightDashedBorder = TextTableBorder("┆", "L", 1, 1100)

        val horizontalQuadrupleLightDashedBorder = TextTableBorder("┈", "L", 1, 1200)
        val verticalQuadrupleLightDashedBorder = TextTableBorder("┊", "L", 1, 1200)

        val horizontalLightBorder = TextTableBorder("─", "L", 1, 1300)
        val verticalLightBorder = TextTableBorder("│", "L", 1, 1300)

        // Heavy borders...
        val horizontalDoubleHeavyDashedBorder = TextTableBorder("╍", "H", 1, 1400)
        val verticalDoubleHeavyDashedBorder = TextTableBorder("╏", "H", 1, 1400)

        val horizontalTripleHeavyDashedBorder = TextTableBorder("┅", "H", 1, 1500)
        val verticalTripleHeavyDashedBorder = TextTableBorder("┇", "H", 1, 1500)

        val horizontalQuadrupleHeavyDashedBorder = TextTableBorder("┉", "H", 1, 1600)
        val verticalQuadrupleHeavyDashedBorder = TextTableBorder("┋", "H", 1, 1600)

        val horizontalHeavyBorder = TextTableBorder("━", "H", 1, 1700)
        val verticalHeavyBorder = TextTableBorder("┃", "H", 1, 1700)

        val horizontalDoubleBorder = TextTableBorder("═", "D", 1, 1800)
        val verticalDoubleBorder = TextTableBorder("║", "D", 1, 1800)
    }
}
