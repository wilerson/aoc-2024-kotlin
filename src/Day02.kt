import kotlin.math.absoluteValue

fun main() {
    fun extractLists(input: List<String>) = input.asSequence()
        .filter { it.isNotEmpty() }
        .map { it.split(' ') }
        .map { it.map(String::toInt) }

    fun filterSafeReports(it: List<Int>) = ((it == it.sorted() || it == it.sortedDescending())
        && it.distinct().size == it.size
        && it.windowed(2).none { (a, b) -> (b - a).absoluteValue > 3 })

    fun part1List(input: List<String>) = extractLists(input).filter(::filterSafeReports)

    fun part1(input: List<String>) = part1List(input).count()

    fun part2List(input: List<String>) = extractLists(input).filter {
        if (filterSafeReports(it)) {
            true
        } else {
            var r = false
            it.forEachIndexed { i, _ ->
                val l2 = it.toMutableList()
                l2.removeAt(i)
                if (!r && filterSafeReports(l2)) {
                    r = true
                }
            }

            r
        }
    }


    fun part2(input: List<String>): Int {
        return part2List(input).count()
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
