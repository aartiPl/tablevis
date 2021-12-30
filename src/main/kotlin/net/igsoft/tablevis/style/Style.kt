package net.igsoft.tablevis.style

import net.igsoft.tablevis.model.HorizontalAlignment
import net.igsoft.tablevis.model.VerticalAlignment

interface Style {
    val layer: Int

    val leftMargin: Int
    val topMargin: Int
    val rightMargin: Int
    val bottomMargin: Int

    val verticalAlignment: VerticalAlignment
    val horizontalAlignment: HorizontalAlignment

    val minimalTextWidth: Int

    val horizontalLineWidth: Int
    val horizontalLineHeight: Int
    val verticalLineWidth: Int
    val verticalLineHeight: Int
}
