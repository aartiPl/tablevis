package net.igsoft.tablevis.style.text

import net.igsoft.tablevis.style.Border

// bordersStyle: L-light, H-heavy, D-double, other character is taken without interpretation
data class TextTableBorder(val line: String, val borderStyle: String, override val size: Int, override val elevation: Int) : Border {
    companion object {
        val none = TextTableBorder(" ", " ", 1, Int.MAX_VALUE)
    }
}
