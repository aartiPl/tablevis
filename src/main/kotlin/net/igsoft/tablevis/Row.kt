package net.igsoft.tablevis

data class Row(
    val width: Int, val height: Int, val style: Style, val cells: List<Cell>
)
