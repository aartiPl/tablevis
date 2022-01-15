package net.igsoft.tablevis

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isInstanceOf
import net.igsoft.tablevis.printer.text.TextTablePrinter
import net.igsoft.tablevis.style.text.BoxTextTableStyleSet
import net.igsoft.tablevis.style.text.NoBorderTextTableStyleSet
import net.igsoft.tablevis.style.text.SimpleTextTableStyleSet
import net.igsoft.tablevis.style.text.TextTableBorder
import org.junit.jupiter.api.Test

class IntegrationTest {
    private val styleSet = BoxTextTableStyleSet(lineSeparator = "\n")
    private val printer = TextTablePrinter()

    @Test
    fun `Nothing defined`() {
        val table = TableBuilder(styleSet).build()

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo("")
    }

    @Test
    fun `Nothing defined, but empty header`() {
        val table = TableBuilder(styleSet) {
            row(styleSet.header)
        }.build()

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo(
            """|┏━━┓
               |┃  ┃
               |┗━━┛
               |""".trimMargin()
        )
    }

    @Test
    fun `Header, row and footer`() {
        val table = TableBuilder(styleSet) {
            width = 40
            //alignRight()

            row(styleSet.header) {
                cell {
                    center()
                    value = "Header"
                }
            }

            row {
                cell {
                    value = "Row 1 Cell 1"
                }
                cell {
                    right()
                    value = "Row 1 Cell 2"
                }
            }

            row {
                cell {
                    value = "Row 2 Cell 1"
                }
                cell {
                    right()
                    value = "Row 2 Cell 2"
                }
            }

            row(styleSet.footer) {
                cell {
                    center()
                    value = "Footer"
                }
                cell {
                    center().middle()
                    width = 15
                    value = "page 1/1"
                }
            }
        }.build()

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo(
            """|┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
               |┃                Header                ┃
               |┡━━━━━━━━━━━━━━━━━━┯━━━━━━━━━━━━━━━━━━━┩
               |│ Row 1 Cell 1     │      Row 1 Cell 2 │
               |├──────────────────┼───────────────────┤
               |│ Row 2 Cell 1     │      Row 2 Cell 2 │
               |┢━━━━━━━━━━━━━━━━━━┷━━━┳━━━━━━━━━━━━━━━┪
               |┃        Footer        ┃   page 1/1    ┃
               |┗━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━┛
               |""".trimMargin()
        )
    }

    @Test
    fun `Header, rows and footer - narrow`() {
        val table = TableBuilder(styleSet) {
            width = 21
            //alignRight()

            row(styleSet.header) {
                cell {
                    center()
                    value = "Header"
                }
            }

            row {
                cell {
                    id("firstCol", "firstRow")
                    value = "Row 1 Cell 1"
                }
                cell {
                    value = "Row 1 Cell 2"
                }
            }

            row {
                cell {
                    id("firstCol")
                    value = "Row 2 Cell 1"
                }
                cell {
                    value = "Row 2 Cell 2"
                }
            }

            row {
                cell {
                    id("firstCol")
                    value = "Row 3 Cell 1"
                }
                cell {
                    value = "Row 3 Cell 2"
                }
            }

            row(styleSet.footer) {
                cell {
                    center()
                    value = "Footer"
                }
                cell {
                    center().middle()
                    width = 15
                    value = "page 1/1"
                }
            }

            forId("firstRow").setHeight(5)
        }.build()

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo(
            """|┏━━━━━━━━━━━━━━━━━━━┓
               |┃      Header       ┃
               |┡━━━━━━━━━┯━━━━━━━━━┩
               |│ Row 1   │ Row 1   │
               |│ Cell 1  │ Cell 2  │
               |│         │         │
               |│         │         │
               |│         │         │
               |├─────────┼─────────┤
               |│ Row 2   │ Row 2   │
               |│ Cell 1  │ Cell 2  │
               |├─────────┼─────────┤
               |│ Row 3   │ Row 3   │
               |│ Cell 1  │ Cell 2  │
               |┢━━━┳━━━━━┷━━━━━━━━━┪
               |┃ F ┃   page 1/1    ┃
               |┃ o ┃               ┃
               |┃ o ┃               ┃
               |┃ t ┃               ┃
               |┃ e ┃               ┃
               |┃ r ┃               ┃
               |┗━━━┻━━━━━━━━━━━━━━━┛
               |""".trimMargin()
        )
    }

    @Test
    fun `Splitting text into rows`() {
        val table = TableBuilder(styleSet) {
            width = 29

            row {
                cell {
                    value = LOREM_IPSUM_TEXT
                }
            }
        }.build()

        println(printer.print(table))

        @Suppress("SpellCheckingInspection") assertThat(printer.print(table)).isEqualTo(
            """|┌───────────────────────────┐
               |│ Lorem Ipsum is simply     │
               |│ dummy text of the printi- │
               |│ ng and typesetting indus- │
               |│ try.                      │
               |│                           │
               |│     Lorem Ipsum has been  │
               |│ the industry's standard   │
               |│ dummy text ever since the │
               |│ 1500s, when an unknown    │
               |│ printer took a galley of  │
               |│ type and scrambled it to  │
               |│ make a type specimen      │
               |│ book. It has survived not │
               |│ only five centuries, but  │
               |│ also the leap into elect- │
               |│ ronic typesetting, remai- │
               |│ ning essentially unchang- │
               |│ ed. It was popularised in │
               |│ the 1960s with the relea- │
               |│ se of Letraset sheets     │
               |│ containing Lorem Ipsum    │
               |│ passages, and more recen- │
               |│ tly with desktop publish- │
               |│ ing software like Aldus   │
               |│ PageMaker including       │
               |│ versions of Lorem Ipsum.  │
               |└───────────────────────────┘
               |""".trimMargin()
        )
    }

    @Test
    fun `Simple header with windows line separator`() {
        val styleSet = SimpleTextTableStyleSet(lineSeparator = "\r\n")
        val table = TableBuilder(styleSet) {
            width = 39

            row(styleSet.header) {
                cell {
                    center()
                    value = "Header 1"
                }
            }
        }.build()

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo(
            //@formatter:off
            "+=~==~==~==~==~==~==~==~==~==~==~==~==+\r\n" +
            "*              Header 1               *\r\n" +
            "+=~==~==~==~==~==~==~==~==~==~==~==~==+\r\n"
            //@formatter:on
        )
    }

    @Test
    fun `Simple text style with cascading centering property`() {
        val styleSet = SimpleTextTableStyleSet(lineSeparator = "\n")
        val table = TableBuilder(styleSet) {
            center()
            width = 39

            row {
                cell {
                    value = "Header 1"
                }
                cell {
                    value = "Header 2"
                }
            }

            row {
                cell {
                    value = "Row"
                }
            }
        }.build()

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo(
            """|+------------------+------------------+
               ||     Header 1     |     Header 2     |
               |+------------------+------------------+
               ||                 Row                 |
               |+-------------------------------------+
               |""".trimMargin()
        )
    }

    @Test
    fun `Cascading styles`() {
        val styleSet = SimpleTextTableStyleSet(lineSeparator = "\n")
        val table = TableBuilder(styleSet) {
            center() //Everything centered by default
            width = 39

            row(styleSet.header) {
                //New style cancels top level alignment (no inheritance here)
                cell {
                    right()
                    value = "Header 1"
                }
                cell {
                    value = "Header 2"
                }
            }

            row {
                //Inherited center property
                cell {
                    value = "Row"
                }
            }

            row {
                right() //this row is right aligned
                cell {
                    value = "Cell 1"
                }
                cell {
                    left() //but this cell is left aligned
                    value = "Cell 2"
                }
            }
        }.build()

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo(
            """|+=~==~==~==~==~==~=+=~==~==~==~==~==~=+
               |*         Header 1 * Header 2         *
               |+=~==~==~==~==~==~=+=~==~==~==~==~==~=+
               ||                 Row                 |
               |+------------------+------------------+
               ||           Cell 1 | Cell 2           |
               |+------------------+------------------+
               |""".trimMargin()
        )
    }

    @Test
    fun `Id on table with empty header`() {
        val table = TableBuilder(styleSet) {
            row(styleSet.header) {
                cell {
                    id("headerCell")
                }
            }

            forId("headerCell").setWidth(5)
        }.build()

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo(
            """|┏━━━━━━━┓
               |┃       ┃
               |┗━━━━━━━┛
               |""".trimMargin()
        )
    }

    @Test
    fun `Set min width on header and rows`() {
        val table = TableBuilder(styleSet) {
            row(styleSet.header) {
                cell {
                    id("col1")
                    value = "Col 1"
                }
                cell {
                    value = "Col 2"
                }
                cell {
                    value = "Col 3"
                }
            }

            row {
                cell {
                    id("col1")
                    value = "12"
                }
                cell {
                    value = "12345678"
                }
            }

            row {
                cell {
                    id("col1")
                    value = "12345678"
                }
                cell {
                    value = "32"
                }
            }

            forId("col1").setMinimalWidth()
        }.build()

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo(
            """|┏━━━━━━━━━━┳━━━━━━━┳━━━━━━━┓
               |┃ Col 1    ┃ Col 2 ┃ Col 3 ┃
               |┡━━━━━━━━━━╇━━━━━━━┻━━━━━━━┩
               |│ 12       │ 12345678      │
               |├──────────┼───────────────┤
               |│ 12345678 │ 32            │
               |└──────────┴───────────────┘
               |""".trimMargin()
        )
    }

    @Test
    fun `Width shorter than it is possible to put texts`() {
        @Suppress("SpellCheckingInspection") assertThat {
            TableBuilder(styleSet) {
                width = 6

                row {
                    cell {
                        value = "Tekst pierwszy"
                    }
                    cell {
                        value = "Tekst drugi"
                    }
                    cell {
                        value = "Tekst trzeci"
                    }
                }

                row {
                    cell {
                        value = "Dolny wiersz"
                    }
                }
            }.build()
        }.isFailure().isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `Simple table with width set`() {
        @Suppress("SpellCheckingInspection")
        val table = TableBuilder(styleSet) {
            width = 35

            row {
                cell {
                    value = "Tekst pierwszy"
                }
                cell {
                    value = "Tekst drugi"
                }
                cell {
                    value = "Tekst trzeci"
                }
            }

            row {
                cell {
                    center()
                    value = "Dolny wiersz"
                }
            }
        }.build()

        println(printer.print(table))

        @Suppress("SpellCheckingInspection") assertThat(printer.print(table)).isEqualTo(
            """|┌──────────┬──────────┬───────────┐
               |│ Tekst    │ Tekst    │ Tekst tr- │
               |│ pierwszy │ drugi    │ zeci      │
               |├──────────┴──────────┴───────────┤
               |│          Dolny wiersz           │
               |└─────────────────────────────────┘
               |""".trimMargin()
        )
    }

    @Test
    fun `Simple table without width set`() {
        @Suppress("SpellCheckingInspection")
        val table = TableBuilder(styleSet) {
            row {
                cell {
                    value = "Tekst pierwszy"
                }
                cell {
                    value = "Tekst drugi"
                }
                cell {
                    value = "Tekst trzeci"
                }
            }

            row {
                cell {
                    center()
                    value = "Dolny wiersz"
                }
            }
        }.build()

        println(printer.print(table))

        @Suppress("SpellCheckingInspection") assertThat(printer.print(table)).isEqualTo(
            """|┌────────────────┬─────────────┬──────────────┐
               |│ Tekst pierwszy │ Tekst drugi │ Tekst trzeci │
               |├────────────────┴─────────────┴──────────────┤
               |│                Dolny wiersz                 │
               |└─────────────────────────────────────────────┘
               |""".trimMargin()
        )
    }

    @Test
    fun `Simple table without width set and with multiline text`() {
        @Suppress("SpellCheckingInspection")
        val table = TableBuilder(styleSet) {
            row {
                cell {
                    value = "Tekst pierwszy"
                }
                cell {
                    value = "Tekst drugi\nLinia 1\nLinia2\nBardzo długi tekst"
                }
                cell {
                    value = "Tekst trzeci"
                }
            }

            row {
                cell {
                    center()
                    value = "Dolny wiersz"
                }
            }
        }.build()

        println(printer.print(table))

        @Suppress("SpellCheckingInspection") assertThat(printer.print(table)).isEqualTo(
            """|┌────────────────┬────────────────────┬──────────────┐
               |│ Tekst pierwszy │ Tekst drugi        │ Tekst trzeci │
               |│                │ Linia 1            │              │
               |│                │ Linia2             │              │
               |│                │ Bardzo długi tekst │              │
               |├────────────────┴────────────────────┴──────────────┤
               |│                    Dolny wiersz                    │
               |└────────────────────────────────────────────────────┘
               |""".trimMargin()
        )
    }

    @Test
    fun `Simple table without width set and with multiline text1`() {
        @Suppress("SpellCheckingInspection")
        val table = TableBuilder(styleSet) {
            row {
                cell {
                    value = "Tekst pierwszy"
                }
                cell {
                    center()
                    value = BANK_ACCOUNT_TEXT
                }
                cell {
                    value = "Text trzeci"
                }
            }

            row {
                cell {
                    center()
                    value = "Dolny wiersz"
                }
            }
        }.build()

        println(printer.print(table))

        @Suppress("SpellCheckingInspection") assertThat(printer.print(table)).isEqualTo(
            """|┌────────────────┬──────────────────────────────────────┬─────────────┐
               |│ Tekst pierwszy │          Rachunek bankowy:           │ Text trzeci │
               |│                │ Jakaś świetna firma - "Krótka nazwa" │             │
               |│                │           ul. Zielona 114            │             │
               |│                │           02-620 Warszawa            │             │
               |│                │                                      │             │
               |│                │ PLN 78 1280 1112 1341 1210 0909 5620 │             │
               |│                │            Bank Pekao SA             │             │
               |│                │       VIII odział w Warszawie        │             │
               |├────────────────┴──────────────────────────────────────┴─────────────┤
               |│                            Dolny wiersz                             │
               |└─────────────────────────────────────────────────────────────────────┘
               |""".trimMargin()
        )
    }

    @Test
    fun `Simple table without width set and with multiline text and multiple aligned rows`() {
        @Suppress("SpellCheckingInspection")
        val table = TableBuilder(styleSet) {
            row {
                cell {
                    id("left")
                    value = "Tekst pierwszy"
                }
                cell {
                    id("central")
                    center()
                    value = BANK_ACCOUNT_TEXT
                }
                cell {
                    value = "Text trzeci"
                }
            }

            row {
                cell {
                    id("left")
                    value = "column A"
                }
                cell {
                    id("central")
                    value = "column B"
                    center()
                }
                cell {
                    value = "column C"
                }
            }

            row {
                cell {
                    value = "Dolny wiersz"
                }
            }

            forId("central").setMinimalWidth()
            forId("left").setMinimalWidth()
        }.build()

        println(printer.print(table))

        @Suppress("SpellCheckingInspection") assertThat(printer.print(table)).isEqualTo(
            """|┌────────────────┬──────────────────────────────────────┬─────────────┐
               |│ Tekst pierwszy │          Rachunek bankowy:           │ Text trzeci │
               |│                │ Jakaś świetna firma - "Krótka nazwa" │             │
               |│                │           ul. Zielona 114            │             │
               |│                │           02-620 Warszawa            │             │
               |│                │                                      │             │
               |│                │ PLN 78 1280 1112 1341 1210 0909 5620 │             │
               |│                │            Bank Pekao SA             │             │
               |│                │       VIII odział w Warszawie        │             │
               |├────────────────┼──────────────────────────────────────┼─────────────┤
               |│ column A       │               column B               │ column C    │
               |├────────────────┴──────────────────────────────────────┴─────────────┤
               |│ Dolny wiersz                                                        │
               |└─────────────────────────────────────────────────────────────────────┘
               |""".trimMargin()
        )
    }

    @Test
    fun `Program options formatting - no borders`() {
        val table = TableBuilder(NoBorderTextTableStyleSet(lineSeparator = "\n")) {
            width = 110
            leftMargin = 0
            rightMargin = 0

            row {
                cell {
                    justify()
                    value = LOREM_IPSUM_TEXT
                }
            }

            row { }

            row {
                cell {
                    id(1)
                }
                cell {
                    id(2)
                    value = "First option"
                }
                cell {
                    id(3)
                }
                cell {
                    justify()
                    value = OPTION1_DESCRIPTION_TEXT
                }
            }

            row { }

            row {
                cell {
                    id(1)
                }
                cell {
                    id(2)
                    value = "Some other option"
                }
                cell {
                    id(3)
                }
                cell {
                    justify()
                    value = OPTION2_DESCRIPTION_TEXT
                }
            }

            row { }

            forId(1).setWidth(2)
            forId(3).setWidth(4)
            forId(2).setMinimalWidth()
        }.build()

        println(printer.print(table))

        @Suppress("SpellCheckingInspection") assertThat(printer.print(table)).isEqualTo(
            """|Lorem Ipsum is simply dummy text of the printing and typesetting industry.                                    
               |                                                                                                              
               |    Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took
               |a  galley of type and scrambled it to make a type  specimen book. It has survived not only five centuries, but
               |also  the leap into electronic  typesetting, remaining essentially unchanged.  It was popularised in the 1960s
               |with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing
               |software like Aldus PageMaker including versions of Lorem Ipsum.                                              
               |                                                                                                              
               |  First option             Option  description - It is a long  established     *fact*    that a reader will be
               |                       distracted  by the readable content of a page  when looking at its layout. The point of
               |                       using  Lorem Ipsum  is that  it has  a more-or-less  normal distribution of letters, as
               |                       opposed  to using 'Content here,  content here', making it  look like readable English.
               |                       Many  desktop publishing  packages and  web page  editors now  use Lorem Ipsum as their
               |                       default model text, and a search for 'lorem ipsum' will uncover many web sites still in
               |                       their  infancy. Various  versions have  evolved over  the years, sometimes by accident,
               |                       sometimes on purpose (injected humour and the like).                                   
               |                                                                                                              
               |  Some other option        Some  other option  description -  contrary to  popular belief,  Lorem Ipsum is not
               |                       simply  random text. It has roots in a  piece of classical Latin literature from 45 BC,
               |                       making  it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney
               |                       College in Virginia, looked up one of the more obscure Latin words, consectetur, from a
               |                       Lorem  Ipsum passage, and going through the  cites of the word in classical literature,
               |                       discovered  the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33
               |                       of  de Finibus Bonorum et Malorum (The Extremes of Good and Evil) by Cicero, written in
               |                       45  BC.  This  book  is  a  treatise  on  the theory of ethics, very popular during the
               |                       Renaissance.  The first line of Lorem Ipsum, Lorem ipsum dolor sit amet.., comes from a
               |                       line in section 1.10.32.                                                               
               |                                                                                                              
               |""".trimMargin()
        )
    }

    @Test
    fun `Program options formatting - with borders`() {
        val table = TableBuilder(styleSet) {
            width = 110
            row {
                cell {
                    justify()
                    value = LOREM_IPSUM_TEXT
                }
            }

            row { }

            row {
                cell {
                    id(1)
                }
                cell {
                    id(2)
                    value = "First option"
                }
                cell {
                    value = ":"
                }
                cell {
                    justify()
                    value = OPTION1_DESCRIPTION_TEXT
                }
            }

            row {
                cell {
                    id(1)
                }
                cell {
                    id(2)
                    value = "Some other option"
                }
                cell {
                    value = ":"
                }
                cell {
                    justify()
                    value = OPTION2_DESCRIPTION_TEXT
                }
            }

            row { }

            //col-3 and other identifiers are added automatically
            forId(1, 2, "col-3").setMinimalWidth()
        }.build()

        println(printer.print(table))

        @Suppress("SpellCheckingInspection") assertThat(printer.print(table)).isEqualTo(
            """|┌────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
               |│ Lorem Ipsum is simply dummy text of the printing and typesetting industry.                                 │
               |│                                                                                                            │
               |│     Lorem  Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer │
               |│ took  a galley  of type  and scrambled  it to  make a  type specimen  book. It  has survived not only five │
               |│ centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popular- │
               |│ ised  in the 1960s with the release of Letraset  sheets containing Lorem Ipsum passages, and more recently │
               |│ with   desktop   publishing   software   like   Aldus   PageMaker   including  versions  of  Lorem  Ipsum. │
               |├────────────────────────────────────────────────────────────────────────────────────────────────────────────┤
               |│                                                                                                            │
               |├──┬───────────────────┬───┬─────────────────────────────────────────────────────────────────────────────────┤
               |│  │ First option      │ : │     Option  description - It is a  long established     *fact*    that a reader │
               |│  │                   │   │ will  be  distracted  by  the  readable  content  of a page when looking at its │
               |│  │                   │   │ layout.  The point of  using Lorem Ipsum  is that it  has a more-or-less normal │
               |│  │                   │   │ distribution  of letters,  as opposed  to using  'Content here,  content here', │
               |│  │                   │   │ making  it look like readable English. Many desktop publishing packages and web │
               |│  │                   │   │ page  editors now use Lorem Ipsum as their default model text, and a search for │
               |│  │                   │   │ 'lorem  ipsum'  will  uncover  many  web  sites still in their infancy. Various │
               |│  │                   │   │ versions  have  evolved  over  the  years,  sometimes by accident, sometimes on │
               |│  │                   │   │ purpose (injected humour and the like).                                         │
               |├──┼───────────────────┼───┼─────────────────────────────────────────────────────────────────────────────────┤
               |│  │ Some other option │ : │     Some  other option description - contrary to popular belief, Lorem Ipsum is │
               |│  │                   │   │ not  simply random text. It has roots  in a piece of classical Latin literature │
               |│  │                   │   │ from 45 BC, making it over 2000 years old. Richard McClintock, a Latin profess- │
               |│  │                   │   │ or  at Hampden-Sydney College  in Virginia, looked  up one of  the more obscure │
               |│  │                   │   │ Latin  words, consectetur,  from a  Lorem Ipsum  passage, and going through the │
               |│  │                   │   │ cites  of the word in classical  literature, discovered the undoubtable source. │
               |│  │                   │   │ Lorem  Ipsum comes from sections  1.10.32 and 1.10.33 of  de Finibus Bonorum et │
               |│  │                   │   │ Malorum  (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book │
               |│  │                   │   │ is a treatise on the theory of ethics, very popular during the Renaissance. The │
               |│  │                   │   │ first  line of Lorem Ipsum, Lorem ipsum dolor  sit amet.., comes from a line in │
               |│  │                   │   │ section 1.10.32.                                                                │
               |├──┴───────────────────┴───┴─────────────────────────────────────────────────────────────────────────────────┤
               |│                                                                                                            │
               |└────────────────────────────────────────────────────────────────────────────────────────────────────────────┘
               |""".trimMargin()
        )
    }

    @Test
    fun `Cells without borders`() {
        val table = TableBuilder(styleSet) {
            center()

            row {
                cell {
                    bottomBorder = TextTableBorder.none
                    value = 1
                }
                cell {
                    rightBorder = TextTableBorder.none
                    value = 2
                }
                cell {
                    bottomBorder = TextTableBorder.none
                    value = 3
                }
            }
            row {
                cell {
                    value = 4
                }
                cell {
                    value = 5
                }
                cell {
                    value = 6
                }
            }
            row {
                cell {
                    value = 7
                }
                cell {
                    rightBorder = TextTableBorder.none
                    value = 8
                }
                cell {
                    value = 9
                }
            }

        }.build()

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo(
            """|┌───┬───────┐
               |│ 1 │ 2   3 │
               |│   ├───┐   │
               |│ 4 │ 5 │ 6 │
               |├───┼───┴───┤
               |│ 7 │ 8   9 │
               |└───┴───────┘
               |""".trimMargin()
        )
    }

    @Test
    fun `Header in first col`() {
        val table = TableBuilder(BoxTextTableStyleSet(lineSeparator = "\n")) {
            row {
                cell(styleSet.header) { value = "Nice title 1" }
                cell { value = "Nice title 2" }
                cell { value = "Nice title 3" }
            }
        }.build()

        println(TextTablePrinter().print(table))

        assertThat(printer.print(table)).isEqualTo(
            """|┏━━━━━━━━━━━━━━┱──────────────┬──────────────┐
               |┃ Nice title 1 ┃ Nice title 2 │ Nice title 3 │
               |┗━━━━━━━━━━━━━━┹──────────────┴──────────────┘
               |""".trimMargin()
        )
    }

    @Test
    fun `Header in last col and round corners`() {
        val table = TableBuilder(BoxTextTableStyleSet(lineSeparator = "\n", roundCorners = true)) {
            row {
                cell { value = "Cell 1" }
                cell { value = "Cell 2" }
                cell(styleSet.header) { value = "Cell 3" }
            }
        }.build()

        println(TextTablePrinter().print(table))

        assertThat(printer.print(table)).isEqualTo(
            """|╭────────┬────────┲━━━━━━━━┓
               |│ Cell 1 │ Cell 2 ┃ Cell 3 ┃
               |╰────────┴────────┺━━━━━━━━┛
               |""".trimMargin()
        )
    }

    @Test
    fun `Line type changed`() {
        val table = TableBuilder(BoxTextTableStyleSet(lineSeparator = "\n")) {
            row {
                cell(styleSet.header) { value = "Cell 1"; rightBorder = TextTableBorder.none}
                cell { value = "Cell 2" }
                cell(styleSet.header) { value = "Cell 3"; leftBorder = TextTableBorder.none }
            }
            row {
                cell(styleSet.header) { value = "Cell 1"; bottomBorder = TextTableBorder.none}
                cell { value = "Cell 2" }
                cell(styleSet.header) { value = "Cell 3"; bottomBorder = TextTableBorder.none }
            }
            row {
                cell { value = "Cell 1"; rightBorder = TextTableBorder.none}
                cell { value = "Cell 2" }
                cell { value = "Cell 3"; leftBorder = TextTableBorder.none }
            }
        }.build()

        println(TextTablePrinter().print(table))

        assertThat(printer.print(table)).isEqualTo(
            """|┏━━━━━━━━╾────────╼━━━━━━━━┓
               |┃ Cell 1   Cell 2   Cell 3 ┃
               |┣━━━━━━━━┱────────┲━━━━━━━━┫
               |┃ Cell 1 ┃ Cell 2 ┃ Cell 3 ┃
               |╿        ┖────────┚        ╿
               |│ Cell 1   Cell 2   Cell 3 │
               |└──────────────────────────┘
               |""".trimMargin()
        )
    }

    @Test
    fun `Rows and footer cells in edges`() {
        val table = TableBuilder(BoxTextTableStyleSet(lineSeparator = "\n")) {
            row {
                cell(styleSet.footer) { value = "Row 1 - Cell 1" }
                cell { value = "Row 1 - Cell 2" }
                cell(styleSet.footer) { value = "Row 1 - Cell 3" }
            }
            row {
                cell { value = "Row 2 - Cell 1" }
                cell { value = "Row 2 - Cell 2" }
                cell { value = "Row 2 - Cell 3" }
            }
            row {
                cell(styleSet.footer) { value = "Row 3 - Cell 1" }
                cell { value = "Row 3 - Cell 2" }
                cell(styleSet.footer) { value = "Row 3 - Cell 3" }
            }
        }.build()

        println(TextTablePrinter().print(table))

        assertThat(printer.print(table)).isEqualTo(
            """|┏━━━━━━━━━━━━━━━━┱────────────────┲━━━━━━━━━━━━━━━━┓
               |┃ Row 1 - Cell 1 ┃ Row 1 - Cell 2 ┃ Row 1 - Cell 3 ┃
               |┡━━━━━━━━━━━━━━━━╃────────────────╄━━━━━━━━━━━━━━━━┩
               |│ Row 2 - Cell 1 │ Row 2 - Cell 2 │ Row 2 - Cell 3 │
               |┢━━━━━━━━━━━━━━━━╅────────────────╆━━━━━━━━━━━━━━━━┪
               |┃ Row 3 - Cell 1 ┃ Row 3 - Cell 2 ┃ Row 3 - Cell 3 ┃
               |┗━━━━━━━━━━━━━━━━┹────────────────┺━━━━━━━━━━━━━━━━┛
               |""".trimMargin()
        )
    }

    @Test
    fun `Header cell in the centre`() {
        val table = TableBuilder(BoxTextTableStyleSet(lineSeparator = "\n")) {
            row {
                cell { value = "Row 1 - Cell 1" }
                cell { value = "Row 1 - Cell 2" }
                cell { value = "Row 1 - Cell 3" }
            }
            row {
                cell { value = "Row 2 - Cell 1" }
                cell(styleSet.header) { value = "Row 2 - Cell 2" }
                cell { value = "Row 2 - Cell 3" }
            }
            row {
                cell { value = "Row 3 - Cell 1" }
                cell { value = "Row 3 - Cell 2" }
                cell { value = "Row 3 - Cell 3" }
            }
        }.build()

        println(TextTablePrinter().print(table))

        assertThat(printer.print(table)).isEqualTo(
            """|┌────────────────┬────────────────┬────────────────┐
               |│ Row 1 - Cell 1 │ Row 1 - Cell 2 │ Row 1 - Cell 3 │
               |├────────────────╆━━━━━━━━━━━━━━━━╅────────────────┤
               |│ Row 2 - Cell 1 ┃ Row 2 - Cell 2 ┃ Row 2 - Cell 3 │
               |├────────────────╄━━━━━━━━━━━━━━━━╃────────────────┤
               |│ Row 3 - Cell 1 │ Row 3 - Cell 2 │ Row 3 - Cell 3 │
               |└────────────────┴────────────────┴────────────────┘
               |""".trimMargin()
        )
    }

    @Test
    fun `Automatic synchronisation of headers`() {


        val table = TableBuilder(BoxTextTableStyleSet(lineSeparator = "\n")) {
            row {
                cell { value = "11 - o" }
                cell { value = "12 - oo" }
                cell { value = "13 - ooo" }
            }
            row {
                cell { value = "21 - oooo" }
                cell { value = "22 - ooooo" }
                cell { value = "23 - oooooo" }
            }
            row {
                cell { value = "31 - ooooooo" }
                cell { value = "32 - oooooooo" }
                cell { value = "33 - ooooooooo" }
            }
            syncColumns()
        }.build()

        println(TextTablePrinter().print(table))

        assertThat(printer.print(table)).isEqualTo(
            """|┌──────────────┬───────────────┬────────────────┐
               |│ 11 - o       │ 12 - oo       │ 13 - ooo       │
               |├──────────────┼───────────────┼────────────────┤
               |│ 21 - oooo    │ 22 - ooooo    │ 23 - oooooo    │
               |├──────────────┼───────────────┼────────────────┤
               |│ 31 - ooooooo │ 32 - oooooooo │ 33 - ooooooooo │
               |└──────────────┴───────────────┴────────────────┘
               |""".trimMargin()
        )
    }

    @Suppress("SpellCheckingInspection")
    companion object {
        //@formatter:off
        private const val BANK_ACCOUNT_TEXT = "Rachunek bankowy:\nJakaś świetna firma - \"Krótka nazwa\"\nul. Zielona 114\n" +
                "02-620 Warszawa\n\nPLN 78 1280 1112 1341 1210 0909 5620\nBank Pekao SA\nVIII odział w Warszawie"

        private const val LOREM_IPSUM_TEXT = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. \n\n\t" +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took " +
                "a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, " +
                "but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the " +
                "1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop " +
                "publishing software like Aldus PageMaker including versions of Lorem Ipsum."

        private const val OPTION1_DESCRIPTION_TEXT = "\tOption description - It is a long established     *fact*    that a reader " +
                "will be distracted by the readable content of a page when looking at its layout. The point of using Lorem " +
                "Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, " +
                "content here', making it look like readable English. Many desktop publishing packages and web page " +
                "editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many " +
                "web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, " +
                "sometimes on purpose (injected humour and the like)."

        private const val OPTION2_DESCRIPTION_TEXT = "\tSome other option description - contrary to popular belief, " +
                "Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, " +
                "making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, " +
                "looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through " +
                "the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from " +
                "sections 1.10.32 and 1.10.33 of de Finibus Bonorum et Malorum (The Extremes of Good and Evil) by Cicero, " +
                "written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. " +
                "The first line of Lorem Ipsum, Lorem ipsum dolor sit amet.., comes from a line in section 1.10.32."
        //@formatter:on
    }
}
