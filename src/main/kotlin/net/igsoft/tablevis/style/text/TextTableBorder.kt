package net.igsoft.tablevis.style.text

import net.igsoft.tablevis.style.Border

data class TextTableBorder(val line: String, override val size: Int, override val elevation: Int) : Border()
