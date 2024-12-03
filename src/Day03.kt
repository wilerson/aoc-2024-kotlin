
fun day03() {

    val regex = """mul\(\d{1,3}?,\d{1,3}?\)""".toRegex()
    val regex2 = """(mul\(\d{1,3}?,\d{1,3}?\))|(do\(\))|(don't\(\))""".toRegex()

    fun part1(input: List<String>): Int {
        return input.sumOf { section ->
            regex.findAll(section).map { match ->
                val (a, b) = match.value.drop(4).dropLast(1).split(',').map { it.toInt() }

                a * b
            }.sum()
        }
    }

    fun part2(input: List<String>): Int {
        var process = true
        return input.sumOf { section ->
            regex2.findAll(section).map { match ->
                val value = match.value
                if (value == "do()") {
                    process = true
                    0
                } else if (value == "don't()") {
                    process = false
                    0
                } else if (process) {
                    val (a, b) = value.drop(4).dropLast(1).split(',').map { it.toInt() }

                    a * b
                } else {
                    0
                }
            }.sum()
        }
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)
    check(part2(readInput("Day03_test2")) == 48)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
