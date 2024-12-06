
fun day06() {
    val guards = listOf('^', '>', 'v', '<')

    fun findGuard(input: List<String>): Triple<Int, Int, Char> {
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (guards.contains(input[i][j])) {
                    return Triple(i, j, input[i][j])
                }
            }
        }
        return Triple(-1, -1, Char.MIN_VALUE)
    }

    fun turnGuard(guard: Char) = when(guard) {
        '^' -> '>'
        '>' -> 'v'
        'v' -> '<'
        '<' -> '^'
        else -> Char.MIN_VALUE
    }

    fun part1(input: List<String>): Int {
        val (y, x, guard) = findGuard(input)

        return if (y != -1 && x != -1) {
            val positions = mutableSetOf(y to x)

            fun findObstacle(input: List<String>, x: Int, y: Int, guard: Char): Pair<Int, Int> {
                when(guard) {
                    '^' -> {
                        for (i in y downTo 0) {
                            if (input[i][x] == '#') {
                                return i+1 to x
                            }
                            positions.add(i to x)
                        }
                    }

                    '>' -> {
                        for (j in x until input[0].length) {
                            if (input[y][j] == '#') {
                                return y to j-1
                            }
                            positions.add(y to j)
                        }
                    }

                    'v' -> {
                        for (i in y until input.size) {
                            if (input[i][x] == '#') {
                                return i-1 to x
                            }
                            positions.add(i to x)
                        }
                    }

                    '<' -> {
                        for (j in x downTo 0) {
                            if (input[y][j] == '#') {
                                return y to j+1
                            }
                            positions.add(y to j)
                        }
                    }
                }

                return -1 to -1
            }

            var (i, j) = findObstacle(input, x, y, guard)
            var guard = turnGuard(guard)
            while(i != -1 && j != -1) {

                val k = findObstacle(input, y=i, x=j, guard=guard)
                i = k.first
                j = k.second
                guard = turnGuard(guard)
            }


            positions.size
        } else 0
    }

    fun part2(input: List<String>): Int {
        val firstPosition = findGuard(input)
        val (posY, posX, guard) = firstPosition
        var obstructedPositions = 0
        var obstructedPosition = 0 to 0
        val visitedPositions = mutableSetOf(firstPosition)

        fun findObstacle(input: List<String>, x: Int, y: Int, guard: Char): Pair<Int, Int> {
            when(guard) {
                '^' -> {
                    for (i in y downTo 0) {
                        if (input[i][x] == '#' || i to x == obstructedPosition) {
                            return i+1 to x
                        }
                        visitedPositions.add(Triple(i, x, guard))
                    }
                }

                '>' -> {
                    for (j in x until input[0].length) {
                        if (input[y][j] == '#' || y to j == obstructedPosition) {
                            return y to j-1
                        }
                        visitedPositions.add(Triple(y, j, guard))
                    }
                }

                'v' -> {
                    for (i in y until input.size) {
                        if (input[i][x] == '#' || i to x == obstructedPosition) {
                            return i-1 to x
                        }
                        visitedPositions.add(Triple(i, x, guard))
                    }
                }

                '<' -> {
                    for (j in x downTo 0) {
                        if (input[y][j] == '#' || y to j == obstructedPosition) {
                            return y to j+1
                        }
                        visitedPositions.add(Triple(y, j, guard))
                    }
                }
            }

            return -1 to -1
        }

        for (y in input.indices) {
            for (x in input[y].indices) {
                var previousSize = visitedPositions.size
                obstructedPosition = y to x

                var (i, j) = findObstacle(input, posX, posY, guard)
                var guard = turnGuard(guard)
                while(i != -1 && j != -1) {

                    val k = findObstacle(input, y=i, x=j, guard=guard)
                    i = k.first
                    j = k.second
                    guard = turnGuard(guard)

                    if (visitedPositions.size == previousSize) {
                        obstructedPositions++
                        break
                    }
                    previousSize = visitedPositions.size
                }
                visitedPositions.clear()
            }
        }


        return obstructedPositions
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
