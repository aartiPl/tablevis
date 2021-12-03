package net.igsoft.tablevis

interface SectionStyle {
    val horizontalLineWidth: Int
    val horizontalLineHeight: Int
    val verticalLineWidth: Int
    val verticalLineHeight: Int
}

interface TableStyle {
    val leftIndent: Int
    val topIndent: Int
    val rightIndent: Int
    val bottomIndent: Int

    val vertical: Vertical
    val horizontal: Horizontal

    val headerSectionStyle: SectionStyle
    val rowSectionStyle: SectionStyle
    val footerSectionStyle: SectionStyle
}
