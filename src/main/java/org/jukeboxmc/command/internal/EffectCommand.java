package org.jukeboxmc.command.internal;

import com.nukkitx.protocol.bedrock.data.command.CommandParamType;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jukeboxmc.Server;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandData;
import org.jukeboxmc.command.CommandParameter;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.potion.Effect;
import org.jukeboxmc.potion.EffectType;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name ( "effect" )
@Description ( "Add or remove status effect." )
@Permission ( "jukeboxmc.command.effect" )
public class EffectCommand extends Command {

    public EffectCommand() {
        super( CommandData.builder()
                .addParameters( new CommandParameter[]{
                                new CommandParameter( "target", CommandParamType.TARGET, false ),
                                new CommandParameter( "effect", Stream.of( EffectType.values() ).map( effectType -> effectType.name().toLowerCase() ).collect( Collectors.toList() ), false ),
                                new CommandParameter( "seconds", CommandParamType.INT, true ),
                                new CommandParameter( "amplifier", CommandParamType.INT, true ),
                                new CommandParameter( "visible", Arrays.asList( "true", "false" ), true )
                        },
                        new CommandParameter[]{
                                new CommandParameter( "target", CommandParamType.TARGET, false ),
                                new CommandParameter( "clear", List.of( "clear" ), false )
                        }
                )
                .build() );
    }

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        if ( args.length >= 2 ) {
            Player target = Server.getInstance().getPlayer( args[0] );
            String type = args[1].toLowerCase();

            if ( type.equalsIgnoreCase( "clear" ) ) {
                target.removeAllEffects();
                commandSender.sendMessage( "Took all effects from " + target.getName() );
            } else {
                if ( !EnumUtils.isValidEnum( EffectType.class, type.toUpperCase() ) ) {
                    commandSender.sendMessage( "Effect not found." );
                    return;
                }
                EffectType effectType = EffectType.valueOf( type.toUpperCase() );
                if ( !NumberUtils.isCreatable( args[2] ) ) {
                    commandSender.sendMessage( "The seconds must be a number." );
                    return;
                }
                int seconds = args.length == 3 ? Integer.parseInt( args[2] ) : 30;
                if ( !NumberUtils.isCreatable( args[3] ) ){
                    commandSender.sendMessage( "The amplifier must be a number." );
                    return;
                }
                int amplifier = args.length == 4 ? Integer.parseInt( args[3] ) : 0;
                if ( !args[4].equalsIgnoreCase( "true" ) || !args[4].equalsIgnoreCase( "false" ) ){
                    commandSender.sendMessage( "The visible must be a boolean." );
                    return;
                }
                boolean visible = args.length != 5 || Boolean.parseBoolean( args[4] );
                target.addEffect( Effect.create( effectType ).setDuration( seconds, TimeUnit.SECONDS ).setAmplifier( amplifier ).setVisible( visible ) );
                commandSender.sendMessage( "Gave " + effectType.name().toLowerCase() + " * " + amplifier + " to " + target.getName() + " for " + seconds + " seconds." );
            }
        }
    }
}
