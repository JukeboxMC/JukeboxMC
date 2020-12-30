package org.jukeboxmc.player;

import lombok.Getter;
import lombok.Setter;
import org.jukeboxmc.Server;
import org.jukeboxmc.entity.adventure.AdventureSettings;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.entity.attribute.AttributeType;
import org.jukeboxmc.entity.attribute.Attributes;
import org.jukeboxmc.entity.passiv.EntityHuman;
import org.jukeboxmc.inventory.PlayerInventory;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.PlayStatusPacket;
import org.jukeboxmc.network.packet.TextPacket;
import org.jukeboxmc.network.raknet.Connection;
import org.jukeboxmc.player.info.DeviceInfo;
import org.jukeboxmc.player.skin.Skin;
import org.jukeboxmc.world.Sound;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Player extends EntityHuman {

    private String name;
    private String xuid;
    private UUID uuid;
    private Skin skin;

    private float headYaw;

    private int viewDistance = 32;

    private boolean isOnGround;
    @Getter
    @Setter
    private boolean isSpawned;

    private Locale locale;
    private Location location;

    private Server server;
    private Attributes attributes;
    private AdventureSettings adventureSettings;
    private GameMode gameMode = GameMode.CREATIVE;
    private DeviceInfo deviceInfo;
    private InetSocketAddress address;
    private PlayerConnection playerConnection;

    private PlayerInventory playerInventory;

    private List<UUID> emotes = new ArrayList<>();

    public Player( Server server, Connection connection ) {
        this.server = server;
        this.attributes = new Attributes();
        this.adventureSettings = new AdventureSettings( this );
        this.location = new Location( server.getDefaultWorld(), 0, 10, 0, 0, 0 ); //Need form saved file
        this.address = connection.getSender();
        this.playerConnection = new PlayerConnection( this, connection );

        this.playerInventory = new PlayerInventory( this );
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

    public boolean isOnGround() {
        return isOnGround;
    }

    public void setOnGround( boolean onGround ) {
        isOnGround = onGround;
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

    public World getWorld() {
        return this.location.getWorld();
    }

    public float getX() {
        return this.getLocation().getX();
    }

    public int getFloorX() {
        return (int) Math.floor( this.getLocation().getX() );
    }

    public float getY() {
        return this.location.getY();
    }

    public int getFloorY() {
        return (int) Math.floor( this.getLocation().getY() );
    }

    public float getZ() {
        return this.location.getZ();
    }

    public int getFloorZ() {
        return (int) Math.floor( this.getLocation().getZ() );
    }

    public float getYaw() {
        return this.location.getYaw();
    }

    public float getPitch() {
        return this.location.getPitch();
    }

    public int getChunkX() {
        return (int) this.location.getX() >> 4;
    }

    public int getChunkZ() {
        return (int) this.location.getZ() >> 4;
    }

    public Chunk getChunk() {
        return this.location.getWorld().getChunk( this.getChunkX(), this.getChunkZ() );
    }

    public void setLocation( Location location ) {
        this.location = location;
    }

    public Server getServer() {
        return this.server;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public Attribute getAttribute( AttributeType attributeType ) {
        return this.attributes.getAttributes().stream().filter( attribute -> attribute.getAttributeType() == attributeType ).findFirst().orElse( null );
    }

    public AdventureSettings getAdventureSettings() {
        return this.adventureSettings;
    }

    public GameMode getGameMode() {
        return this.gameMode;
    }

    public void setGameMode( GameMode gameMode ) {
        this.gameMode = gameMode;
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

    public List<UUID> getEmotes() {
        return this.emotes;
    }

    public float getHeadYaw() {
        return this.headYaw;
    }

    public void setHeadYaw( float headYaw ) {
        this.headYaw = headYaw;
    }

    public void setAddress( InetSocketAddress address ) {
        this.address = address;
    }

    @Override
    public PlayerInventory getInventory() {
        return this.playerInventory;
    }

    //Other

    public void firstSpawn() {
        this.setSpawned( true );
        this.playerConnection.sendNetworkChunkPublisher();

        this.playerConnection.sendTime( 1000 );
        this.playerConnection.sendAdventureSettings();
        this.playerConnection.sendAttributes( this.attributes.getAttributes() );
        this.playerConnection.sendMetadata();

        this.playerConnection.sendStatus( PlayStatusPacket.Status.PLAYER_SPAWN );
        this.playerInventory.addViewer( this );

        this.getPlayerConnection().addPlayerToList();

        if ( this.getServer().getOnlinePlayers().size() > 1 ) {
            this.getPlayerConnection().sendPlayerList();
        }

        //JoinEvent

        new Timer( ).schedule( new TimerTask() {
            @Override
            public void run() {
                Player player = Player.this;
                player.getInventory().setItem( 1, ItemType.STONE.getItem().setAmount( 2 ) );
                player.getInventory().setItem( 8, ItemType.STONE.getItem().setAmount( 64 ) );
                player.sendMessage( "ADD" );
            }
        }, 1000 * 5);

        new Timer( ).schedule( new TimerTask() {
            @Override
            public void run() {
                Player player = Player.this;
                player.getInventory().clear();
                player.sendMessage( "REMOVE" );
            }
        }, 1000 * 10);
    }

    public void leaveGame() {
        this.getWorld().removePlayer( this );

        this.playerInventory.removeViewer( this );

        Server server = this.getServer();
        server.removePlayer( this.getAddress() );
        server.setOnlinePlayers( server.getOnlinePlayers().size() );
        server.broadcastMessage( "§e" + this.getName() + " left the game" );
    }

    public int getViewDistance() {
        return this.viewDistance;
    }

    public float getEyeHeight() {
        return 1.62f;
    }

    public void setViewDistance( int viewDistance ) {
        this.viewDistance = viewDistance;
        this.playerConnection.setViewDistance( viewDistance );
    }

    public void disconnect( String message ) {
        this.playerConnection.disconnect( message );
    }

    public void sendMessage( String message ) {
        this.playerConnection.sendMessage( message, TextPacket.Type.RAW );
    }

    public void sendTip( String message ) {
        this.playerConnection.sendMessage( message, TextPacket.Type.TIP );
    }

    public void sendAttributes( List<Attribute> attributes ) {
        this.playerConnection.sendAttributes( attributes );
    }

    public void sendattribute( Attribute attribute ) {
        this.playerConnection.sendattribute( attribute );
    }

    public void playSound( Sound sound ) {
        this.playSound( this.location, sound, 1, 1 );
    }

    public void playSound( Sound sound, float volume, float pitch ) {
        this.playSound( this.location, sound, volume, pitch );
    }

    public void playSound( Vector position, Sound sound ) {
        this.playSound( position, sound, 1, 1 );
    }

    public void playSound( Vector position, Sound sound, float volume, float pitch ) {
        this.playerConnection.playSound( position, sound, volume, pitch );
    }
}
