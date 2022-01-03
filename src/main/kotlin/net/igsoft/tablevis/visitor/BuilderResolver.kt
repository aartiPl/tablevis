package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.builder.CellDef
import net.igsoft.tablevis.builder.CellProperties
import net.igsoft.tablevis.builder.RowProperties
import net.igsoft.tablevis.builder.TableProperties
import net.igsoft.tablevis.model.*
import net.igsoft.tablevis.style.Border
import net.igsoft.tablevis.style.Style
import net.igsoft.tablevis.style.StyleSet
import java.util.*

class BuilderResolver<STYLE : Style, STYLE_SET : StyleSet<STYLE>>(private val styleSet: STYLE_SET) :
    Visitor<STYLE, Table<STYLE_SET>, List<HorizontalElement>, Cell<STYLE>> {

    private var upperLine = TreeMap<Int, Intersection>()

    override fun visit(tableProperties: TableProperties<STYLE>): Table<STYLE_SET> {
        val horizontalElements = mutableListOf<HorizontalElement>()

        if (tableProperties.rows.isNotEmpty()) {
            for (rowDef in tableProperties.rows) {
                horizontalElements.addAll(rowDef.applyVisitor(this))
            }
            horizontalElements.add(toLine(upperLine))
        }

        return Table(styleSet, tableProperties.width!!, tableProperties.height!!, horizontalElements)
    }

    override fun visit(rowProperties: RowProperties<STYLE>): List<HorizontalElement> {
        val lowerLine = TreeMap<Int, Intersection>()

        val verticalElements = mutableListOf<VerticalElement>()

        var lastBorder: Border? = null
        var lastCell: Cell<STYLE>? = null
        var leftSection: Section

        var hPosition = 0
        var vPosition = 0

        for (cellDef in rowProperties.cells) {
            val cell = cellDef.applyVisitor(this)

            leftSection = Section(cell.height, resolveStyle(lastBorder, cell.style.leftBorder))
            lastBorder = cell.style.rightBorder
            lastCell = cell

            val leftBorderSize = leftSection.border.size
            resolveIntersection(upperLine, lowerLine, hPosition, leftBorderSize, cell)

            verticalElements.add(leftSection)
            verticalElements.add(cell)

            hPosition += cell.width + leftBorderSize
            vPosition += cell.height
        }

        verticalElements.add(Section(lastCell!!.height, resolveStyle(lastBorder, lastCell.style.leftBorder)))

        val line = toLine(upperLine)
        upperLine = lowerLine

        return listOf(
            line,
            Row(rowProperties.commonStyle, rowProperties.width!!, rowProperties.height!!, verticalElements),
        )
    }

    override fun visit(cellProperties: CellProperties<STYLE>): Cell<STYLE> {
        return Cell(
            cellProperties.commonStyle, cellProperties.width!!, cellProperties.height!!, cellProperties.lines
        )
    }

    private fun resolveStyle(previousBorder: Border?, border: Border?): Border {
        require(previousBorder != null || border != null)

        if (previousBorder == null) return border!!
        if (border == null) return previousBorder

        return if (previousBorder.elevation > border.elevation) previousBorder else border
    }

    private fun resolveIntersection(
        upperLine: TreeMap<Int, Intersection>,
        lowerLine: TreeMap<Int, Intersection>,
        position: Int,
        leftBorderSize: Int,
        cell: Cell<STYLE>
    ) {
        val lastUpperIntersectionBorder = if (upperLine.isNotEmpty()) upperLine.lastEntry().value.right else Border.empty
        var upperIntersection = upperLine.getOrPut(position) { Intersection(matrix = arrayOf(lastUpperIntersectionBorder, Border.empty, lastUpperIntersectionBorder, Border.empty)) }
        upperIntersection.right = resolveStyle(upperIntersection.right, cell.style.topBorder)
        upperIntersection.bottom = resolveStyle(upperIntersection.bottom, cell.style.leftBorder)
        upperIntersection = upperLine.getOrPut(position + leftBorderSize + cell.width) { Intersection() }
        upperIntersection.left = resolveStyle(upperIntersection.left, cell.style.topBorder)
        upperIntersection.bottom = resolveStyle(upperIntersection.bottom, cell.style.rightBorder)

        val lastLowerIntersectionBorder = if (upperLine.isNotEmpty()) upperLine.lastEntry().value.right else Border.empty
        var lowerIntersection = lowerLine.getOrPut(position) { Intersection(matrix = arrayOf(lastLowerIntersectionBorder, Border.empty, lastLowerIntersectionBorder, Border.empty)) }
        lowerIntersection.right = resolveStyle(lowerIntersection.right, cell.style.bottomBorder)
        lowerIntersection.top = resolveStyle(lowerIntersection.top, cell.style.leftBorder)
        lowerIntersection = lowerLine.getOrPut(position + leftBorderSize + cell.width) { Intersection() }
        lowerIntersection.left = resolveStyle(lowerIntersection.left, cell.style.bottomBorder)
        lowerIntersection.top = resolveStyle(lowerIntersection.top, cell.style.rightBorder)
    }

    private fun toLine(tree: TreeMap<Int, Intersection>): Line {
        val elements = mutableListOf<LineElement>()

        var lastEntry: MutableMap.MutableEntry<Int, Intersection>? = null

        for (entry in tree.entries) {
            if (lastEntry == null) {
                elements.add(entry.value)
                lastEntry = entry
                continue
            }

            //TODO: what is intersection size? Is it really 1? Probably we should not calculate it here, but just copy from cell width
            elements.add(Section(entry.key - lastEntry.key - 1, lastEntry.value.right))
            elements.add(entry.value)
            lastEntry = entry
        }

        return Line(elements)
    }
}
