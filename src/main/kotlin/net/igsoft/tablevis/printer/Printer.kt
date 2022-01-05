package net.igsoft.tablevis.printer

interface Printer<T> {
    fun print(table: T): String
}
