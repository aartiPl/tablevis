package net.igsoft.tablevis

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isInstanceOf
import net.igsoft.tablevis.text.BoxTextStyleSet
import net.igsoft.tablevis.text.SimpleTextStyleSet
import net.igsoft.tablevis.text.TextTablePrinter
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

class IntegrationTest {
    private val styleSet = BoxTextStyleSet(lineSeparator = "\n")
    private val printer = TextTablePrinter()

    @Test
    fun `Nothing defined`() {
        val table = Table.using(styleSet)

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo("")
    }

    @Test
    fun `Nothing defined, but empty header`() {
        val table = Table.using(styleSet) {
            row(styleSet.header) {}
        }

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
        val table = Table.using(styleSet) {
            width = 40
            //alignRight()

            row(styleSet.header) {
                cell {
                    alignCenter()
                    text = "Header"
                }
            }

            row {
                cell {
                    text = "Row 1 Cell 1"
                }
                cell {
                    alignRight()
                    text = "Row 1 Cell 2"
                }
            }

            row {
                cell {
                    text = "Row 2 Cell 1"
                }
                cell {
                    alignRight()
                    text = "Row 2 Cell 2"
                }
            }

            row(styleSet.footer) {
                cell {
                    alignCenter()
                    text = "Footer"
                }
                cell {
                    alignCenter().alignMiddle()
                    width = 15
                    text = "page 1/1"
                }
            }
        }

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
        val table = Table.using(styleSet) {
            width = 21
            //alignRight()

            row(styleSet.header) {
                cell {
                    alignCenter()
                    text = "Header"
                }
            }

            row {
                cell {
                    id("firstCol", "firstRow")
                    text = "Row 1 Cell 1"
                }
                cell {
                    text = "Row 1 Cell 2"
                }
            }

            row {
                cell {
                    id("firstCol")
                    text = "Row 2 Cell 1"
                }
                cell {
                    text = "Row 2 Cell 2"
                }
            }

            row {
                cell {
                    id("firstCol")
                    text = "Row 3 Cell 1"
                }
                cell {
                    text = "Row 3 Cell 2"
                }
            }

            row(styleSet.footer) {
                cell {
                    alignCenter()
                    text = "Footer"
                }
                cell {
                    alignCenter().alignMiddle()
                    width = 15
                    text = "page 1/1"
                }
            }

            forId("firstRow").setHeight(5)
        }

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
        val table = Table.using(styleSet) {
            width = 29

            row {
                cell {
                    text = LOREM_IPSUM_TEXT
                }
            }
        }

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo(
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
    fun `Nothing defined, but empty header with windows lineSeparator`() {
        val style = SimpleTextStyleSet(lineSeparator = "\r\n")
        val table = Table.using(style) {
            row(style.header) { }
        }

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo("+~=+\r\n*  *\r\n+~=+\r\n")
    }

    @Test
    fun `Id on table with empty header`() {
        val table = Table.using(styleSet) {
            row(styleSet.header) {
                cell {
                    id("headerCell")
                }
            }

            forId("headerCell").setWidth(5)
        }

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
        val table = Table.using(styleSet) {
            row(styleSet.header) {
                cell {
                    id("col1")
                    text = "Col 1"
                }
                cell {
                    text = "Col 2"
                }
                cell {
                    text = "Col 3"
                }
            }

            row {
                cell {
                    id("col1")
                    text = "12"
                }
                cell {
                    text = "12345678"
                }
            }

            row {
                cell {
                    id("col1")
                    text = "12345678"
                }
                cell {
                    text = "32"
                }
            }

            forId("col1").setMinimalWidth()
        }

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
        assertThat {
            Table.using(styleSet) {
                width = 6

                row {
                    cell {
                        text = "Tekst pierwszy"
                    }
                    cell {
                        text = "Tekst drugi"
                    }
                    cell {
                        text = "Tekst trzeci"
                    }
                }

                row {
                    cell {
                        text = "Dolny wiersz"
                    }
                }
            }
        }.isFailure().isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `Simple table with width set`() {
        val table = Table.using(styleSet) {
            width = 35

            row {
                cell {
                    text = "Tekst pierwszy"
                }
                cell {
                    text = "Tekst drugi"
                }
                cell {
                    text = "Tekst trzeci"
                }
            }

            row {
                cell {
                    alignCenter()
                    text = "Dolny wiersz"
                }
            }
        }

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo(
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
        val table = Table.using(styleSet) {
            row {
                cell {
                    text = "Tekst pierwszy"
                }
                cell {
                    text = "Tekst drugi"
                }
                cell {
                    text = "Tekst trzeci"
                }
            }

            row {
                cell {
                    alignCenter()
                    text = "Dolny wiersz"
                }
            }
        }

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo(
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
        val table = Table.using(styleSet) {
            row {
                cell {
                    text = "Tekst pierwszy"
                }
                cell {
                    text = "Tekst drugi\nLinia 1\nLinia2\nBardzo długi tekst"
                }
                cell {
                    text = "Tekst trzeci"
                }
            }

            row {
                cell {
                    alignCenter()
                    text = "Dolny wiersz"
                }
            }
        }

        println(printer.print(table))


        assertThat(printer.print(table)).isEqualTo(
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
        val table = Table.using(styleSet) {
            row {
                cell {
                    text = "Tekst pierwszy"
                }
                cell {
                    alignCenter()
                    text = BANK_ACCOUNT_TEXT
                }
                cell {
                    text = "Text trzeci"
                }
            }

            row {
                cell {
                    alignCenter()
                    text = "Dolny wiersz"
                }
            }
        }

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo(
            """|┌────────────────┬──────────────────────────────────────┬─────────────┐
               |│ Tekst pierwszy │          Rachunek bankowy:           │ Text trzeci │
               |│                │ CRK ProEcclesia SCh - "ProCracovia"  │             │
               |│                │           ul. Puławska 114           │             │
               |│                │           02-620 Warszawa            │             │
               |│                │                                      │             │
               |│                │ PLN 78 1240 1112 1111 0010 0909 5620 │             │
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
        val table = Table.using(styleSet) {
            row {
                cell {
                    id("left")
                    text = "Tekst pierwszy"
                }
                cell {
                    id("central")
                    alignCenter()
                    text = BANK_ACCOUNT_TEXT
                }
                cell {
                    text = "Text trzeci"
                }
            }

            row {
                cell {
                    id("left")
                    text = "column A"
                }
                cell {
                    id("central")
                    text = "column B"
                    alignCenter()
                }
                cell {
                    text = "column C"
                }
            }

            row {
                cell {
                    text = "Dolny wiersz"
                }
            }

            forId("central").setMinimalWidth()
            forId("left").setMinimalWidth()
        }

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo(
            """|┌────────────────┬──────────────────────────────────────┬─────────────┐
               |│ Tekst pierwszy │          Rachunek bankowy:           │ Text trzeci │
               |│                │ CRK ProEcclesia SCh - "ProCracovia"  │             │
               |│                │           ul. Puławska 114           │             │
               |│                │           02-620 Warszawa            │             │
               |│                │                                      │             │
               |│                │ PLN 78 1240 1112 1111 0010 0909 5620 │             │
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
    fun `Just show the table`() {
        //http://en.wikipedia.org/wiki/Box-drawing_character#Unicode

        //TODO: no-borders style
        val table = Table.using(styleSet) {
            width = 110
            row {
                cell {
                    justify()
                    text = LOREM_IPSUM_TEXT
                }
            }

            row { }

            row {
                cell {
                    id(1)
                }
                cell {
                    id(2)
                    text = "First option"
                }
                cell {
                    text = ":"
                }
                cell {
                    justify()
                    text = OPTION1_DESCRIPTION_TEXT
                }
            }

            row {
                cell {
                    id(1)
                }
                cell {
                    id(2)
                    text = "Some other option"
                }
                cell {
                    text = ":"
                }
                cell {
                    text = OPTION2_DESCRIPTION_TEXT
                }
            }

            row { }

            //col-3 and other identifiers are added automatically
            forId(1, 2, "col-3").setMinimalWidth()
        }

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo(
            """|┌────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
               |│ Lorem Ipsum is simply dummy text of the printing and typesetting industry.                                 │
               |│                                                                                                            │
               |│     Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer  │
               |│ took a galley of type and scrambled it to make a type specimen book. It has survived not only five         │
               |│ centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popular- │
               |│ ised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently   │
               |│ with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.                   │
               |├────────────────────────────────────────────────────────────────────────────────────────────────────────────┤
               |│                                                                                                            │
               |├──┬───────────────────┬───┬─────────────────────────────────────────────────────────────────────────────────┤
               |│  │ First option      │ : │     Option description - It is a long established     *fact*    that a reader   │
               |│  │                   │   │ will be distracted by the readable content of a page when looking at its        │
               |│  │                   │   │ layout. The point of using Lorem Ipsum is that it has a more-or-less normal     │
               |│  │                   │   │ distribution of letters, as opposed to using 'Content here, content here',      │
               |│  │                   │   │ making it look like readable English. Many desktop publishing packages and web  │
               |│  │                   │   │ page editors now use Lorem Ipsum as their default model text, and a search for  │
               |│  │                   │   │ 'lorem ipsum' will uncover many web sites still in their infancy. Various       │
               |│  │                   │   │ versions have evolved over the years, sometimes by accident, sometimes on       │
               |│  │                   │   │ purpose (injected humour and the like).                                         │
               |├──┼───────────────────┼───┼─────────────────────────────────────────────────────────────────────────────────┤
               |│  │ Some other option │ : │     Some other option description - contrary to popular belief, Lorem Ipsum is  │
               |│  │                   │   │ not simply random text. It has roots in a piece of classical Latin literature   │
               |│  │                   │   │ from 45 BC, making it over 2000 years old. Richard McClintock, a Latin profess- │
               |│  │                   │   │ or at Hampden-Sydney College in Virginia, looked up one of the more obscure     │
               |│  │                   │   │ Latin words, consectetur, from a Lorem Ipsum passage, and going through the     │
               |│  │                   │   │ cites of the word in classical literature, discovered the undoubtable source.   │
               |│  │                   │   │ Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of de Finibus Bonorum et    │
               |│  │                   │   │ Malorum (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book  │
               |│  │                   │   │ is a treatise on the theory of ethics, very popular during the Renaissance. The │
               |│  │                   │   │ first line of Lorem Ipsum, Lorem ipsum dolor sit amet.., comes from a line in   │
               |│  │                   │   │ section 1.10.32.                                                                │
               |├──┴───────────────────┴───┴─────────────────────────────────────────────────────────────────────────────────┤
               |│                                                                                                            │
               |└────────────────────────────────────────────────────────────────────────────────────────────────────────────┘
               |""".trimMargin())
    }

    companion object {
        //@formatter:off
        private const val BANK_ACCOUNT_TEXT = "Rachunek bankowy:\nCRK ProEcclesia SCh - \"ProCracovia\"\nul. Puławska 114\n" +
                "02-620 Warszawa\n\nPLN 78 1240 1112 1111 0010 0909 5620\nBank Pekao SA\nVIII odział w Warszawie"

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
