package net.igsoft.tablevis.style

class BoxTextStyleSet(
    override val lineSeparator: String = System.lineSeparator(),

    val header: TextStyle = TextStyle("━", "┃", 100),
    val row: TextStyle = TextStyle("─", "│", 50),
    val footer: TextStyle = TextStyle("━", "┃", 75),
) : TextStyleSet<TextStyle> {
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
    }

    override fun resolveCrossSection(value: IntersectionMatrix): Char {
        return intersections[value.toString()] ?: '?'
    }
}
