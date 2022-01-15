package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.builder.CellProperties
import net.igsoft.tablevis.builder.RowProperties
import net.igsoft.tablevis.builder.TableProperties
import net.igsoft.tablevis.model.*
import net.igsoft.tablevis.style.Border
import net.igsoft.tablevis.style.Border.Companion.empty
import net.igsoft.tablevis.style.Style
import net.igsoft.tablevis.style.StyleSet
import java.util.*

class BuilderResolver<STYLE : Style, STYLE_SET : StyleSet<STYLE>>(private val styleSet: STYLE_SET) :
    Visitor<STYLE, Table<STYLE_SET>, List<HorizontalElement>, Cell<STYLE>> {
    private var upperLine = TreeMap<Int, Intersection>()
    private var lowerLine = TreeMap<Int, Intersection>()
    private var firstLine = true

    override fun visit(tableProperties: TableProperties<STYLE>): Table<STYLE_SET> {
        val horizontalElements = mutableListOf<HorizontalElement>()

        if (tableProperties.rows.isNotEmpty()) {
            for (rowDef in tableProperties.rows) {
                horizontalElements.addAll(rowDef.applyVisitor(this))
                firstLine = false
            }
            horizontalElements.add(toLine(upperLine))
        }

        return Table(styleSet, tableProperties.width!!, tableProperties.height!!, horizontalElements)
    }

    override fun visit(rowProperties: RowProperties<STYLE>): List<HorizontalElement> {
        val verticalElements = mutableListOf<VerticalElement>()

        var lastBorder: Border = empty
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
            resolveIntersection(upperLine, lowerLine, hPosition, leftBorderSize, cell, firstLine)

            verticalElements.add(leftSection)
            verticalElements.add(cell)

            hPosition += cell.width + leftBorderSize
            vPosition += cell.height
        }

        upperLine.lastEntry().value.right = empty //Last cell right intersection is always empty
        verticalElements.add(Section(lastCell!!.height, lastBorder))

        val line = toLine(upperLine)

        //Switch upperLine and lowerLine
        val helper = upperLine
        upperLine = lowerLine
        lowerLine = helper
        lowerLine.clear()

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

    private fun resolveStyle(border: Border, vararg borders: Border): Border {
        return listOf(border, *borders).maxByOrNull { it.elevation }!!
    }

    private fun resolveIntersection(
        upperLine: TreeMap<Int, Intersection>,
        lowerLine: TreeMap<Int, Intersection>,
        cellStartPosition: Int,
        leftBorderSize: Int,
        cell: Cell<STYLE>,
        firstLine: Boolean
    ) {
        val cellEndPosition = cellStartPosition + leftBorderSize + cell.width

        val leftTopIntersection = upperLine.getOrPut(cellStartPosition) { Intersection() }
        leftTopIntersection.right = resolveStyle(leftTopIntersection.right, cell.style.topBorder)
        leftTopIntersection.bottom = resolveStyle(leftTopIntersection.bottom, cell.style.leftBorder)

        val rightTopIntersection = upperLine.getOrPut(cellEndPosition) {
            if (firstLine) {
                //This is first upper line - just leave right intersection empty it will be filled with the next cell
                Intersection(matrix = arrayOf(leftTopIntersection.right, empty, empty, empty))
            } else {
                //We are splitting existing upper cell into parts - left and right border of intersection are same
                //as they were in previous intersection.
                //In case it is the last cell in row, right border will be replaced by empty Border later.
                val continuation = resolveStyle(leftTopIntersection.right, cell.style.topBorder)
                Intersection(matrix = arrayOf(leftTopIntersection.right, empty, continuation, empty))
            }
        }

        //We should also look if there were any other intersection and update its left/right values
        upperLine.filter { it.key in (cellStartPosition + 1) until cellEndPosition }.forEach { entry ->
            entry.value.left = resolveStyle(entry.value.left, leftTopIntersection.right)
            entry.value.right = resolveStyle(entry.value.right, leftTopIntersection.right)
        }

        rightTopIntersection.left = resolveStyle(rightTopIntersection.left, cell.style.topBorder)
        rightTopIntersection.bottom = resolveStyle(rightTopIntersection.bottom, cell.style.rightBorder)

        val leftBottomIntersection = lowerLine.getOrPut(cellStartPosition) { Intersection() }
        leftBottomIntersection.right = resolveStyle(leftBottomIntersection.right, cell.style.bottomBorder)
        leftBottomIntersection.top = resolveStyle(leftBottomIntersection.top, cell.style.leftBorder)

        val rightBottomIntersection = lowerLine.getOrPut(cellEndPosition) { Intersection() }
        rightBottomIntersection.left = resolveStyle(rightBottomIntersection.left, cell.style.bottomBorder)
        rightBottomIntersection.top = resolveStyle(rightBottomIntersection.top, cell.style.rightBorder)
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

            elements.add(Section(entry.key - lastEntry.key - lastEntry.value.right.size, lastEntry.value.right))
            elements.add(entry.value)
            lastEntry = entry
        }

        return Line(elements)
    }
}
