package org.jukeboxmc.command;

import org.jukeboxmc.command.jukebox.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Kaooot
 * @version 1.0
 */
public class CommandManager {

    private final List<Command> commands = new ArrayList<>();

    public CommandManager() {
        List<Class<? extends Command>> commands = Arrays.asList(
                GameModeCommand.class,
                PluginsCommand.class,
                TeleportCommand.class,
                StopCommand.class,
                TimeCommand.class,
                SaveCommand.class,
                OperatorCommand.class,
                RemoveOperatorCommand.class,
                GameRulesCommand.class
        );

        for ( final Class<? extends Command> commandClass : commands ) {
            try {
                this.registerCommand( commandClass.newInstance() );
            } catch ( InstantiationException | IllegalAccessException e ) {
                e.printStackTrace();
            }
        }
    }

    public void handleCommandInput( CommandSender commandSender, String input ) {
        try {
            final String[] commandParts = input.substring( 1 ).split( " " );
            final String commandIdentifier = commandParts[0];

            int consumed = 0;
            Command targetCommand = null;
            while ( targetCommand == null ) {
                for ( Command command : this.commands ) {
                    if ( command.getName().equalsIgnoreCase( commandIdentifier ) || ( command.getAliases() != null && command.getAliases().contains( commandIdentifier ) ) ) {
                        targetCommand = command;
                        break;
                    }
                }

                consumed++;
                if ( targetCommand == null ) {
                    if ( commandParts.length == consumed ) {
                        break;
                    }
                }
            }

            if ( targetCommand == null ) {
                commandSender.sendMessage( "The command for " + commandIdentifier + " could not be found" );
                return;
            }

            if ( targetCommand.getPermission() != null && !commandSender.hasPermission( targetCommand.getPermission() ) ) {
                if ( targetCommand.getPermissionMessage() != null && !targetCommand.getPermissionMessage().isEmpty() ) {
                    commandSender.sendMessage( targetCommand.getPermissionMessage() );
                } else {
                    commandSender.sendMessage( "You don't have permission to do that" );
                }
                return;
            }

            String[] params;
            if ( commandParts.length > consumed ) {
                params = new String[commandParts.length - consumed];
                System.arraycopy( commandParts, consumed, params, 0, commandParts.length - consumed );
            } else {
                params = new String[0];
            }
            targetCommand.execute( commandSender, commandIdentifier, params );
        } catch ( Throwable e ) {
            e.printStackTrace();
        }
    }

    public void registerCommand( Command command ) {
        this.commands.add( command );
    }

    public void unregisterCommand( Command command ) {
        this.commands.remove( command );
    }

    public boolean isCommandRegistered( String name ) {
        for ( Command command : this.commands ) {
            if ( command.getName().equalsIgnoreCase( name ) ) {
                return true;
            }
        }

        return false;
    }

    public List<Command> getCommands() {
        return new ArrayList<>( this.commands );
    }

    private Command getCommandByNameOrAlias( String identifier ) {
        for ( final Command command : this.commands ) {
            if ( command.getName().equalsIgnoreCase( identifier ) || ( command.getAliases() != null &&
                    command.getAliases().contains( identifier ) ) ) {
                return command;
            }
        }

        return null;
    }
}