
fun day05() {
    fun String.isCorrect(rule: String): Boolean {
        val (first, second) = rule.split('|')

        val firstIndex = indexOf(first)
        val secondIndex = indexOf(second)

        return firstIndex == -1 || secondIndex == -1 || firstIndex < secondIndex
    }

    fun String.isCorrect(rules: List<String>) = rules.all { this.isCorrect(it) }

    fun ruleDoesNotApply(v1: String, v2: String, first: String, second: String) =
        (v1 != first && v1 != second) || (v2 != first && v2 != second)

    fun valuesInOrder(
        val1: String,
        val2: String,
        pos1: Int,
        pos2: Int,
        first: String,
        second: String,
    ) = if (ruleDoesNotApply(val1, val2, first, second)) {
        true
    } else {
        if (val1 == first && val2 == second) {
            pos1 >= pos2
        } else {
            pos2 >= pos1
        }
    }

    fun String.valuesInRule(v1: String, v2: String, p1: Int, p2: Int): Boolean {
        val (first, second) = split('|')

        return valuesInOrder(v1, v2, p1, p2, first, second)
    }

    fun part1(input: List<String>): Int {
        val (rules, updates) = input.filter { it.isNotEmpty() }.partition { '|' in it }

        return updates.filter { it.isCorrect(rules) }.sumOf {
            val update = it.split(',')
            update[update.size / 2].toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val (rules, updates) = input.filter { it.isNotEmpty() }.partition { '|' in it }
        return updates.filterNot { it.isCorrect(rules) }.sumOf { updateList ->
            val update = updateList.split(',').map { it to updateList.indexOf(it) }
                .sortedWith { (v1, p1), (v2, p2) ->
                    if (rules.all { it.valuesInRule(v1, v2, p1, p2) }) 1 else -1
                }.map { it.first }

           update[update.size / 2].toInt()
        }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
