package net.igsoft.tablevis.model

data class Cell(
    val width: Int,
    val height: Int,

    val leftIndent: Int,
    val topIndent: Int,
    val rightIndent: Int,
    val bottomIndent: Int,

    val horizontalAlignment: HorizontalAlignment,
    val verticalAlignment: VerticalAlignment,

    val lines: List<String>
)
