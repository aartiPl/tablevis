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

    val verticalAlignment: VerticalAlignment
    val horizontalAlignment: HorizontalAlignment

    val headerSectionStyle: SectionStyle
    val rowSectionStyle: SectionStyle
    val footerSectionStyle: SectionStyle
}
