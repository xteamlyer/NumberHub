package app.myzel394.numberhub.feature.converter

import kotlin.math.ceil

// So here's the problem. If we just go down from 3 downto 0 with columns 2, this results in:
// [3, 2]
// [1, 0]
// While this is correct, the ordering is incorrect. It should be
// [2, 3]
// [0, 1]
// TODO: Add support for rtl languages
internal fun createSortedArray(range: IntRange, columns: Int): List<Int> {
    val result = mutableListOf<Int>()
    val rows = ceil(range.count().toDouble() / columns.toDouble()).toInt()
    for (row in rows downTo 0) {
        for (column in 0 until columns) {
            val index = row * columns + column + range.first
            if (index <= range.last) {
                result.add(index)
            }
        }
    }

    return result
}
