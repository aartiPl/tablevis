package net.igsoft.tablevis.style.text

class BoxTextTableStyleSet(
    override val lineSeparator: String = System.lineSeparator(),

    val header: TextTableStyle = TextTableStyle(
        verticalBorder = verticalHeavyBoxBorder,
        horizontalBorder = horizontalHeavyBoxBorder,
    ),
    val row: TextTableStyle = TextTableStyle(
        verticalBorder = verticalBoxBorder,
        horizontalBorder = horizontalBoxBorder,
    ),
    val footer: TextTableStyle = TextTableStyle(
        verticalBorder = verticalHeavyFooterBoxBorder,
        horizontalBorder = horizontalHeavyFooterBoxBorder,
    ),
) : TextTableStyleSet<TextTableStyle> {
    override val baseStyle = row

    //Encoding of intersections:
    //  .T.             T - Top Char
    //  LIR             L - Left Char, I - Intersection Char, R - Right Char
    //  .B.             B - Bottom Char
    //Intersections are encoded as follows: LTRB -> I
    private val intersections = buildMap<String, Char> {
        //   LTRB    I
        put("  ─│", '┌')
        put("─  │", '┐')
        put(" │─│", '├')
        put("─│ │", '┤')
        put("─ ─│", '┬')
        put("─│─ ", '┴')
        put("─│─│", '┼')
        put("─│  ", '┘')
        put(" │─ ", '└')

        put("  ━┃", '┏')
        put("━  ┃", '┓')
        put(" ┃━┃", '┣')
        put("━┃ ┃", '┫')
        put("━ ━┃", '┳')
        put("━┃━ ", '┻')
        put("━┃━┃", '╋')
        put("━┃  ", '┛')
        put(" ┃━ ", '┗')

        put("  ═║", '╔')
        put("═  ║", '╗')
        put(" ║═║", '╠')
        put("═║ ║", '╣')
        put("═ ═║", '╦')
        put("═║═ ", '╩')
        put("═║═║", '╬')
        put("═║  ", '╝')
        put(" ║═ ", '╚')

        put(" ║═│", '╠')
        put("═║ │", '╣')
        put("═ ═│", '╤')

        put(" │━┃", '┢')
        put(" ┃━│", '┡')
        put("━┃ │", '┩')
        put("━│━ ", '┷')
        put("━ ━│", '┯')
        put("━│ ┃", '┪')
        put("━┃━│", '╇')
        put("━│━┃", '╈')
        put(" ┃─│", '┞')
    }

    override fun resolveIntersection(value: String): Char {
        return intersections[value] ?: '?'
    }

    companion object {
        val horizontalBoxBorder = TextTableBorder("─", 1, 50)
        val verticalBoxBorder = TextTableBorder("│", 1, 50)

        val horizontalHeavyBoxBorder = TextTableBorder("━", 1, 100)
        val verticalHeavyBoxBorder = TextTableBorder("┃", 1, 100)

        val horizontalHeavyFooterBoxBorder = TextTableBorder("━", 1, 75)
        val verticalHeavyFooterBoxBorder = TextTableBorder("┃", 1, 75)
    }
}
