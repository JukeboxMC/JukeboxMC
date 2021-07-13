package org.jukeboxmc.command.jukebox;

import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandOutput;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.*;
import org.jukeboxmc.command.validator.StringValidator;

import java.util.Map;

/**
 * @author Kaooot
 * @version 1.0
 */
@Name ( "test" )
@Aliases ( {
        @Alias ( "t" ),
        @Alias ( "te" )
} )
@Description ( "executes this test command" )
@Permission ( "jukebox.test" )
@ParameterSections ( {
        @ParameterSection ( {
                @Parameter ( name = "args", validator = StringValidator.class, arguments = { "arg1", "arg2" } )
        } )
} )
public class TestCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender commandSender, String alias, Map<String, Object> arguments ) {
        return new CommandOutput().success( "Test has been successfully executed" );
    }
}