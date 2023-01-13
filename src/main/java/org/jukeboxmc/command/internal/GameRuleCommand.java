package org.jukeboxmc.command.internal;

import com.nukkitx.protocol.bedrock.data.command.CommandParamType;
import org.apache.commons.lang3.math.NumberUtils;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandData;
import org.jukeboxmc.command.CommandParameter;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.gamerule.GameRule;

import java.util.Arrays;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name ( "gamerule" )
@Description ( "Set a gamerule value" )
@Permission ( "jukeboxmc.command.gamerule" )
public class GameRuleCommand extends Command {

    public GameRuleCommand() {
        super( CommandData.builder()
                .setParameters( new CommandParameter[]{
                                new CommandParameter( "rule", Arrays.stream( GameRule.values() ).map( gameRule -> gameRule.getIdentifier().toLowerCase() ).toList(), false ),
                                new CommandParameter( "value", List.of( "true", "false" ), false )
                        },
                        new CommandParameter[]{
                                new CommandParameter( "rule", Arrays.stream( GameRule.values() ).map( gameRule -> gameRule.getIdentifier().toLowerCase() ).toList(), false ),
                                new CommandParameter( "value", CommandParamType.INT, false ),

                        }
                ).build() );
    }

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        if ( commandSender instanceof Player player ) {
            if ( args.length == 2 ) {
                String gameRuleValue = args[0];
                GameRule gameRule = GameRule.fromIdentifier( gameRuleValue );
                if ( gameRule != null ) {
                    if ( NumberUtils.isCreatable( args[1] ) ) {
                        int value = Integer.parseInt( args[1] );
                        if ( gameRule.equals( GameRule.MAX_COMMAND_CHAIN_LENGTH ) || gameRule.equals( GameRule.SPAWN_RADIUS ) ) {
                            player.getWorld().setGameRule( gameRule, value );
                            player.sendMessage( "Gamerule " + gameRule.getIdentifier() + " has been updated to " + value );
                        } else {
                            player.sendMessage( "§cYou can only use one number for maxCommandChainLength or spawnRadius." );
                        }
                    } else {
                        String boolValue = args[1];
                        if ( boolValue.equalsIgnoreCase( "true" ) || boolValue.equalsIgnoreCase( "false" ) ) {
                            boolean value = Boolean.parseBoolean( boolValue );
                            player.getWorld().setGameRule( gameRule, value );
                            player.sendMessage( "Gamerule " + gameRule.getIdentifier() + " has been updated to " + value );
                        } else {
                            player.sendMessage( "§cValue must be a boolean." );
                        }
                    }
                } else {
                    player.sendMessage( "§cGameRule not found." );
                }
            }
        } else {
            commandSender.sendMessage( "§cYou must be a player." );
        }
    }
}
