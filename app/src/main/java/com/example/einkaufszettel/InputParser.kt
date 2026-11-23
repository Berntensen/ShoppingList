package com.example.einkaufszettel

data class ParsedInput(val name: String, val quantity: Int)

object InputParser {
    // Quantity only at the start: "3 Milch", "3x Milch", "3 x Milch", "x3 Milch"
    private val pattern = Regex(
        """^\s*(?:(\d{1,3})\s*[x×]?|[x×]\s*(\d{1,3}))\s+(.+?)\s*$""",
        RegexOption.IGNORE_CASE
    )

    fun parse(input: String): ParsedInput {
        val match = pattern.matchEntire(input)
        if (match == null) return ParsedInput(input.trim(), 1)
        val number = (match.groupValues[1].ifEmpty { match.groupValues[2] }).toInt()
        return ParsedInput(match.groupValues[3].trim(), number.coerceIn(1, 999))
    }

    /** For comparing names: ignores case and extra whitespace. */
    fun normalize(name: String): String =
        name.trim().replace(Regex("\\s+"), " ").lowercase()
}