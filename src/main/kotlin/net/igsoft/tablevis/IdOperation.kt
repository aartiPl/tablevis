package net.igsoft.tablevis

class IdOperation<S : Style, T : StyleSet<S>>(private val table: TableBuilder<S, T>, private val ids: List<Any>) {
    private val setMinimalWidthFn: (Set<CellBuilder<S, T>>) -> Unit = { cells ->
        val maxWidth = cells.maxOf { it.naturalTextWidth }

        cells.forEach {
            it.width = it.leftMargin + maxWidth + it.rightMargin
        }
    }

    fun setMinimalWidth(): IdOperation<S, T> = apply {
        ids.forEach { table.addOperation(it, setMinimalWidthFn) }
    }

    fun setWidth(width: Int): IdOperation<S, T> = apply {
        ids.forEach { id ->
            table.addOperation(id) { cells ->
                cells.forEach {
                    it.width = it.leftMargin + width + it.rightMargin
                }
            }
        }
    }

    fun setHeight(height: Int): IdOperation<S, T> = apply {
        ids.forEach { id ->
            table.addOperation(id) { cells ->
                cells.forEach {
                    it.height = it.topMargin + height + it.bottomMargin
                }
            }
        }
    }
}
