package org.jukeboxmc.network.raknet.type;

import org.jukeboxmc.Server;
import org.jukeboxmc.network.packet.Protocol;
import org.jukeboxmc.player.GameMode;

import java.util.StringJoiner;

/**
 * @author Kaooot, LucGamesYT
 * @version 1.0
 */
public class ServerInfo {

    private long serverId;
    private String motd;
    private String subMotd;
    private int maxPlayers;
    private GameMode defaultGameMode;

    public long getServerId() {
        return this.serverId;
    }

    public String getMotd() {
        return this.motd;
    }

    public String getSubmotd() {
        return this.subMotd;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public GameMode getDefaultGameMode() {
        return this.defaultGameMode;
    }

    public void setServerId( final long serverId ) {
        this.serverId = serverId;
    }

    public void setMotd( final String motd ) {
        this.motd = motd;
    }

    public void setSubmotd( final String submotd ) {
        this.subMotd = submotd;
    }

    public void setMaxPlayers( final int maxPlayers ) {
        this.maxPlayers = maxPlayers;
    }

    public void setDefaultGameMode( final GameMode defaultGameMode ) {
        this.defaultGameMode = defaultGameMode;
    }

    @Override
    public String toString() {
        final StringJoiner stringJoiner = new StringJoiner( ";" );
        stringJoiner.add( "MCPE" );
        stringJoiner.add( this.motd );
        stringJoiner.add( Integer.toString( Protocol.CURRENT_PROTOCOL ) );
        stringJoiner.add( Protocol.MINECRAFT_VERSION );
        stringJoiner.add( Integer.toString( Server.getInstance().getOnlinePlayers().size() ) );
        stringJoiner.add( Integer.toString( this.maxPlayers ) );
        stringJoiner.add( Long.toString( this.serverId ) );
        stringJoiner.add( this.subMotd );
        stringJoiner.add( this.defaultGameMode.getIdentifier() );
        stringJoiner.add( "1" );

        return stringJoiner.toString();
    }
}