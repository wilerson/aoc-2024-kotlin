
val days = listOf(
    ::day01,
    ::day02,
    ::day03,
)

fun main(args: Array<String>) {
    val day = args.firstOrNull()?.toIntOrNull() ?: days.size
    days[day - 1]()
}
