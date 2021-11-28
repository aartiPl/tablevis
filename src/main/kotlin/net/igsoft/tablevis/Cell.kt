package net.igsoft.tablevis

data class Cell(
    val width: Int,
    val height: Int,

    val leftIndent: Int,
    val topIndent: Int,
    val rightIndent: Int,
    val bottomIndent: Int,

    val horizontal: HorizontalAlignment,
    val vertical: VerticalAlignment,

    val lines: List<String>
)
