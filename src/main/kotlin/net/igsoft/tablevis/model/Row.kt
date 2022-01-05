package net.igsoft.tablevis.model

import net.igsoft.tablevis.builder.CommonStyle
import net.igsoft.tablevis.style.Style

data class Row<STYLE : Style>(
    val style: CommonStyle<STYLE>, val width: Int, val height: Int, val verticalElements: List<VerticalElement>
) : HorizontalElement
