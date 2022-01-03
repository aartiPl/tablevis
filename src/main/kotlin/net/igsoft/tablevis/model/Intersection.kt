package net.igsoft.tablevis.model

import net.igsoft.tablevis.style.Border
import net.igsoft.tablevis.style.StyleSet.Companion.empty

data class Intersection(val matrix: Array<Border> = arrayOf(empty, empty, empty, empty)) : LineElement {
    var left: Border
        get() = matrix[0]
        set(value) {
            matrix[0] = value
        }

    var top: Border
        get() = matrix[1]
        set(value) {
            matrix[1] = value
        }

    var right: Border
        get() = matrix[2]
        set(value) {
            matrix[2] = value
        }

    var bottom: Border
        get() = matrix[3]
        set(value) {
            matrix[3] = value
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Intersection
        if (!matrix.contentEquals(other.matrix)) return false

        return true
    }

    override fun hashCode(): Int {
        return matrix.contentHashCode()
    }
}
