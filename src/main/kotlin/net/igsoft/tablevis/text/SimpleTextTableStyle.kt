package net.igsoft.tablevis.text

import net.igsoft.tablevis.HorizontalAlignment
import net.igsoft.tablevis.VerticalAlignment

class SimpleTextTableStyle(
    override val lineSeparator: String = System.lineSeparator(),

    override val leftIndent: Int = 1,
    override val topIndent: Int = 0,
    override val rightIndent: Int = 1,
    override val bottomIndent: Int = 0,

    override val verticalAlignment: VerticalAlignment = VerticalAlignment.Middle,
    override val horizontalAlignment: HorizontalAlignment = HorizontalAlignment.Left,

    override val headerSectionStyle: TextSectionStyle = TextSectionStyle("=", "*", "", "", "", ""),
    override val rowSectionStyle: TextSectionStyle = TextSectionStyle("-", "|", "", "", "", ""),
    override val footerSectionStyle: TextSectionStyle = TextSectionStyle("~", "|", "", "", "", ""),
) : TextTableStyle
