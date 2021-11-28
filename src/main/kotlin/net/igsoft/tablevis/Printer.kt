package net.igsoft.tablevis

interface Printer<T> {
    fun print(table: T): String
}
