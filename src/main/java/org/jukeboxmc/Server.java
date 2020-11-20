package org.jukeboxmc;

import lombok.NoArgsConstructor;
import org.jukeboxmc.network.raknet.Listener;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@NoArgsConstructor
public class Server {

    private InetSocketAddress address;
    private Listener listener;

    private boolean isShutdown = false;

    private Map<UUID, Player> players = new HashMap<>(); //TODO

    public boolean startServer( InetSocketAddress address ) {
        this.address = address;

        this.listener = new Listener();
        if ( !this.listener.listen( this.address.getHostName(), this.address.getPort() ) ) {
            System.out.println( "Der Server konnte nicht starten, läuft er bereits auf dem gleichen Port?" );
            return false;
        }
        return true;
    }

    public void setOnlinePlayers( int onlinePlayers ) {
        this.listener.getServerName().setOnlinePlayers( onlinePlayers );
    }

    public void setMaxPlayers( int maxPlayers ) {
        this.listener.getServerName().setMaxPlayers( maxPlayers );
    }

    public void setMotd( String motd ) {
        this.listener.getServerName().setMotd( motd );
    }

    public void setDefaultGamemode( GameMode defaultGamemode ) {
        this.listener.getServerName().setGameMode( defaultGamemode );
    }

    public GameMode getDefaultGamemode() {
        return this.listener.getServerName().getGameMode();
    }

    public String getMotd() {
        return this.listener.getServerName().getMotd();
    }

    public int getPort() {
        return this.address.getPort();
    }

    public String getHostname() {
        return this.address.getHostName();
    }

    public boolean isShutdown() {
        return this.isShutdown;
    }

    public void shutdown() {
        this.isShutdown = true;
    }

}
