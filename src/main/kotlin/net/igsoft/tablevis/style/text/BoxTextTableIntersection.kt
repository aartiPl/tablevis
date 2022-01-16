package net.igsoft.tablevis.style.text

object BoxTextTableIntersection {
    //Encoding of intersections:
    //  .T.             T - Top Char
    //  LIR             L - Left Char, I - Intersection Char, R - Right Char
    //  .B.             B - Bottom Char
    //Intersections are encoded as follows: LTRB -> I

    //@formatter:off
    //   LTRB    I
    val straightLightCornerIntersection = buildMap {
        put(" LL ", '└')
        put("L  L", '┐')
        put("  LL", '┌')
        put("LL  ", '┘')
    }

    //   LTRB    I
    val roundedLightCornerIntersection = buildMap {
        put(" LL ", '╰')
        put("L  L", '╮')
        put("  LL", '╭')
        put("LL  ", '╯')
    }

    //   LTRB    I
    val intersections = buildMap {
        // ******************************************* Homogenous style ********************************************
        //Light intersections (without corners, which are defined above)
        put(" LLL", '├')
        put("LL L", '┤')
        put("L LL", '┬')
        put("LLL ", '┴')
        put("LLLL", '┼')

        //Heavy intersections
        put("  HH", '┏')
        put("H  H", '┓')
        put("HH  ", '┛')
        put(" HH ", '┗')
        put(" HHH", '┣')
        put("HH H", '┫')
        put("H HH", '┳')
        put("HHH ", '┻')
        put("HHHH", '╋')

        //Double intersections
        put("  DD", '╔')
        put("D  D", '╗')
        put(" DD ", '╚')
        put("DD  ", '╝')
        put(" DDD", '╠')
        put("DD D", '╣')
        put("D DD", '╦')
        put("DDD ", '╩')
        put("DDDD", '╬')

        // ********************************************* Mixed style ***********************************************
        //None intersections
        put("L   ", ' ')
        put("H   ", ' ')
        put(" L  ", ' ')
        put(" H  ", ' ')
        put("  L ", ' ')
        put("  H ", ' ')
        put("   L", ' ')
        put("   H", ' ')
        put("L   ", ' ')
        put("H   ", ' ')
        put(" L  ", ' ')
        put(" H  ", ' ')
        put("  L ", ' ')
        put("  H ", ' ')
        put("   L", ' ')
        put("   H", ' ')
        put("L L ", '─')
        put("H H ", '─')
        put(" L L", '│')
        put(" H H", '│')
        put("    ", ' ')

        //Mixed intersections (heavy and light)
        put(" HL ", '┖')
        put(" LH ", '┕')
        put("LH  ", '┚')
        put("HL  ", '┙')
        put("H  L", '┑')
        put("L  H", '┒')
        put("  HL", '┍')
        put("  LH", '┎')
        put(" LHH", '┢')
        put(" HHL", '┡')
        put(" HLL", '┞')
        put(" LLH", '┟')
        put("HH L", '┩')
        put("HL H", '┪')
        put("LH L", '┦')
        put("LL H", '┧')
        put("HLH ", '┷')
        put("LHL ", '┸')
        put("LHH ", '┺')
        put("HHL ", '┹')
        put("H HL", '┯')
        put("H LL", '┭')
        put("H LH", '┱')
        put("L HH", '┲')
        put("H L ", '╾')
        put("L H ", '╼')
        put(" H L", '╿')
        put(" L H", '╽')
        put("HHHL", '╇')
        put("HLHH", '╈')
        put("HHLH", '╉')
        put("LHHH", '╊')
        put("LHHL", '╄')
        put("LLHH", '╆')
        put("HLLH", '╅')
        put("HHLL", '╃')

        //Double light and heavy
        put(" DL ", '╙'); put(" DH ", '╙')
        put("LD  ", '╜'); put("HD  ", '╜')
        put("  LD", '╓'); put("  HD", '╓')
        put("L  D", '╖'); put("H  D", '╖')

        put(" LD ", '╘'); put(" HD ", '╘')
        put("DL  ", '╛'); put("DH  ", '╛')
        put("  DL", '╒'); put("  DH", '╒')
        put("D  L", '╕'); put("D  H", '╕')

        put("DLD ", '╧'); put("DHD ", '╧')
        put("D DL", '╤'); put("D DH", '╤')
        put(" DLD", '╟'); put(" DHD", '╟')
        put("LD D", '╢'); put("HD D", '╢')

        put("DL L", '╡'); put("DH H", '╡')
        put(" LDL", '╞'); put(" HDH", '╞')
        put("LDL ", '╨'); put("HDH ", '╨')
        put("L LD", '╥'); put("H HD", '╥')

        put("DLDL", '╪'); put("DHDH", '╪')
        put("LDLD", '╫'); put("HDHD", '╫')

        put("DLDD", '╬'); put("DHDD", '╬')
        put("DDDL", '╬'); put("DDDH", '╬')
    }
    //@formatter:on
}
