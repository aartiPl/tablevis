package net.igsoft.tablevis.text

@JvmInline
value class IntersectionMatrix(private val matrix: CharArray = charArrayOf(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',)) {


    fun set(x: Int, y: Int, c: Char) = apply{
        require(x in 0..2 && y in 0..2)
        matrix[x + y * 3] = c
    }

    fun setRow(y: Int, c: Char) = apply{
        require(y in 0..2)
        for (i in 0..2) {
            set(i, y, c)
        }
    }

    fun setCol(x: Int, c: Char) = apply{
        require(x in 0..2)
        for (i in 0..2) {
            set(x, i, c)
        }
    }

    override fun toString(): String {
        return String(matrix)
    }
}
