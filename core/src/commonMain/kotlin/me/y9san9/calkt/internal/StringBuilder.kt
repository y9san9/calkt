package me.y9san9.calkt.internal

@PublishedApi
internal inline fun StringBuilder.withIndent(
    indent: String = "    ",
    block: StringBuilder.() -> Unit
) {
    val string = buildString(block).prependIndent(indent)
    append(string)
}
