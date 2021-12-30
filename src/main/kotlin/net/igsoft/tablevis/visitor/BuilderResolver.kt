package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.builder.CellProperties
import net.igsoft.tablevis.builder.RowProperties
import net.igsoft.tablevis.builder.TableProperties
import net.igsoft.tablevis.model.Cell
import net.igsoft.tablevis.model.Row
import net.igsoft.tablevis.model.Table
import net.igsoft.tablevis.style.Style
import net.igsoft.tablevis.style.StyleSet

class BuilderResolver<STYLE : Style, STYLE_SET : StyleSet<STYLE>>(private val styleSet: STYLE_SET) :
    Visitor<STYLE, Table<STYLE_SET>, Row, Cell> {
    override fun visit(tableProperties: TableProperties<STYLE>): Table<STYLE_SET> {
        return Table(styleSet,
                     tableProperties.width!!,
                     tableProperties.height!!,
                     tableProperties.rows.map { it.applyVisitor(this) })
    }

    override fun visit(rowProperties: RowProperties<STYLE>): Row {
        return Row(rowProperties.width!!,
                   rowProperties.height!!,
                   rowProperties.style,
                   rowProperties.cells.map { it.applyVisitor(this) })
    }

    // Only cell can be resolved in first turn - other natural sizes depends on later stages of processing
    override fun visit(cellProperties: CellProperties<STYLE>): Cell {
        return Cell(
            cellProperties.width!!,
            cellProperties.height!!,
            cellProperties.leftMargin,
            cellProperties.topMargin,
            cellProperties.rightMargin,
            cellProperties.bottomMargin,
            cellProperties.horizontalAlignment,
            cellProperties.verticalAlignment,
            cellProperties.lines
        )
    }
}
