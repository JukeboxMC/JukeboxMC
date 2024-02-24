package org.jukeboxmc.server.command

import org.cloudburstmc.protocol.bedrock.data.command.CommandEnumConstraint
import org.cloudburstmc.protocol.bedrock.data.command.CommandEnumData

open class CommandEnum(val name: String, val values: Array<String>) {

    fun toNetwork(): CommandEnumData {
        val aliases: Array<String> = if (this.values.isNotEmpty()) {
            val list = this.values.toMutableList()
            list.add(name)
            list.toTypedArray()
        } else {
            arrayOf(this.name)
        }
        return CommandEnumData("${this.name}Aliases", toNetwork(aliases), false)
    }

    fun toNetwork(values: Array<String>): LinkedHashMap<String, Set<CommandEnumConstraint>> {
        val map = LinkedHashMap<String, Set<CommandEnumConstraint>>()
        for (value in values) {
            map[value] = emptySet()
        }
        return map
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (this.javaClass != other?.javaClass) return false
        other as CommandEnum
        if (this.name != other.name) return false
        return this.values == other.values
    }

    override fun hashCode(): Int {
        return this.name.hashCode()
    }
}

