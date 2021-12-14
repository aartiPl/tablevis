package net.igsoft.tablevis

interface SectionStyle {
    val horizontalLineWidth: Int
    val horizontalLineHeight: Int
    val verticalLineWidth: Int
    val verticalLineHeight: Int
    val layer: Int
}

interface TableStyle {
    val leftMargin: Int
    val topMargin: Int
    val rightMargin: Int
    val bottomMargin: Int

    val verticalAlignment: VerticalAlignment
    val horizontalAlignment: HorizontalAlignment

    val sections: Map<Section, SectionStyle>
}
