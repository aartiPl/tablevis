package net.igsoft.tablevis

class SimpleTextTableStyle(
    override val leftIndent: Int = 1,
    override val topIndent: Int = 0,
    override val rightIndent: Int = 1,
    override val bottomIndent: Int = 0,

    override val verticalAlignment: VerticalAlignment = VerticalAlignment.Middle,
    override val horizontalAlignment: HorizontalAlignment = HorizontalAlignment.Left,

    override val headerHorizontalLineChar: String = "=",
    override val horizontalLineChar: String = "-",
    override val verticalLineChar: String = "|",
    override val tableLineSeparator: String = System.lineSeparator()
) : TextTableStyle() {

}
