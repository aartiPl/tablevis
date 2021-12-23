package net.igsoft.tablevis

interface Style {
    val horizontalLineWidth: Int
    val horizontalLineHeight: Int
    val verticalLineWidth: Int
    val verticalLineHeight: Int

    val layer: Int
    val leftMargin: Int
    val topMargin: Int
    val rightMargin: Int
    val bottomMargin: Int

    val verticalAlignment: VerticalAlignment
    val horizontalAlignment: HorizontalAlignment
}
