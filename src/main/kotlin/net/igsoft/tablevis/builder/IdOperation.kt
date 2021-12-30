package net.igsoft.tablevis.builder

import net.igsoft.tablevis.style.Style

class IdOperation<STYLE : Style>(
    private val ids: List<Any>, private val functions: MutableMap<Any, MutableSet<(Set<CellProperties<STYLE>>) -> Unit>>
) {
    private val setMinimalWidthFn: (Set<CellProperties<STYLE>>) -> Unit = { cells ->
        val maxWidth = cells.maxOf { it.naturalTextWidth }

        cells.forEach {
            it.width = it.leftMargin + maxWidth + it.rightMargin
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
                    it.width = it.leftMargin + width + it.rightMargin
                }
            }
        }
    }

    fun setHeight(height: Int): IdOperation<STYLE> = apply {
        ids.forEach { id ->
            val functionsSet = functions.getOrPut(id) { mutableSetOf() }
            functionsSet.add { cells ->
                cells.forEach {
                    it.height = it.topMargin + height + it.bottomMargin
                }
            }
        }
    }
}