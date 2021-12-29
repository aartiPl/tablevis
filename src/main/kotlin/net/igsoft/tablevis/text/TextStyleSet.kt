package net.igsoft.tablevis.text

import net.igsoft.tablevis.StyleSet

interface TextStyleSet<STYLE : TextStyle> : StyleSet<STYLE> {
    fun resolveCrossSection(value: IntersectionMatrix): Char
    val lineSeparator: String
}
