package org.jukeboxmc.server.command

import org.cloudburstmc.protocol.bedrock.data.command.CommandOverloadData
import org.cloudburstmc.protocol.bedrock.data.command.CommandParamType
import org.cloudburstmc.protocol.bedrock.data.command.CommandPermission

class CommandData(
        var name: String,
        var description: String,
        var usage: String,
        var permission: String? = null,
        var permissionMessage: Boolean = true,
        aliases: CommandEnum,
        overloads: MutableList<Array<CommandParameter>>
) {

    val aliases: CommandEnum
    val overloads: MutableList<Array<CommandParameter>>

    init {
        this.aliases = aliases
        this.overloads = overloads
    }

    fun toNetwork(): org.cloudburstmc.protocol.bedrock.data.command.CommandData {
        val description = this.description

        val overloadData = Array(this.overloads.size) {
            val parameters = this.overloads[it]
            Array(parameters.size) { index ->
                parameters[index].toNetwork()
            }
        }

        val commandOverloadData: MutableList<CommandOverloadData> = mutableListOf()
        for (data in overloadData) {
            commandOverloadData.add(CommandOverloadData(false, data))
        }

        return org.cloudburstmc.protocol.bedrock.data.command.CommandData(
                this.name,
                description,
                emptySet(),
                CommandPermission.ANY,
                this.aliases.toNetwork(),
                arrayListOf(),
                commandOverloadData.toTypedArray()
        )
    }

    override fun toString(): String {
        return "CommandData(name='$name', description='$description', usage='$usage', permission='$permission', aliases=$aliases, overloads=$overloads)"
    }

    class Builder {
        var name = ""
        var description = ""
        var usage = ""
        var permission: String? = null
        var permissionMessage: Boolean = true
        var aliases: MutableList<String> = ArrayList()
        var overloads: MutableList<Array<CommandParameter>> = mutableListOf()

        constructor()

        constructor(name: String) {
            this.name = name.lowercase()
        }

        fun build(): CommandData {
            if (this.overloads.size == 0) {
                this.overloads.add(arrayOf(CommandParameter("args", CommandParamType.TEXT, true)))
            }
            return CommandData(this.name, this.description, this.usage, this.permission, this.permissionMessage, CommandEnum(this.name, this.aliases.toTypedArray()), this.overloads)
        }
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }

        fun builder(commandName: String): Builder {
            return Builder(commandName)
        }
    }
}

