
fun day08() {

    fun List<String>.findCharacterPositions(character: Char) = flatMapIndexed { i, line ->
        line.mapIndexed { j, c ->
            if (c == character) i to j else null
        }.filterNotNull()
    }

    fun List<String>.findUniqueCharacters() = flatMap { it.toCharArray().toSet() }.distinct()

    fun before(pos1: Pair<Int, Int>, pos2: Pair<Int, Int>): Pair<Int, Int> {
        val diffY = pos2.first - pos1.first
        val diffX = pos2.second - pos1.second
        return pos1.first - diffY to pos1.second - diffX
    }

    fun after(pos1: Pair<Int, Int>, pos2: Pair<Int, Int>): Pair<Int, Int> {
        val diffY = pos2.first - pos1.first
        val diffX = pos2.second - pos1.second
        return pos2.first + diffY to pos2.second + diffX
    }

    fun Pair<Int, Int>.inBounds(input: List<String>) =
        first >= 0 && second >= 0 && first < input.size && second < input.first().length

    fun part1(input: List<String>): Int {
        val chars = input.findUniqueCharacters().filter { it != '.' }
        return chars.flatMap { c ->
            val positions = input.findCharacterPositions(c)
            positions.flatMapIndexed { i, pos1 ->
                val antinodes = mutableListOf<Pair<Int, Int>>()
                for (j in i+1 until positions.size) {
                    val pos2 = positions[j]

                    val before = before(pos1, pos2)
                    val after = after(pos1, pos2)
                    if (before.inBounds(input)) antinodes.add(before)
                    if (after.inBounds(input)) antinodes.add(after)
                }

                antinodes
            }
        }.distinct().count()
    }

    fun part2(input: List<String>): Int {
        fun findAllBeforeInBounds(
            pos1: Pair<Int, Int>,
            pos2: Pair<Int, Int>,
            input: List<String>,
        ): List<Pair<Int, Int>> {
            val before = before(pos1, pos2)

            return if (before.inBounds(input)) {
                listOf(before) + findAllBeforeInBounds(before, pos1, input)
            } else {
                emptyList()
            }
        }

        fun findAllAfterInBounds(
            pos1: Pair<Int, Int>,
            pos2: Pair<Int, Int>,
            input: List<String>,
        ): List<Pair<Int, Int>> {
            val after = after(pos1, pos2)

            return if (after.inBounds(input)) {
                listOf(after) + findAllAfterInBounds(pos2, after, input)
            } else {
                emptyList()
            }
        }

        val chars = input.findUniqueCharacters().filter { it != '.' }
        return chars.flatMap { c ->
            val positions = input.findCharacterPositions(c)
            positions.flatMapIndexed { i, pos1 ->
                val antinodes = mutableListOf<Pair<Int, Int>>()
                for (j in i+1 until positions.size) {
                    val pos2 = positions[j]

                    antinodes.addAll(findAllBeforeInBounds(pos1, pos2, input))
                    antinodes.addAll(findAllAfterInBounds(pos1, pos2, input))
                }

                antinodes
            } + if (positions.size > 1) positions else emptyList()
        }.distinct().count()
    }

    val testInput = readInput("Day08_test")
    val test1 = part1(testInput)
    check(test1 == 14) { "FAIL1 $test1" }
    val test2 = part2(testInput)
    check(test2 == 34) { "FAIL2 $test2" }

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
