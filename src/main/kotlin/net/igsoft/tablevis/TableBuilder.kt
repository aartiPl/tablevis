package net.igsoft.tablevis

import net.igsoft.tablevis.builder.TableDef
import net.igsoft.tablevis.model.Table
import net.igsoft.tablevis.style.Style
import net.igsoft.tablevis.style.StyleSet
import net.igsoft.tablevis.visitor.*

class TableBuilder<STYLE : Style, STYLE_SET : StyleSet<STYLE>>(
    private val styleSet: STYLE_SET, private val block: TableDef<STYLE, STYLE_SET>.() -> Unit = {}
) {
    fun build(): Table<STYLE_SET> {
        val table = TableDef(styleSet).apply(block)

        //Make sure there is at least one cell in a row...
        //Map cellIds to cells... Add default row and col names (col-1, row-1, etc.)
        table.applyVisitor(CellIdResolver())

        //Do minimal calculations on texts and resolution of cells...
        table.applyVisitor(BasePropertiesResolver())

        //Execute deferred functions...
        table.applyVisitor(DeferredFunctionsResolver())

        //Calculate widths
        table.applyVisitor(WidthResolver())

        //Calculate cell sizes so that they match table size
        table.applyVisitor(TextAdjustingResolver())

        //Calculate heights
        table.applyVisitor(HeightResolver())

        return table.applyVisitor(BuilderResolver(styleSet))
    }
}
