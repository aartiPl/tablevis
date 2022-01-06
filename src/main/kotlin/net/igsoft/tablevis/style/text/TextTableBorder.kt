package net.igsoft.tablevis.style.text

import net.igsoft.tablevis.style.Border

data class TextTableBorder(val line: String, override val size: Int, override val elevation: Int) : Border {
    companion object {
        val none = TextTableBorder(" ", 1, Int.MAX_VALUE)
    }
}
