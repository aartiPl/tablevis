package net.igsoft.tablevis.style

interface TextStyleSet<STYLE : TextStyle> : StyleSet<STYLE> {
    fun resolveCrossSection(value: IntersectionMatrix): Char
    val lineSeparator: String
}
