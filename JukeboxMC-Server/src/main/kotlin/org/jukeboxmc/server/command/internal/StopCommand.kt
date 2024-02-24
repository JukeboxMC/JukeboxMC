package org.jukeboxmc.server.command.internal

import org.jukeboxmc.api.command.Command
import org.jukeboxmc.api.command.CommandSender
import org.jukeboxmc.api.command.annotation.Description
import org.jukeboxmc.api.command.annotation.Name
import org.jukeboxmc.api.command.annotation.Permission

@Name("stop")
@Description("Stop the server")
@Permission("jukeboxmc.command.stop")
class StopCommand : Command {

    override fun execute(commandSender: CommandSender, command: String, args: Array<String>) {
        commandSender.sendMessage("Stopping the server...")
        commandSender.getServer().shutdown()
    }
}