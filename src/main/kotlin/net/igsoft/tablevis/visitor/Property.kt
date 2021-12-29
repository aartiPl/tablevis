package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.marker.TypedMarker

object Property {
    val leftMargin = TypedMarker<Int>("leftMargin")
    val rightMargin = TypedMarker<Int>("rightMargin")
    val text = TypedMarker<String>("text")
    val lines = TypedMarker<List<String>>("lines")
    val width = TypedMarker<Int>("width")
    val height = TypedMarker<Int>("width")

    val minimalTextWidth = TypedMarker<Int>("minimalTextWidth")
    val naturalTextWidth = TypedMarker<Int>("naturalTextWidth")
    val naturalWidth = TypedMarker<Int>("naturalWidth")
}
