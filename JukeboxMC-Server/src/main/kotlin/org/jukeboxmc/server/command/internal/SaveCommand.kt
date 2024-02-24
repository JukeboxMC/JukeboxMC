package org.jukeboxmc.server.command.internal

import org.jukeboxmc.api.command.Command
import org.jukeboxmc.api.command.CommandSender
import org.jukeboxmc.api.command.annotation.Description
import org.jukeboxmc.api.command.annotation.Name
import org.jukeboxmc.api.command.annotation.Permission
import org.jukeboxmc.server.JukeboxServer

@Name("save")
@Description("Save all worlds.")
@Permission("jukeboxmc.command.save")
class SaveCommand : Command {

    override fun execute(commandSender: CommandSender, command: String, args: Array<String>) {
        commandSender.sendMessage("Saving all worlds...")
        for (world in JukeboxServer.getInstance().getWorlds()) {
            world.save()
            commandSender.sendMessage("Saving " + world.getName() + " success.")
        }
    }
}