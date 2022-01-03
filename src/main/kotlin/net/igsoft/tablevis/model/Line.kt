package net.igsoft.tablevis.model

class Line(val elements: List<LineElement>) : HorizontalElement {
    var maxSize: Int = elements.maxOf {
        if (it is Section) {
            it.border.size
        } else {
            0
        }
    }
}
