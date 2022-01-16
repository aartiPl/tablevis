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
        val chars = intersection.matrix.joinToString(separator = "") { resolveBorder(it) }

        return BoxTextTableIntersection1.intersections[chars]
            ?: (if (roundCorners) BoxTextTableIntersection1.roundedLightCornerIntersection[chars] else BoxTextTableIntersection1.straightLightCornerIntersection[chars])
            ?: '?'
    }

    private fun resolveBorder(border: Border): String {
        return if (border == Border.empty) {
            " "
        } else {
            (border as TextTableBorder).line
        }
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
