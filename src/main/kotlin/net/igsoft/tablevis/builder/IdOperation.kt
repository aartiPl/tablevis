package net.igsoft.tablevis.builder

import net.igsoft.tablevis.model.HorizontalAlignment
import net.igsoft.tablevis.model.VerticalAlignment
import net.igsoft.tablevis.style.Style

class IdOperation<STYLE : Style>(
    private val ids: List<Any>, private val functions: MutableMap<Any, MutableSet<(Set<CellProperties<STYLE>>) -> Unit>>
) {
    private val setMinimalWidthFn: (Set<CellProperties<STYLE>>) -> Unit = { cells ->
        val maxWidth = cells.maxOf { it.naturalTextWidth }

        cells.forEach {
            it.width = it.commonStyle.leftMargin + maxWidth + it.commonStyle.rightMargin
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
                    it.width = it.commonStyle.leftMargin + width + it.commonStyle.rightMargin
                }
            }
        }
    }

    fun setHeight(height: Int): IdOperation<STYLE> = apply {
        ids.forEach { id ->
            val functionsSet = functions.getOrPut(id) { mutableSetOf() }
            functionsSet.add { cells ->
                cells.forEach {
                    it.height = it.commonStyle.topMargin + height + it.commonStyle.bottomMargin
                }
            }
        }
    }

    fun center() {
        ids.forEach {id ->
            val functionsSet = functions.getOrPut(id) { mutableSetOf() }
            functionsSet.add { cells ->
                cells.forEach {
                    it.commonStyle.horizontalAlignment = HorizontalAlignment.Center
                }
            }
        }
    }

    fun left() {
        ids.forEach {id ->
            val functionsSet = functions.getOrPut(id) { mutableSetOf() }
            functionsSet.add { cells ->
                cells.forEach {
                    it.commonStyle.horizontalAlignment = HorizontalAlignment.Left
                }
            }
        }
    }

    fun right() {
        ids.forEach {id ->
            val functionsSet = functions.getOrPut(id) { mutableSetOf() }
            functionsSet.add { cells ->
                cells.forEach {
                    it.commonStyle.horizontalAlignment = HorizontalAlignment.Right
                }
            }
        }
    }

    fun justify() {
        ids.forEach {id ->
            val functionsSet = functions.getOrPut(id) { mutableSetOf() }
            functionsSet.add { cells ->
                cells.forEach {
                    it.commonStyle.horizontalAlignment = HorizontalAlignment.Justified
                }
            }
        }
    }

    fun top() {
        ids.forEach {id ->
            val functionsSet = functions.getOrPut(id) { mutableSetOf() }
            functionsSet.add { cells ->
                cells.forEach {
                    it.commonStyle.verticalAlignment = VerticalAlignment.Top
                }
            }
        }
    }

    fun middle() {
        ids.forEach {id ->
            val functionsSet = functions.getOrPut(id) { mutableSetOf() }
            functionsSet.add { cells ->
                cells.forEach {
                    it.commonStyle.verticalAlignment = VerticalAlignment.Middle
                }
            }
        }
    }

    fun bottom() {
        ids.forEach {id ->
            val functionsSet = functions.getOrPut(id) { mutableSetOf() }
            functionsSet.add { cells ->
                cells.forEach {
                    it.commonStyle.verticalAlignment = VerticalAlignment.Bottom
                }
            }
        }
    }
}
