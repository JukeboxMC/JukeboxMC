package org.jukeboxmc.server.command.internal

import org.jukeboxmc.api.command.Command
import org.jukeboxmc.api.command.CommandSender
import org.jukeboxmc.api.command.annotation.Description
import org.jukeboxmc.api.command.annotation.Name
import org.jukeboxmc.api.command.annotation.Permission
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.util.Utils
import java.util.concurrent.TimeUnit

@Name("status")
@Description("Show the current status form the server.")
@Permission("jukeboxmc.command.status")
class StatusCommand : Command {

    override fun execute(commandSender: CommandSender, command: String, args: Array<String>) {
        val builder = StringBuilder()
        builder.append("§7======== §8| §bServer Status §8| §7========").append("\n")
        builder.append("§7Uptime§8: §b").append(formatUptime(System.currentTimeMillis() - JukeboxServer.getInstance().getStartTime())).append("\n")

        val currentTps= JukeboxServer.getInstance().getTps()
        val tpsColor = if (currentTps in 13..16) "§e" else if (currentTps < 12) "§c" else "§a"
        builder.append("§7Current TPS§8: ").append(tpsColor).append(currentTps).append("\n")

        val runtime = Runtime.getRuntime()
        val totalMB: Double = Utils.round(runtime.totalMemory().toDouble() / 1024 / 1024, 2)
        val usedMB: Double = Utils.round((runtime.totalMemory() - runtime.freeMemory()).toDouble() / 1024 / 1024, 2)
        val maxMB: Double = Utils.round(runtime.maxMemory().toDouble() / 1024 / 1024, 2)
        val usage = usedMB / maxMB * 100

        builder.append("§7Used VM Memory§8: §b").append(usedMB).append(" MB. (").append(Utils.round(usage, 2)).append("%)").append("\n")
        builder.append("§7Total VM Memory§8: §b").append(totalMB).append(" MB.")
        commandSender.sendMessage(builder.toString())
    }

    private fun formatUptime(uptime: Long): String {
        var remainingUptime = uptime
        val days = TimeUnit.MILLISECONDS.toDays(remainingUptime)
        remainingUptime -= TimeUnit.DAYS.toMillis(days)
        val hours = TimeUnit.MILLISECONDS.toHours(remainingUptime)
        remainingUptime -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingUptime)
        remainingUptime -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingUptime)
        return "$days §7Days §b$hours §7Hours §b$minutes §7Minutes §b$seconds §7Seconds"
    }
}