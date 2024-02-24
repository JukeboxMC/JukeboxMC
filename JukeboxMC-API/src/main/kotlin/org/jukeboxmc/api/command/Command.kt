package org.jukeboxmc.api.command

interface Command {

    /**
     * This method is called when the command is executed.
     *
     * @param commandSender The sender of the command.
     * @param command       The command that was executed.
     * @param args          The arguments of the command.
     */
    fun execute(commandSender: CommandSender, command: String, args: Array<String>)

}