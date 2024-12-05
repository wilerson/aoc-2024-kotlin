
fun day04() {
    val regex1 = """XMAS""".toRegex()
    val regex2 = """SAMX""".toRegex()

    fun part1(input: List<String>): Int = input.mapIndexed { i, line ->
        val verticals = if (i < input.size - 3) {
            line.mapIndexed { j, c ->
                val downString = sequenceOf(input[i + 1][j], input[i + 2][j], input[i + 3][j]).joinToString("")
                val leftString = sequenceOf(
                    input[i + 1].getOrNull(j - 1),
                    input[i + 2].getOrNull(j - 2),
                    input[i + 3].getOrNull(j - 3)
                ).filterNotNull().joinToString("")
                val rightString = sequenceOf(
                    input[i + 1].getOrNull(j + 1),
                    input[i + 2].getOrNull(j + 2),
                    input[i + 3].getOrNull(j + 3)
                ).filterNotNull().joinToString("")


                when (c) {
                    'X' -> {
                        val down = if (downString == "MAS") 1 else 0
                        val left = if (leftString == "MAS") 1 else 0
                        val right = if (rightString == "MAS") 1 else 0

                        down + left + right
                    }
                    'S' -> {
                        val down = if (downString == "AMX") 1 else 0
                        val left = if (leftString == "AMX") 1 else 0
                        val right = if (rightString == "AMX") 1 else 0

                        down + left + right
                    }
                    else -> 0
                }
            }.sum()
        } else 0

        verticals + regex1.findAll(line).count() + regex2.findAll(line).count()
    }.sum()

    fun part2(input: List<String>): Int {
        val lineLength = input[0].length
        return input.windowed(3).map { strings ->
            var count = 0
            if(strings.size == 3) {
                for (j in 0..lineLength - 3) {
                    if (strings[1][j + 1] == 'A') {
                        val firstLine = sequenceOf(strings[0][j], strings[0][j + 2]).joinToString("")
                        val thirdLine = sequenceOf(strings[2][j], strings[2][j + 2]).joinToString("")
                        if (firstLine == "MS" && thirdLine == "MS") {
                            count++
                        }

                        if (firstLine == "SM" && thirdLine == "SM") {
                            count++
                        }

                        if (firstLine == "SS" && thirdLine == "MM") {
                            count++
                        }

                        if (firstLine == "MM" && thirdLine == "SS") {
                            count++
                        }
                    }
                }
            }

            count
        }.sum()
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18) { "FAIL: ${part1(testInput)}" }
    check(part2(testInput) == 9)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
