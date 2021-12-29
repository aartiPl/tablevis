package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.marker.Marker
import net.igsoft.tablevis.marker.TypedMarker

@Suppress("UNCHECKED_CAST")
class TypedProperties {
    private val properties = mutableMapOf<Marker, Any>()

    operator fun <T> get(marker: TypedMarker<T>): T? = properties[marker] as T?

    fun <T> getValue(marker: TypedMarker<T>): T = properties.getValue(marker) as T

    operator fun <T> set(marker: TypedMarker<T>, value: T) {
        properties[marker] = value as Any
    }

    fun exists(marker: TypedMarker<*>): Boolean = properties.containsKey(marker)
}
