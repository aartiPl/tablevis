package net.igsoft.tablevis

abstract class TextTableStyle(
    override val leftIndent: Int = 1,
    override val topIndent: Int = 0,
    override val rightIndent: Int = 1,
    override val bottomIndent: Int = 0,

    override val verticalAlignment: VerticalAlignment = VerticalAlignment.Middle,
    override val horizontalAlignment: HorizontalAlignment = HorizontalAlignment.Left,

    open val headerHorizontalLineChar: String = "=",
    open val horizontalLineChar: String = "-",
    open val verticalLineChar: String = "|",
    open val tableLineSeparator: String = System.lineSeparator()
) : TableStyle {
    override val headerHorizontalLineMeasure = 1
    override val headerVerticalLineMeasure = 1
    override val rowHorizontalLineMeasure = 1
    override val rowVerticalLineMeasure = 1
    override val footerHorizontalLineMeasure = 1
    override val footerVerticalLineMeasure = 1
}
