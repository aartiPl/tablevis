package net.igsoft.tablevis.visitor

import net.igsoft.tablevis.builder.CellProperties
import net.igsoft.tablevis.builder.RowProperties
import net.igsoft.tablevis.builder.TableProperties
import net.igsoft.tablevis.style.Style

class DeferredFunctionsResolver<STYLE : Style> : Visitor<STYLE, Unit, Unit, Unit> {
    override fun visit(tableProperties: TableProperties<STYLE>) {
        for(operation in tableProperties.globalOperations) {
            operation(tableProperties)
        }

        for (entry in tableProperties.cellsPerId.entries) {
            val cellsToApply = entry.value
            val functionsToExecute = tableProperties.idOperations[entry.key] ?: emptySet()
            functionsToExecute.forEach { fn ->
                fn(cellsToApply)
            }
        }
    }

    override fun visit(rowProperties: RowProperties<STYLE>) {}
    override fun visit(cellProperties: CellProperties<STYLE>) {}
}
