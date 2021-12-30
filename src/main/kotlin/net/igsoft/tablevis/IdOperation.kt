package net.igsoft.tablevis

class IdOperation<STYLE : Style>(
    private val ids: List<Any>, private val functions: MutableMap<Any, MutableSet<(Set<CellDef<STYLE>>) -> Unit>>
) {
    private val setMinimalWidthFn: (Set<CellDef<STYLE>>) -> Unit = { cells ->
        val maxWidth = cells.maxOf { it.properties.naturalTextWidth }

        cells.forEach {
            it.properties.width = it.leftMargin + maxWidth + it.rightMargin
        }
    }

    fun setMinimalWidth(): IdOperation<STYLE> = apply {
        ids.forEach {id ->
            val functionsSet = functions.getOrPut(id) { mutableSetOf() }
            functionsSet.add(setMinimalWidthFn)
        }
    }

    fun setWidth(width: Int): IdOperation<STYLE> = apply {
        ids.forEach { id ->
            val functionsSet = functions.getOrPut(id) { mutableSetOf() }
            functionsSet.add { cells ->
                cells.forEach {
                    it.properties.width = it.leftMargin + width + it.rightMargin
                }
            }
        }
    }

    fun setHeight(height: Int): IdOperation<STYLE> = apply {
        ids.forEach { id ->
            val functionsSet = functions.getOrPut(id) { mutableSetOf() }
            functionsSet.add { cells ->
                cells.forEach {
                    it.properties.height = it.topMargin + height + it.bottomMargin
                }
            }
        }
    }
}
