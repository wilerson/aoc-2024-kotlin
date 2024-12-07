
fun day07() {
    fun compute(input: List<String>, concat: Boolean = false) = input.sumOf { row ->
        val (result, numberList) = row.split(':').map { it.trim() }
        val numbers = numberList.trim().split(' ').map { it.toLong() }

        val possibleResults = mutableListOf<Long>()

        numbers.forEachIndexed { i, value ->
            if (i == 0) possibleResults.add(value)
            else {
                for (j in possibleResults.indices) {
                    val current = possibleResults[j]
                    possibleResults[j] += value
                    possibleResults.add(current * value)
                    if (concat) possibleResults.add("$current$value".toLong())
                }
            }
        }

        if (result.toLong() in possibleResults) result.toLong()
        else 0
    }

    fun part1(input: List<String>) = compute(input)

    fun part2(input: List<String>) = compute(input, true)

    val testInput = readInput("Day07_test")
    val test1 = part1(testInput)
    check(test1 == 3749L) { "FAIL1 $test1"}
    val test2 = part2(testInput)
    check(test2 == 11387L) { "FAIL2 $test2"}

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
