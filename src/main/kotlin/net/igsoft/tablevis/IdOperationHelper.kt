package net.igsoft.tablevis

//object IdOperationHelper {
//  implicit def delegateToTable(operationBuilder: IdOperationHelper): TextTableBuilder = operationBuilder.table
//}
//
//class IdOperationHelper(val table: TextTableBuilder, id: Any) {
//  private val setMinimalWidthFn: Function[Set[TextCellBuilder], Unit] = cells => {
//    var maxWidth = 0
//
//    for (cell <- cells) {
//      val currentWidth = Utils.maxLineSizeBasedOnText(cell.cellText)
//      maxWidth = if (currentWidth > maxWidth) currentWidth else maxWidth
//    }
//
//    cells.foreach(cell => cell.cellTextWidth = Some(maxWidth))
//  }
//
//  def setMinimalWidth(): IdOperationHelper = {
//    table.addOperation(id, setMinimalWidthFn)
//    this
//  }
//}
