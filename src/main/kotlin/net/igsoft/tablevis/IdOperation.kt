package net.igsoft.tablevis

class IdOperation<T : TableStyle>(private val table: TableBuilder<T>, private val ids: List<Any>) {

    private val setMinimalWidthFn: (Set<CellBuilder<T>>) -> Unit = { cells ->
        {
//        var maxWidth = 0
//
//        for (cell  in cells) {
//            val currentWidth = Utils.maxLineSizeBasedOnText(cell.cellText)
//            maxWidth = if (currentWidth > maxWidth) currentWidth else maxWidth
//        }
//
//        cells.foreach(cell => cell . cellTextWidth = Some (maxWidth))
        }
    }

    fun setMinimalWidth(): IdOperation<T> = apply {
        ids.forEach {
            table.addOperation(it, setMinimalWidthFn)
        }
    }

    fun setHeight(height: Int) {
    }
}
