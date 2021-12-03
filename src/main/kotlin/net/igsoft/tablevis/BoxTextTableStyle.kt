package net.igsoft.tablevis

class BoxTextTableStyle(
    override val lineSeparator: String = System.lineSeparator(),

    override val leftIndent: Int = 1,
    override val topIndent: Int = 0,
    override val rightIndent: Int = 1,
    override val bottomIndent: Int = 0,

    override val vertical: Vertical = Vertical.Middle,
    override val horizontal: Horizontal = Horizontal.Left,

    override val headerSectionStyle: TextSectionStyle = TextSectionStyle("─", "│", "", "┐", "", ""),
    override val rowSectionStyle: TextSectionStyle = TextSectionStyle("─", "│", "", "┐", "", ""),
    override val footerSectionStyle: TextSectionStyle = TextSectionStyle("─", "│", "", "┐", "", ""),
) : TextTableStyle
