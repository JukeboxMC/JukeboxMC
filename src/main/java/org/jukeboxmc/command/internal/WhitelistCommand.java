package org.jukeboxmc.command.internal;

import org.cloudburstmc.protocol.bedrock.data.command.CommandParamType;
import org.jukeboxmc.Server;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandData;
import org.jukeboxmc.command.CommandParameter;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;

import java.util.Arrays;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name ( "whitelist" )
@Description ( "Manages the list of players allowed to use this server" )
@Permission ( "jukeboxmc.command.whitelist" )
public class WhitelistCommand extends Command {

    public WhitelistCommand() {
        super( CommandData.builder()
                .setParameters( new CommandParameter[]{
                                new CommandParameter( "whitelist", Arrays.asList( "on", "off" ), false )
                        },
                        new CommandParameter[]{
                                new CommandParameter( "list", List.of( "list" ), false )
                        },
                        new CommandParameter[]{
                                new CommandParameter( "action", Arrays.asList( "add", "remove" ), false ),
                                new CommandParameter( "player", CommandParamType.TARGET, false )

                        }
                )
                .build() );
    }

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        if ( args.length == 1 ) {
            if ( args[0].equalsIgnoreCase( "on" ) ) {
                if ( Server.getInstance().hasWhitelist() ) {
                    commandSender.sendMessage( "§cThe whitelist is already activated" );
                    return;
                }
                Server.getInstance().setWhitelist( true );
                commandSender.sendMessage( "The whitelist was activated" );
            } else if ( args[0].equalsIgnoreCase( "off" ) ) {
                if ( !Server.getInstance().hasWhitelist() ) {
                    commandSender.sendMessage( "§cThe whitelist is already disabled" );
                    return;
                }
                Server.getInstance().setWhitelist( false );
                commandSender.sendMessage( "The whitelist was deactivated" );
            } else if ( args[0].equalsIgnoreCase( "list" ) ){
                StringBuilder builder = new StringBuilder();
                for ( String playerName : Server.getInstance().getWhitelist() ) {
                    builder.append( playerName ).append( ", " );
                }
                builder.setLength( builder.length() - 2 );
                commandSender.sendMessage( "The following players are on the whitelist: " + builder );
            } else {
                this.sendUsage( commandSender );
            }
        } else if ( args.length == 2 ) {
            if ( args[0].equalsIgnoreCase( "add" ) ) {
                String playerName = args[1];
                if ( Server.getInstance().isPlayerOnWhitelist( playerName ) ) {
                    commandSender.sendMessage( "§c" + playerName + " is already on the whitelist" );
                    return;
                }
                Server.getInstance().addPlayerToWhitelist( playerName );
                commandSender.sendMessage( playerName + " has been added to the whitelist" );
            } else if ( args[0].equalsIgnoreCase( "remove" ) ) {
                String playerName = args[1];
                if ( !Server.getInstance().isPlayerOnWhitelist( playerName ) ) {
                    commandSender.sendMessage( "§c" + playerName + " is not on the whitelist" );
                    return;
                }
                Server.getInstance().removePlayerFromWhitelist( playerName );
                commandSender.sendMessage( playerName + " was removed from the whitelist" );
            } else {
                this.sendUsage( commandSender );
            }
        } else {
            this.sendUsage( commandSender );
        }
    }

    private void sendUsage( CommandSender commandSender ) {
        commandSender.sendMessage( "Usage:" );
        commandSender.sendMessage( "- /whitelist <add|remove> <player>" );
        commandSender.sendMessage( "- /whitelist <on|off>" );
    }
}
