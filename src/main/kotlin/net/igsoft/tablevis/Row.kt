package net.igsoft.tablevis

data class Row(
    val width: Int, val height: Int, val style: SectionStyle, val cells: List<Cell>
)
