package net.igsoft.tablevis

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.igsoft.tablevis.text.TextTablePrinter
import net.igsoft.tablevis.text.SimpleTextTableStyle
import org.junit.jupiter.api.Test

class TextTableBuilderTest {
    //@formatter:off
  private val BANK_ACCOUNT_TEXT = "Rachunek bankowy:\nCRK ProEcclesia SCh - \"ProCracovia\"\nul. Puławska 114\n" +
    "02-620 Warszawa\n\nPLN 78 1240 1112 1111 0010 0909 5620\nBank Pekao SA\nVIII odział w Warszawie"

  private val LOREM_IPSUM_TEXT = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. \n\n\t" +
    "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took " +
    "a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, " +
    "but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the " +
    "1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop " +
    "publishing software like Aldus PageMaker including versions of Lorem Ipsum."

  private val OPTION1_DESCRIPTION_TEXT = "\tOption description - It is a long established     *fact*    that a reader " +
    "will be distracted by the readable content of a page when looking at its layout. The point of using Lorem " +
    "Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, " +
    "content here', making it look like readable English. Many desktop publishing packages and web page " +
    "editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many " +
    "web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, " +
    "sometimes on purpose (injected humour and the like)."

  private val OPTION2_DESCRIPTION_TEXT = "\tSome other option description - contrary to popular belief, " +
    "Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, " +
    "making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, " +
    "looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through " +
    "the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from " +
    "sections 1.10.32 and 1.10.33 of de Finibus Bonorum et Malorum (The Extremes of Good and Evil) by Cicero, " +
    "written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. " +
    "The first line of Lorem Ipsum, Lorem ipsum dolor sit amet.., comes from a line in section 1.10.32."
  //@formatter:on

    private val style = SimpleTextTableStyle(lineSeparator = "\n")
    private val printer = TextTablePrinter()

    @Test
    fun `Nothing defined`() {
        val table = Table.using(style)

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo("")
    }

    @Test
    fun `Nothing defined, but empty header`() {
        val table = Table.using(style) {
            addHeader {}
        }

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo(
            """|====
               ||  |
               |====
               |""".trimMargin()
        )
    }

    @Test
    fun `Header and few rows`() {
        val table = Table.using(style) {
            //width = 5

            addHeader {
                addCell {
                    alignCenter()
                    text = "Header"
                }
            }

            addRow {
                addCell {
                    id("firstCol", "firstRow")
                    text = "Row 1 Cell 1"
                }

                addCell {
                    text = "Row 1 Cell 2"
                }
            }

            addRow {
                addCell {
                    id("firstCol")
                    text = "Row 2 Cell 1"
                }

                addCell {
                    text = "Row 2 Cell 2"
                }
            }

            addFooter {
                addCell {
                    alignCenter()
                    text = "Footer"
                }

                addCell {
                    alignCenter()
                    alignMiddle()
                    width = 20
                    text = "page 1/1"
                }
            }

            forId("firstCol").setMinimalWidth()
            forId("firstRow").setHeight(5)
        }

        println(printer.print(table))

        assertThat(printer.print(table)).isEqualTo(
            """|===============================
         || This is header              |
         |===============================
         || Row 1 Cell 1 | Row 1 Cell 2 |
         |-------------------------------
         || Row 2 Cell 1 | Row 2 Cell 2 |
         |-------------------------------
         |""".trimMargin()
        )
    }
}

//    fun `Nothing defined, but empty header with windows lineSeparator`() {
//        val table = TextTable.builder().lineSeparator("\r\n").withHeader().build()
//        assertThat(printer.print(table)).isEqualTo("====\r\n|  |\r\n====\r\n")
//    }
//
//    fun `Id on table with empty header`() {
//        val table =
//            TextTable.builder().lineSeparator("\n").withHeader().addCell()
//                .id("headerCell")
//                .forId("headerCell")
//                .setMinimalWidth().build
//
//        assertThat(printer.print(table)).isEqualTo(
//            """|====
//               ||  |
//               |====
//               |""".trimMargin()
//        )
//    }
//

//
//    fun `Set min width on header and rows`() {
//        val table =
//            TextTable.builder.lineSeparator("\n").withHeader.addCell()
//                .id("col1")
//                .text("Header - col 1")
//                .addCell()
//                .text("Col 2")
//                .addCell()
//                .text("Col 3")
//                .addRow()
//                .addCell()
//                .id("col1")
//                .text("12")
//                .addCell()
//                .text("12")
//                .addCell()
//                .text("12345678")
//                .addRow()
//                .addCell()
//                .id("col1")
//                .text("1234")
//                .addCell()
//                .text("123")
//                .addCell()
//                .text("32")
//                .forId("col1")
//                .setMinimalWidth().build
//
//        assertThat(printer.print(table)).isEqualTo(
//            """|==================================
//         || Header - col 1 | Col 2 | Col 3 |
//         |==================================
//         || 12             | 12 | 12345678 |
//         |----------------------------------
//         || 1234           | 123 | 32      |
//         |----------------------------------
//         |""".trimMargin()
//        )
//    }
//
//    fun `Width shorter than it is possible to put texts`() {
//
//        val table =
//            TextTable.builder.tableWidth(6)
//                .addRow()
//                .addCell()
//                .text("Tekst pierwszy")
//                .addCell()
//                .text("Tekst drugi")
//                .addCell()
//                .text("Tekst trzeci")
//                .addRow()
//                .addCell()
//                .text("Dolny wiersz").build
//
//        println(table)
//
//        //TODO: it hangs up!
//    }
//
//    fun `Simple table with width set`() {
//        val table =
//            TextTable.builder.tableWidth(40)
//                .lineSeparator("\n")
//                .addRow()
//                .addCell()
//                .text("Tekst pierwszy")
//                .addCell()
//                .text("Tekst drugi")
//                .addCell()
//                .text("Tekst trzeci")
//                .addRow()
//                .addCell()
//                .text("Dolny wiersz").build
//
//        assertThat(printer.print(table)).isEqualTo(
//            """|----------------------------------------
//         || Tekst      | Tekst      | Tekst      |
//         || pierwszy   | drugi      | trzeci     |
//         |----------------------------------------
//         || Dolny wiersz                         |
//         |----------------------------------------
//         |""".trimMargin()
//        )
//    }
//
//    fun `Simple table without width set`() {
//
//        val table =
//            TextTable.builder()
//                .lineSeparator("\n")
//                .addRow()
//                .addCell()
//                .text("Tekst pierwszy")
//                .addCell()
//                .text("Tekst drugi")
//                .addCell()
//                .text("Text trzeci")
//                .addRow()
//                .addCell()
//                .text("Dolny wiersz").build
//
//        println(table.toString)
//
//        assertThat(printer.print(table)).isEqualTo(
//            """|----------------------------------------------
//         || Tekst pierwszy | Tekst drugi | Text trzeci |
//         |----------------------------------------------
//         || Dolny wiersz                               |
//         |----------------------------------------------
//         |""".trimMargin()
//        )
//    }
//
//    fun `Simple table without width set and with multiline text`() {
//
//        val table =
//            TextTable.builder()
//                .lineSeparator("\n")
//                .addRow()
//                .addCell()
//                .text("Tekst pierwszy")
//                .addCell()
//                .text("Tekst drugi\nLine 1\nLine2\nBardzo długi line3")
//                .addCell()
//                .text("Tekst trzeci")
//                .addRow()
//                .addCell()
//                .text("Dolny wiersz").build
//
//        println(table.toString)
//
//        assertThat(printer.print(table)).isEqualTo(
//            """------------------------------------------------------
//        || Tekst pierwszy | Tekst drugi        | Tekst trzeci |
//        ||                | Line 1             |              |
//        ||                | Line2              |              |
//        ||                | Bardzo długi line3 |              |
//        |------------------------------------------------------
//        || Dolny wiersz                                       |
//        |------------------------------------------------------
//        |""".trimMargin()
//        )
//    }
//
//    fun `Simple table without width set and with multiline text1`() {
//        val table =
//            TextTable.builder()
//                .lineSeparator("\n")
//                .addRow()
//                .addCell()
//                .text("Tekst pierwszy")
//                .addCell()
//                .text(BANK_ACCOUNT_TEXT).alignCenter.addCell()
//                .text("Text trzeci")
//                .addRow()
//                .addCell()
//                .text("Dolny wiersz").build
//
//        println(table.toString)
//
//        assertThat(printer.print(table)).isEqualTo(
//            """-----------------------------------------------------------------------
//        || Tekst pierwszy |          Rachunek bankowy:           | Text trzeci |
//        ||                | CRK ProEcclesia SCh - "ProCracovia"  |             |
//        ||                |           ul. Puławska 114           |             |
//        ||                |           02-620 Warszawa            |             |
//        ||                |                                      |             |
//        ||                | PLN 78 1240 1112 1111 0010 0909 5620 |             |
//        ||                |            Bank Pekao SA             |             |
//        ||                |       VIII odział w Warszawie        |             |
//        |-----------------------------------------------------------------------
//        || Dolny wiersz                                                        |
//        |-----------------------------------------------------------------------
//        |""".trimMargin()
//        )
//    }
//
//    fun `Simple table without width set and with multiline text and multiple aligned rows`() {
//
//        val table =
//            TextTable.builder()
//                .lineSeparator("\n")
//                .addRow()
//                .addCell()
//                .id("left")
//                .text("Tekst pierwszy")
//                .addCell()
//                .id("central")
//                .text(BANK_ACCOUNT_TEXT).alignCenter.addCell()
//                .text("Text trzeci")
//                .addRow()
//                .addCell()
//                .id("left")
//                .text("column A")
//                .addCell()
//                .id("central")
//                .text("column B").alignCenter.addCell()
//                .text("column C")
//                .addRow()
//                .addCell()
//                .text("Dolny wiersz")
//                .forId("central")
//                .setMinimalWidth()
//                .forId("left")
//                .setMinimalWidth().build
//
//        println(table.toString)
//
//        assertThat(printer.print(table)).isEqualTo(
//            """-----------------------------------------------------------------------
//        || Tekst pierwszy |          Rachunek bankowy:           | Text trzeci |
//        ||                | CRK ProEcclesia SCh - "ProCracovia"  |             |
//        ||                |           ul. Puławska 114           |             |
//        ||                |           02-620 Warszawa            |             |
//        ||                |                                      |             |
//        ||                | PLN 78 1240 1112 1111 0010 0909 5620 |             |
//        ||                |            Bank Pekao SA             |             |
//        ||                |       VIII odział w Warszawie        |             |
//        |-----------------------------------------------------------------------
//        || column A       |       column B                       | column C    |
//        |-----------------------------------------------------------------------
//        || Dolny wiersz                                                        |
//        |-----------------------------------------------------------------------
//        |""".trimMargin()
//        )
//    }
//
//    fun `Just show the table`() {
//        val optionName = "optionName"
//
//        //http://en.wikipedia.org/wiki/Box-drawing_character#Unicode
//
//        val table = TextTable.builder()
//            .tableWidth(110)
//            //.horizontalLine( '─' )
//            //.horizontalLine( '\u2501' )
//            //.verticalLine( '│' )
//            //.noBorders
//            .addRow()
//            .addCell()
//            .text(LOREM_IPSUM_TEXT)
//            .alignLeft()
//            .justify()
//            .addRow()
//            .addRow()
//            .addCell()
//            .textWidth(2)
//            .addCell()
//            .id(optionName)
//            .text("First option")
//            .addCell()
//            .text(":")
//            .textWidth(1)
//            .leftIndent(1)
//            .rightIndent(1)
//            .addCell()
//            .text(OPTION1_DESCRIPTION_TEXT).justify.addRow()
//            .addCell()
//            .textWidth(2)
//            .addCell()
//            .id(optionName)
//            .text("Some other option")
//            .addCell()
//            .text(" : ")
//            .textWidth(3)
//            .leftIndent(0)
//            .rightIndent(0)
//            .addCell()
//            .text(OPTION2_DESCRIPTION_TEXT)
//            .addRow()
//            .forId(optionName)
//            .setMinimalWidth().build
//
//        println(table)
//    }
