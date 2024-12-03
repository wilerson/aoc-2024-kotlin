import kotlin.math.absoluteValue

fun day01() {
    fun extractLists(input: List<String>) = input.asSequence()
        .filter { it.isNotEmpty() }
        .map { it.split("   ") }
        .map { it[0].toInt() to it[1].toInt() }
        .fold(mutableListOf<Int>() to mutableListOf<Int>()) { (acc1, acc2), (a, b) ->
            acc1.add(a)
            acc2.add(b)
            acc1 to acc2
        }

    fun part1(input: List<String>): Int {
        val (listA, listB) = extractLists(input)

        return listA.sorted().zip(listB.sorted()).sumOf { (x, y) -> (x - y).absoluteValue }
    }

    fun part2(input: List<String>): Int {
        val (listA, listB) = extractLists(input)

        val tally = listB.groupingBy { it }.eachCount()

        return listA.sumOf { a -> a * (tally[a] ?: 0) }
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
