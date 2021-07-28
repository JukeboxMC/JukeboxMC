package org.jukeboxmc.command;

import lombok.SneakyThrows;
import org.jukeboxmc.command.annotation.Parameter;
import org.jukeboxmc.command.annotation.ParameterSection;
import org.jukeboxmc.command.jukebox.GameModeCommand;
import org.jukeboxmc.command.validator.Validator;

import java.util.*;

/**
 * @author Kaooot
 * @version 1.0
 */
public class CommandManager {

    private final List<Command> commands = new ArrayList<>();

    public CommandManager() {
        List<Class<? extends Command>> commands = Arrays.asList(
                GameModeCommand.class
        );

        for ( final Class<? extends Command> commandClass : commands ) {
            try {
                this.registerCommand( commandClass.newInstance() );
            } catch ( InstantiationException | IllegalAccessException e ) {
                e.printStackTrace();
            }
        }
    }

    @SneakyThrows
    public CommandOutput handleCommandInput( CommandSender commandSender, String input ) {
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
            return new CommandOutput().fail( "The command for " + commandIdentifier + " could not be found" );
        }

        if ( targetCommand.getPermission() != null && !commandSender.hasPermission( targetCommand.getPermission() ) ) {
            return new CommandOutput().fail( "You don't have permission to do that" );
        }

        String[] params;
        if ( commandParts.length > consumed ) {
            params = new String[commandParts.length - consumed];
            System.arraycopy( commandParts, consumed, params, 0, commandParts.length - consumed );
        } else {
            params = new String[0];
        }

        Parameter[] targetParameter = null;
        for ( ParameterSection parameterSection : targetCommand.getParameterSections() ) {
            Parameter[] value = parameterSection.value();
            if ( value.length == commandParts.length - 1 || params.length == commandParts.length - 1 ) {
                targetParameter = value;
                break;
            }
        }

        int index = 0;
        Map<String, Object> argumentsMap = new HashMap<>();
        if ( targetParameter != null ) {
            for ( int i = 0; i < params.length; i++ ) {
                Parameter parameter = targetParameter[i];
                Iterator<String> paramIterator = Arrays.asList( params ).iterator();

                Validator value = parameter.validator().newInstance();
                String parseIterator = value.parseIterator( paramIterator );

                if ( parseIterator != null ) {
                    argumentsMap.put( parameter.name(), value.parseObject( parseIterator ) );
                } else {
                    argumentsMap.put( parameter.name(), value.parseObject( commandParts[++index] ) );
                }
            }
        } else {
            return new CommandOutput().fail( "Failed to find parameter" );
        }

        return targetCommand.execute( commandSender, commandIdentifier, argumentsMap );
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