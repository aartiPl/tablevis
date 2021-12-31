package net.igsoft.tablevis.example

import net.igsoft.tablevis.TableBuilder
import net.igsoft.tablevis.printer.TextTablePrinter
import net.igsoft.tablevis.style.BoxTextStyleSet

fun main() {
    println("Let's print some tables...")

    //------------------------------------------------------------------------------------------------------------------

    // tag::simplest_table[]
    val printer = TextTablePrinter()

    var table = TableBuilder(BoxTextStyleSet()) {
        row {}
    }.build()

    println(printer.print(table))
    // end::simplest_table[]

    //------------------------------------------------------------------------------------------------------------------

    // tag::complicated_table[]
    table = TableBuilder(BoxTextStyleSet()) {
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

    table = TableBuilder(BoxTextStyleSet()) {
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

    table = TableBuilder(BoxTextStyleSet()) {
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
    val months = listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
    val profits = listOf(12000, 40000, 29000, 18500, 41300, 21650, 30150, 29999, 24700, 22890, 51135, 49134)
    table = TableBuilder(BoxTextStyleSet()) {
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

        for(i in 0 until 12) {
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

    println("Thanks folks! That was awesome!")
}
