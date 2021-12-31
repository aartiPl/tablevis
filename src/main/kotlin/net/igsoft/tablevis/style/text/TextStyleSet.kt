package net.igsoft.tablevis.style.text

import net.igsoft.tablevis.style.StyleSet

interface TextStyleSet<STYLE : TextStyle> : StyleSet<STYLE> {
    fun resolveCrossSection(value: IntersectionMatrix): Char
    val lineSeparator: String
}
