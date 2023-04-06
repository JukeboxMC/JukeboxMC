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
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name ( "kill" )
@Description ( "Kills entities (players, mobs, etc.)." )
@Permission ( "jukeboxmc.command.kill" )
public class KillCommand extends Command {

    public KillCommand() {
        super( CommandData.builder()
                .setParameters( new CommandParameter[]{
                        new CommandParameter( "target", CommandParamType.TARGET, false )
                } )
                .build() );
    }

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        if ( args.length == 1 ) {
            String target = args[0];

            if ( target.equalsIgnoreCase( "@a" ) ) {
                StringBuilder builder = new StringBuilder();
                for ( Player player : Server.getInstance().getOnlinePlayers() ) {
                    if ( !player.getGameMode().equals( GameMode.CREATIVE ) && !player.getGameMode().equals( GameMode.SPECTATOR ) ) {
                        player.kill();
                        builder.append( player.getName() ).append( ", " );
                    }
                }
                if ( builder.length() > 0 ) {
                    builder.setLength( builder.length() - 2 );
                    commandSender.sendMessage( builder + " killed." );
                }
            } else if ( target.equalsIgnoreCase( "@e" ) ) {
                List<Entity> entities = new ArrayList<>();
                for ( World world : Server.getInstance().getWorlds() ) {
                    entities.addAll( world.getEntitys().stream().filter( entity -> !( entity instanceof Player ) ).collect( Collectors.toSet() ) );
                }
                StringBuilder builder = new StringBuilder();
                for ( Entity entity : entities ) {
                    entity.close();
                    builder.append( entity.getName() ).append( ", " );
                }
                if ( builder.length() > 0 ) {
                    builder.setLength( builder.length() - 2 );
                    commandSender.sendMessage( builder + " killed." );
                }
            } else if ( target.equalsIgnoreCase( "@r" ) ) {
                Collection<Player> onlinePlayers = Server.getInstance().getOnlinePlayers();
                ThreadLocalRandom current = ThreadLocalRandom.current();
                Player targetPlayer = new ArrayList<>( onlinePlayers ).get( current.nextInt( onlinePlayers.size() - 1 ) );
                if ( targetPlayer != null ) {
                    if ( !targetPlayer.getGameMode().equals( GameMode.CREATIVE ) ) {
                        targetPlayer.kill();
                        commandSender.sendMessage( "Killed " + targetPlayer.getName() );
                    }
                }
            } else if ( target.equalsIgnoreCase( " @s" ) ) {
                if ( commandSender instanceof Player player ) {
                    if ( !player.getGameMode().equals( GameMode.CREATIVE ) && !player.getGameMode().equals( GameMode.SPECTATOR ) ) {
                        player.kill();
                        player.sendMessage( player.getName() + " killed." );
                    }
                }
            } else {
                Player targetPlayer = Server.getInstance().getPlayer( target );
                if ( targetPlayer != null ) {
                    targetPlayer.kill();
                    commandSender.sendMessage( targetPlayer.getName() + " killed." );
                } else {
                    commandSender.sendMessage( "Target not found." );
                }
            }
        } else {
            commandSender.sendMessage( "Target not found." );
        }
    }
}
