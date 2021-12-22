package net.igsoft.tablevis

interface StyleSet<S: Style> {
    val leftMargin: Int
    val topMargin: Int
    val rightMargin: Int
    val bottomMargin: Int

    val verticalAlignment: VerticalAlignment
    val horizontalAlignment: HorizontalAlignment

    val baseStyle: S
}
