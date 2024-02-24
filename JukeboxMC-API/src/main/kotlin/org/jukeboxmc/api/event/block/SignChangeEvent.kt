package org.jukeboxmc.api.event.block

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.player.Player

class SignChangeEvent(
    block: Block,
    private val player: Player,
    private val frontText: MutableList<String>,
    private val backText: MutableList<String>
) : BlockEvent(block), Cancellable {

    fun getPlayer(): Player {
        return this.player
    }

    fun getFrontText(): MutableList<String> {
        return this.frontText
    }

    fun getBackText(): MutableList<String> {
        return this.backText
    }

    fun getFrontLine(index: Int): String {
        return this.frontText[index]
    }

    fun setFrontLine(index: Int, line: String) {
        this.frontText[index] = line
    }

    fun getBackLine(index: Int): String {
        return this.backText[index]
    }

    fun setBackLine(index: Int, line: String) {
        this.backText[index] = line
    }
}