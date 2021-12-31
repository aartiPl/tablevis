package net.igsoft.tablevis.style.text

import net.igsoft.tablevis.style.StyleSet

interface TextTableStyleSet<STYLE : TextTableStyle> : StyleSet<STYLE> {
    fun resolveCrossSection(value: IntersectionMatrix): Char
    val lineSeparator: String
}
