package org.jukeboxmc.command;

import org.jukeboxmc.command.annotation.Parameter;
import org.jukeboxmc.command.jukebox.TestCommand;
import org.jukeboxmc.command.validator.ParameterValidator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
                    System.out.println( "param length: " + parameter.arguments().length );
                    System.out.println( "commandPart length " + ( commandParts.length - 1 ) );
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
        List<String> arguments = new ArrayList<>();
        Constructor<? extends ParameterValidator<?, ?>> constructor = null;

        for ( Parameter parameter : command.getParameters() ) {
            if ( parameter.arguments().length == ( commandParts.length - 1 ) ) {
                arguments.addAll( Arrays.asList( parameter.arguments() ) );

                try {
                    constructor = parameter.validator().getConstructor();
                } catch ( NoSuchMethodException e ) {
                    System.out.println( "Constructor is not no arg" );
                    try {
                        constructor = parameter.validator().getConstructor( String.class );
                    } catch ( NoSuchMethodException e1 ) {
                        System.out.println( "Constructor is not string" );
                        try {
                            constructor = parameter.validator().getConstructor( List.class );
                        } catch ( NoSuchMethodException e2 ) {
                            e2.printStackTrace();
                        }
                    }
                }
            }
        }

        try {
            if ( constructor != null ) {
                final ParameterValidator<?, ?> parameterValidator = constructor.newInstance();

                for ( String argument : arguments ) {
                    argumentsMap.put( argument, parameterValidator.validate( input, commandSender ) );
                }
            }
        } catch ( InstantiationException | IllegalAccessException | InvocationTargetException e ) {
            e.printStackTrace();
        }

        if ( argumentsMap.isEmpty() ) {
            return failOutput;
        }

        command.execute( commandSender, commandIdentifier, argumentsMap );

        System.out.println( "executed command with arguments: " + argumentsMap );

        return new CommandOutput().success( "" );
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