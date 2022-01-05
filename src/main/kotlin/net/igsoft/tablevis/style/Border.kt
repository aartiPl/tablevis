package net.igsoft.tablevis.style

interface Border {
    val size: Int
    val elevation: Int

    companion object {
        val empty = object : Border {
            override val size: Int = 0
            override val elevation: Int = 0
        }
    }
}
