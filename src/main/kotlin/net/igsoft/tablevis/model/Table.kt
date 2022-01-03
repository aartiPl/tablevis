package net.igsoft.tablevis.model

import net.igsoft.tablevis.style.Style
import net.igsoft.tablevis.style.StyleSet

class Table<STYLE_SET : StyleSet<out Style>> internal constructor(
    val styleSet: STYLE_SET,
    val width: Int,
    val height: Int,
    val horizontalElements: List<HorizontalElement>
)
