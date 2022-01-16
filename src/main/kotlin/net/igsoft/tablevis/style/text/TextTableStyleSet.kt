package net.igsoft.tablevis.style.text

import net.igsoft.tablevis.model.Intersection
import net.igsoft.tablevis.style.StyleSet

interface TextTableStyleSet<STYLE : TextTableStyle> : StyleSet<STYLE> {
    fun resolveIntersection(intersection: Intersection): Char
    val lineSeparator: String
    val skipTransparentBorders: Boolean
}
