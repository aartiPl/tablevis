package net.igsoft.tablevis

//
//object Utils {
//  fun appendValue<T>(collection: mutable.Buffer<T>, value: T): T = {
//    collection += value
//    value
//  }
//
//  //TODO: merge below two functions
//  fun distributeEvenly(valueNumber: Int, adjustment: Int): List[Int] = {
//    val values = mutable.Buffer[Int]()
//
//    if (valueNumber == 0) {
//      return values.toList
//    }
//
//    if (adjustment == 0) {
//      return values.toList
//    }
//
//    //Assign additional space to cells without width
//    val singleValue = adjustment / valueNumber
//
//    for (value <- 0 until valueNumber - 1) {
//      values += singleValue
//    }
//
//    values += adjustment - values.sum
//
//    values.toList
//  }
//
//  fun distributeProportionally(sumOfWeights: Int, weights: List[Int], adjustment: Int): List[Int] = {
//    val values = mutable.Buffer[Int]()
//
//    if (adjustment == 0) {
//      return values.toList
//    }
//
//    for (index <- 0 until weights.size - 1) {
//      val singleValue = (weights(index) / sumOfWeights) * adjustment
//
//      values += singleValue
//    }
//
//    values += adjustment - values.sum
//
//    values.toList
//  }
//
//  fun maxLineSizeBasedOnText(text : String): Int = {
//    var maxLineSize = 0
//
//    for(line <- text.split("\\r?\\n")) {
//      maxLineSize = if (line.size > maxLineSize) line.size else maxLineSize
//    }
//
//    maxLineSize
//  }
//}
