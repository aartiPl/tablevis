package net.igsoft.tablevis

interface TableStyle {
    val leftIndent: Int
    val topIndent: Int
    val rightIndent: Int
    val bottomIndent: Int

    val verticalAlignment: VerticalAlignment
    val horizontalAlignment: HorizontalAlignment

    val headerHorizontalLineMeasure: Int
    val headerVerticalLineMeasure: Int

    val rowHorizontalLineMeasure: Int
    val rowVerticalLineMeasure: Int

    val footerHorizontalLineMeasure: Int
    val footerVerticalLineMeasure: Int
}
