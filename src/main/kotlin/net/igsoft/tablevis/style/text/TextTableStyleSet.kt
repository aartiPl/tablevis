package net.igsoft.tablevis.style.text

import net.igsoft.tablevis.style.StyleSet

interface TextTableStyleSet<STYLE : TextTableStyle> : StyleSet<STYLE> {
    fun resolveIntersection(value: String): Char
    val lineSeparator: String
    val skipTransparentBorders: Boolean
}
