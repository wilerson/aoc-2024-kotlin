
val days = listOf(
    ::day01,
    ::day02,
    ::day03,
    ::day04,
    ::day05,
    ::day06,
    ::day07,
    ::day08,
)

fun main(args: Array<String>) {
    val day = args.firstOrNull()?.toIntOrNull() ?: days.size
    days[day - 1]()
}
