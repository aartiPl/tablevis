package net.igsoft.tablevis.util

//
object Utils {
    //  fun appendValue<T>(collection: mutable.Buffer<T>, value: T): T = {
//    collection += value
//    value
//  }
//
//TODO: merge below two functions
    fun distributeEvenly(valueNumber: Int, adjustment: Int): List<Int> {
        val values = mutableListOf<Int>()

        if (valueNumber == 0) {
            return values
        }

        if (adjustment == 0) {
            return values
        }

        //Assign additional space to cells without width
        val singleValue = adjustment / valueNumber

        for (value in 0 until valueNumber - 1) {
            values += singleValue
        }

        values += adjustment - values.sum()

        return values
    }

    fun distributeProportionally(sumOfWeights: Int, weights: List<Int>, adjustment: Int): List<Int> {
        val values = mutableListOf<Int>()

        if (adjustment == 0) {
            return values
        }

        for (index in 0 until weights.size - 1) {
            val singleValue = (weights[index] / sumOfWeights) * adjustment

            values += singleValue
        }

        values += adjustment - values.sum()

        return values
    }
}
