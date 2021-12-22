package net.igsoft.tablevis.text

import net.igsoft.tablevis.StyleSet

interface TextStyleSet<T : TextStyle> : StyleSet<T> {
    fun resolveCrossSection(value: IntersectionMatrix): Char
    val lineSeparator: String
}
