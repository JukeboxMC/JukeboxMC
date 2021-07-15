package org.jukeboxmc.command;

import org.jukeboxmc.command.annotation.Parameter;
import org.jukeboxmc.command.jukebox.TestCommand;

import java.util.*;

/**
 * @author Kaooot
 * @version 1.0
 */
public class CommandManager {

    private final List<Command> commands = new ArrayList<>();

    public CommandManager() {
        List<Class<? extends Command>> commands = Arrays.asList(
                TestCommand.class
        );

        for ( final Class<? extends Command> commandClass : commands ) {
            try {
                this.registerCommand( commandClass.newInstance() );
            } catch ( InstantiationException | IllegalAccessException e ) {
                e.printStackTrace();
            }
        }
    }

    public CommandOutput handleCommandInput( CommandSender commandSender, String input ) {
        final String[] commandParts = input.substring( 1 ).split( " " );
        final String commandIdentifier = commandParts[0];

        boolean isCommand = false;

        for ( final Command command : this.commands ) {
            if ( command.getName().equalsIgnoreCase( commandIdentifier ) || ( command.getAliases() != null &&
                    command.getAliases().contains( commandIdentifier ) ) ) {
                for ( Parameter parameter : command.getParameters() ) {
                    if ( parameter.arguments().length == ( commandParts.length - 1 ) && !parameter.optional() ) {
                        isCommand = true;
                        break;
                    }
                }
            }
        }

        CommandOutput failOutput = new CommandOutput().fail( "The command for " + commandIdentifier + " could not be found" );

        if ( !isCommand ) {
            return failOutput;
        }

        Command command = this.getCommandByNameOrAlias( commandIdentifier );

        if ( command == null ) {
            return failOutput;
        }

        Map<String, Object> argumentsMap = new HashMap<>();

        for ( Parameter parameter : command.getParameters() ) {
            if ( parameter.arguments().length == ( commandParts.length - 1 ) ) {
                int i = 0;

                for ( String argument : parameter.arguments() ) {
                    argumentsMap.put( argument, commandParts[i + 1] );

                    i++;
                }
            }
        }

        if ( argumentsMap.isEmpty() ) {
            return failOutput;
        }

        return command.execute( commandSender, commandIdentifier, argumentsMap );
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