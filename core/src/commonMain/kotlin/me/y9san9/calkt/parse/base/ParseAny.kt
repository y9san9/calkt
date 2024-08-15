package me.y9san9.calkt.parse.base

import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.elseTry
import me.y9san9.calkt.parse.getOrFail
import me.y9san9.calkt.parse.tryParse

public fun <T> ParseContext.parseFirstOf(
    blocks: List<() -> T>
): T {
    var result = tryParse(blocks.first())
    var index = 1

    while (true) {
        if (blocks.size == index) break
        result = result.elseTry(context) { blocks[index]() }
        index++
    }

    return result.getOrFail(context)
}
