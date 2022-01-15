package net.igsoft.tablevis.builder

import net.igsoft.tablevis.style.Style

class GlobalOperation<STYLE : Style>(
    private val globalOperations: MutableSet<(TableProperties<STYLE>) -> Unit>,
    private val cellOperations: MutableMap<Any, MutableSet<(Set<CellProperties<STYLE>>) -> Unit>>
) {
    fun syncColumns() = apply {
        set { tableProperties ->
            val maxCells = tableProperties.rows.maxOf { it.properties.cells.size }
            if (maxCells > 0) {
                IdOperation(generateIds(maxCells), cellOperations).setMinimalWidth()
            }
        }
    }

    private fun set(fn: (TableProperties<STYLE>) -> Unit) {
        globalOperations.add(fn)
    }

    companion object {
        internal fun generateIds(maxCount: Int): List<String> {
            val result = mutableListOf<String>()

            for(counter in 1..maxCount) {
                result.add("col-$counter")
            }

            return result
        }
    }
}
