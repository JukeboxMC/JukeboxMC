package org.jukeboxmc.server.command

import org.cloudburstmc.protocol.bedrock.data.command.CommandParamType
import org.jukeboxmc.api.command.Command
import org.jukeboxmc.api.command.CommandManager
import org.jukeboxmc.api.command.CommandSender
import org.jukeboxmc.api.command.annotation.*
import org.jukeboxmc.server.command.internal.*
import org.jukeboxmc.server.extensions.getCommandData
import org.jukeboxmc.server.extensions.setCommandData

class JukeboxCommandManager : CommandManager {

    var commands: MutableList<Command> = mutableListOf()

    init {
        this.commands.add(GameModeCommand())
        this.commands.add(StopCommand())
        this.commands.add(KickCommand())
        this.commands.add(StatusCommand())
        this.commands.add(WhitelistCommand())
        this.commands.add(SaveCommand())
        this.commands.add(OpCommand())
        this.commands.add(DeopCommand())
        this.commands.add(EnchantCommand())
        this.commands.add(PluginsCommand())
        this.commands.add(GameRuleCommand())
        this.commands.add(TeleportCommand())
        this.commands.add(EffectCommand())
        this.commands.add(SummonCommand())
        this.commands.add(TimeCommand())

        for (command in this.commands) {
            this.initCommand(command)
        }
    }

    override fun registerCommand(command: Command) {
        if (this.commands.add(command)) {
            this.initCommand(command)
        }
    }

    fun handleCommandInput(commandSender: CommandSender, input: String) {
        try {
            val commandParts = this.parseQuoteAware(input.substring(1))
            val commandIdentifier = commandParts[0]
            var consumed = 0
            var targetCommand: Command? = null
            while (targetCommand == null) {
                for (command in commands) {
                    val commandData = command.getCommandData() ?: continue
                    if (commandData.name == commandIdentifier || commandData.aliases.values.contains(commandIdentifier)) {
                        targetCommand = command
                        break
                    }
                }
                consumed++
                if (targetCommand == null) {
                    if (commandParts.size == consumed) {
                        break
                    }
                }
            }
            if (targetCommand == null) {
                commandSender.sendMessage("The command for $commandIdentifier could not be found")
                return
            }
            val commandData = targetCommand.getCommandData() ?: return
            if (commandData.permission != null && commandData.permissionMessage && !commandSender.hasPermission(commandData.permission!!)) {
                commandSender.sendMessage("§cYou don't have permission to do that")
                return
            }
            val params: Array<String> = if (commandParts.size > consumed) {
                commandParts.copyOfRange(consumed, commandParts.size)
            } else {
                emptyArray()
            }

            targetCommand.execute(commandSender, commandIdentifier, params)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun initCommand(command: Command) {
        val clazz = command::class.java
        val commandData: CommandData.Builder = CommandData.builder()
        if (clazz.isAnnotationPresent(Name::class.java)) {
            commandData.name = clazz.getAnnotation(Name::class.java).value
        }
        if (clazz.isAnnotationPresent(Description::class.java)) {
            commandData.description = clazz.getAnnotation(Description::class.java).value
        }
        if (clazz.isAnnotationPresent(Permission::class.java)) {
            val annotation = clazz.getAnnotation(Permission::class.java)
            commandData.permission = annotation.value
            commandData.permissionMessage = annotation.permissionMessage
        }
        if (clazz.isAnnotationPresent(Alias::class.java)) {
            commandData.aliases.addAll(clazz.getAnnotation(Alias::class.java).value)
        }
        val overloads: MutableList<Array<CommandParameter>> = mutableListOf()
        for (parameters in clazz.getAnnotationsByType(Parameters::class.java)) {
            val commandParameter: MutableList<CommandParameter> = mutableListOf()
            for (parameter in parameters.parameter) {
                if (parameter.enumValues.isEmpty()) {
                    commandParameter.add(CommandParameter(parameter.name, CommandParamType.valueOf(parameter.parameterType.name), parameter.optional))
                } else {
                    commandParameter.add(CommandParameter(parameter.name, parameter.enumValues.toMutableList(), parameter.optional))
                }
            }
            overloads.add(commandParameter.toTypedArray())
        }
        commandData.overloads.addAll(overloads)
        command.setCommandData(commandData.build())
    }

    private fun parseQuoteAware(input: String): Array<String> {
        val args = arrayListOf<String>()
        var insideQuote = false
        var builder = StringBuilder()
        val chars = input.toCharArray()
        var i = 0
        while (i < input.length) {
            val c = chars[i]
            if (c == '\\' && i < input.length - 1 && chars[i + 1] == '\"') {
                builder.append("\"")
                i++
            } else if (c == '\"') {
                insideQuote = !insideQuote
            } else if (c == ' ' && !insideQuote) {
                args.add(builder.toString())
                builder = StringBuilder()
            } else {
                builder.append(c)
            }
            i++
        }
        args.add(builder.toString())
        return args.toTypedArray()
    }

}