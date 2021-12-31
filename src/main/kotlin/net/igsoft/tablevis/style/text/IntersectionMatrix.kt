package net.igsoft.tablevis.style.text

@JvmInline
value class IntersectionMatrix(private val matrix: CharArray = charArrayOf(' ', ' ', ' ', ' ')) {
    fun setLeft(char: Char) = apply { matrix[0] = char }
    fun setTop(char: Char) = apply { matrix[1] = char }
    fun setRight(char: Char) = apply { matrix[2] = char }
    fun setBottom(char: Char) = apply { matrix[3] = char }

    override fun toString(): String {
        return String(matrix)
    }
}
