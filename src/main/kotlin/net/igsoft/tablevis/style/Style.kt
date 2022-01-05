package net.igsoft.tablevis.style

import net.igsoft.tablevis.model.HorizontalAlignment
import net.igsoft.tablevis.model.VerticalAlignment

interface Style {
    val leftBorder: Border
    val topBorder: Border
    val rightBorder: Border
    val bottomBorder: Border

    val leftMargin: Int
    val topMargin: Int
    val rightMargin: Int
    val bottomMargin: Int

    val verticalAlignment: VerticalAlignment
    val horizontalAlignment: HorizontalAlignment

    val minimalTextWidth: Int
}
