package net.igsoft.tablevis.example

import net.igsoft.tablevis.TableBuilder
import net.igsoft.tablevis.printer.text.TextTablePrinter
import net.igsoft.tablevis.style.text.BoxTextTableStyleSet
import net.igsoft.tablevis.style.text.SimpleTextTableStyleSet
import net.igsoft.tablevis.style.text.TextTableBorder

fun main() {
    println("Let's print some tables...")

    //------------------------------------------------------------------------------------------------------------------

    // tag::simplest_table[]
    val printer = TextTablePrinter()

    var table = TableBuilder(BoxTextTableStyleSet()) {
        row {}
    }.build()

    println(printer.print(table))
    // end::simplest_table[]

    //------------------------------------------------------------------------------------------------------------------

    // tag::complicated_table[]
    table = TableBuilder(BoxTextTableStyleSet()) {
        row(styleSet.header) {
            cell {
                value = "Consecutive number"
            }

            cell {
                value = "Month name"
            }
        }

        row {
            cell {
                value = "1"
            }

            cell {
                value = "January"
            }
        }

        row {
            cell {
                value = "2"
            }

            cell {
                value = "February"
            }
        }
    }.build()

    println(printer.print(table))
    // end::complicated_table[]

    //------------------------------------------------------------------------------------------------------------------

    table = TableBuilder(BoxTextTableStyleSet()) {
        row(styleSet.header) {
            cell {
                value = "Consecutive number"
            }

            cell {
                value = "Month name"
            }
        }

        row {
            cell {
                value = "1"
            }

            cell {
                value = "January"
            }
        }

        row {
            cell {
                value = "2"
            }

            cell {
                value = "February"
            }
        }

        forId("col-1").setMinimalWidth()
    }.build()

    println(printer.print(table))

    //------------------------------------------------------------------------------------------------------------------

    table = TableBuilder(BoxTextTableStyleSet()) {
        row(styleSet.header) {
            cell {
                value = "Consecutive number"
            }

            cell {
                value = "Month name"
            }
        }

        row {
            cell {
                value = "1"
            }

            cell {
                value = "January"
            }
        }

        row {
            cell {
                value = "2"
            }

            cell {
                value = "February"
            }
        }

        forId("col-1").setWidth(11)
    }.build()

    println(printer.print(table))

    //------------------------------------------------------------------------------------------------------------------

    // tag::financial_table[]
    val months = listOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )
    val profits = listOf(12000, 40000, 29000, 18500, 41300, 21650, 30150, 29999, 24700, 22890, 51135, 49134)

    table = TableBuilder(BoxTextTableStyleSet()) {
        width = 60

        row(styleSet.header) {
            cell {
                id("#")
                value = "Consecutive number"
            }
            cell {
                value = "Month name"
            }
            cell {
                id("profit")
                value = "Profit"
            }
        }

        for (i in 0 until 12) {
            row {
                cell {
                    id("#")
                    value = i + 1
                }
                cell {
                    value = months[i]
                }
                cell {
                    id("profit")
                    value = String.format("%,d $", profits[i])
                }
            }
        }

        row(styleSet.footer) {
            cell {
                value = "Sum"
            }
            cell {
                id("profit")
                value = String.format("%,d $", profits.sum())
            }
        }

        forId("#").setWidth(6).center()
        forId("profit").setWidth(12).center()
    }.build()

    println(printer.print(table))
    // end::financial_table[]

    //------------------------------------------------------------------------------------------------------------------

    // tag::simple_text_table[]
    val simpleTextTable = TableBuilder(SimpleTextTableStyleSet(lineSeparator = "\n")) {
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

    println(printer.print(simpleTextTable))
    // end::simple_text_table[]

    //------------------------------------------------------------------------------------------------------------------

    // tag::fancy_borders_table[]
    val fancyBordersTextTable = TableBuilder(BoxTextTableStyleSet()) {
        center()

        row {
            cell {
                bottomBorder = TextTableBorder.noBorder
                value = 1
            }
            cell {
                rightBorder = TextTableBorder.noBorder
                value = 2
            }
            cell {
                bottomBorder = TextTableBorder.noBorder
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
                rightBorder = TextTableBorder.noBorder
                value = 8
            }
            cell {
                value = 9
            }
        }
    }.build()

    println(printer.print(fancyBordersTextTable))
    // end::fancy_borders_table[]

    //------------------------------------------------------------------------------------------------------------------

    // tag::justifying_text_table[]
    //@formatter:off
    val text = "\tKotlin is a modern but already mature programming language aimed to make developers happier. " +
               "It’s concise, safe, interoperable with Java and other languages, and provides many ways to reuse " +
               "code between multiple platforms for productive programming.\n\n\tPick it up to start building " +
               "powerful applications!"
    //@formatter:on

    val justifyingTextTable = TableBuilder(BoxTextTableStyleSet()) {
        width = 81

        row {
            cell {
                value = text
            }
            cell {
                justify()
                value = text
            }
        }
    }.build()

    println(printer.print(justifyingTextTable))
    // end::justifying_text_table[]

    //------------------------------------------------------------------------------------------------------------------

    println("Thanks folks! That was awesome!")
}
