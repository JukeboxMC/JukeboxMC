package org.jukeboxmc.command.jukebox;

import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.GameRule;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name("gamerule")
@Description("Set a gamerule value")
@Permission("jukeboxmc.command.gamerule")
public class GameRulesCommand extends Command {

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        if ( commandSender instanceof Player ) {
            Player player = (Player) commandSender;
            World world = player.getWorld();

            if ( args.length == 1 ) {
                String gameRuleName = args[0];
                if ( gameRuleName != null || !gameRuleName.isEmpty() ) {
                    GameRule<?> gameRule = world.getGameRuleByName( gameRuleName );
                    if ( gameRule != null ) {
                        player.sendMessage( gameRuleName + " = " + world.getGameRule( gameRule ) );
                    }
                } else {
                    player.sendMessage( "You must specify a gamerule" );
                }
            } else if ( args.length == 2 ) {
                String gameRuleName = args[0];

                if ( gameRuleName != null || !gameRuleName.isEmpty() ) {
                    GameRule<?> gameRule = world.getGameRuleByName( gameRuleName );
                    if ( gameRule != null ) {
                        String value = args[1];
                        if ( gameRule.getValue().equals( Boolean.class ) ) {
                            if ( value.equalsIgnoreCase( "true" ) || value.equalsIgnoreCase( "false" ) ) {
                                boolean booleanValue = Boolean.parseBoolean( value );
                                world.setGameRule( gameRule, booleanValue );
                                world.updateGameRules();
                                player.sendMessage( "Game rule " + gameRuleName + " has been updated to " + booleanValue );
                            } else {
                                player.sendMessage( "You must specify a boolean" );
                            }
                        } else {
                            try {
                                int intValue = Integer.parseInt( value );
                                world.setGameRule( gameRule, intValue );
                                world.updateGameRules();
                                player.sendMessage( "Game rule " + gameRuleName + " has been updated to " + intValue );
                            } catch ( NumberFormatException e ) {
                                player.sendMessage( "You must specify a number" );
                            }
                        }
                    } else {
                        player.sendMessage( "The gamerule could not be found" );
                    }
                } else {
                    player.sendMessage( "You must specify a gamerule" );
                }
            }
        } else {
            commandSender.sendMessage( "§cYou must be a player" );
        }
    }
}
