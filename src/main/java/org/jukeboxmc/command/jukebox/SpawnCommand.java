package org.jukeboxmc.command.jukebox;

import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.Dimension;

/**
 * @author Luca W. | GPS_Gamer
 **/

@Name("spawn")
@Description("Teleport you to the world spawn")
public class SpawnCommand extends Command {

  @Override
  public void execute(CommandSender commandSender, String command, String[] args) {
    if (commandSender instanceof Player) {
      final Player player = (Player) commandSender;

      if (args.length == 0) {
        player.teleport(player.getWorld().getSpawnLocation(Dimension.OVERWORLD));
        player.sendMessage("You are now at the world spawn!");
      }
    } else {
      commandSender.sendMessage("§cYou must be a player");
    }
  }
}
