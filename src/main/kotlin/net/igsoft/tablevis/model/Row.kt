package net.igsoft.tablevis.model

import net.igsoft.tablevis.style.Style

data class Row(val width: Int, val height: Int, val style: Style, val cells: List<Cell>)
