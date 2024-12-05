
fun day04() {
    val regex1 = """XMAS""".toRegex()
    val regex2 = """SAMX""".toRegex()
    fun part1(input: List<String>): Int {
        return input.mapIndexed { i, line ->
            val verticals = if (i < input.size - 3) {
                val v = line.mapIndexed { j, c ->
                    if (c == 'X') {
                        val down = if (input[i+1][j] == 'M' && input[i + 2][j] == 'A' && input[i + 3][j] == 'S') {
                            1
                        } else 0

                        val left = if (j >= 3) {
                            if (input[i+1][j-1] == 'M' && input[i + 2][j-2] == 'A' && input[i + 3][j-3] == 'S') {
                                1
                            } else 0
                        } else 0

                        val right = if (j < line.length - 3) {
                            if (input[i+1][j+1] == 'M' && input[i + 2][j+2] == 'A' && input[i + 3][j+3] == 'S') {
                                1
                            } else 0
                        } else 0

                        down + left + right
                    } else if (c == 'S') {
                        val down = if (input[i+1][j] == 'A' && input[i + 2][j] == 'M' && input[i + 3][j] == 'X') {
                            1
                        } else 0

                        val left = if (j >= 3) {
                            if (input[i+1][j-1] == 'A' && input[i + 2][j-2] == 'M' && input[i + 3][j-3] == 'X') {
                                1
                            } else 0
                        } else 0

                        val right = if (j < line.length - 3) {
                            if (input[i+1][j+1] == 'A' && input[i + 2][j+2] == 'M' && input[i + 3][j+3] == 'X') {
                                1
                            } else 0
                        } else 0

                        down + left + right
                    } else 0
                }.sum()

                v
            } else 0

            verticals + regex1.findAll(line).count() + regex2.findAll(line).count()
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val lineLength = input[0].length
        return input.windowed(3).map { strings ->
            var count = 0
            if(strings.size == 3) {
                for (j in 0..lineLength - 3) {
                    if (strings[1][j + 1] == 'A') {
                        if ((strings[0][j] == 'M' && strings[0][j + 2] == 'S')) {
                            if (strings[2][j] == 'M' && strings[2][j + 2] == 'S') {
                                count++
                            }
                        }

                        if ((strings[0][j] == 'S' && strings[0][j + 2] == 'M')) {
                            if (strings[2][j] == 'S' && strings[2][j + 2] == 'M') {
                                count++
                            }
                        }

                        if ((strings[0][j] == 'S' && strings[0][j + 2] == 'S')) {
                            if (strings[2][j] == 'M' && strings[2][j + 2] == 'M') {
                                count++
                            }
                        }

                        if ((strings[0][j] == 'M' && strings[0][j + 2] == 'M')) {
                            if (strings[2][j] == 'S' && strings[2][j + 2] == 'S') {
                                count++
                            }
                        }
                    }
                }
            }

            count
        }.sum()
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18) { "FAIL ${part1(testInput)}" }
    check(part2(testInput) == 9) { "FAIL ${part2(testInput)}" }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
