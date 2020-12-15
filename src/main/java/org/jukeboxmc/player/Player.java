package org.jukeboxmc.player;

import org.jukeboxmc.Server;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.network.raknet.Connection;
import org.jukeboxmc.player.info.DeviceInfo;
import org.jukeboxmc.player.skin.Skin;

import java.net.InetSocketAddress;
import java.util.Locale;
import java.util.UUID;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Player {

    private String name;
    private String xuid;
    private UUID uuid;
    private Skin skin;

    private int viewDistance = 16;

    private Locale locale;
    private Location location;

    private Server server;
    private DeviceInfo deviceInfo;
    private InetSocketAddress address;
    private PlayerConnection playerConnection;

    public Player( Server server, Connection connection ) {
        this.server = server;
        this.location = new Location( server.getDefaultWorld(), 0, 0, 0, 0, 0 );
        this.address = connection.getSender();
        this.playerConnection = new PlayerConnection( this, connection );
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getXuid() {
        return this.xuid;
    }

    public void setXuid( String xuid ) {
        this.xuid = xuid;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setUUID( UUID uuid ) {
        this.uuid = uuid;
    }

    public Skin getSkin() {
        return this.skin;
    }

    public void setSkin( Skin skin ) {
        this.skin = skin;
    }

    public void setLocale( Locale locale ) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation( Location location ) {
        this.location = location;
    }

    public Server getServer() {
        return this.server;
    }

    public DeviceInfo getDeviceInfo() {
        return this.deviceInfo;
    }

    public void setDeviceInfo( DeviceInfo deviceInfo ) {
        this.deviceInfo = deviceInfo;
    }

    public InetSocketAddress getAddress() {
        return this.address;
    }

    public PlayerConnection getPlayerConnection() {
        return this.playerConnection;
    }

    public void setPlayerConnection( PlayerConnection playerConnection ) {
        this.playerConnection = playerConnection;
    }

    //Other

    public int getViewDistance() {
        return this.viewDistance;
    }

    public void setViewDistance( int viewDistance ) {
        this.viewDistance = viewDistance;
        this.playerConnection.setViewDistance( viewDistance );
    }
}
