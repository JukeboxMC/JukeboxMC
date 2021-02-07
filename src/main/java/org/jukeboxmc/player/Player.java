package org.jukeboxmc.player;

import lombok.Getter;
import lombok.Setter;
import org.jukeboxmc.Server;
import org.jukeboxmc.entity.adventure.AdventureSettings;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.entity.attribute.AttributeType;
import org.jukeboxmc.entity.attribute.Attributes;
import org.jukeboxmc.entity.passive.EntityHuman;
import org.jukeboxmc.inventory.*;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.PlayerMovePacket;
import org.jukeboxmc.network.packet.TextPacket;
import org.jukeboxmc.network.raknet.Connection;
import org.jukeboxmc.player.info.DeviceInfo;
import org.jukeboxmc.player.skin.Skin;
import org.jukeboxmc.world.Sound;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Player extends EntityHuman implements InventoryHolder {

    private String name;
    private String xuid;
    private UUID uuid;
    private Skin skin;

    private float headYaw;

    private int viewDistance = 16;

    private boolean isOnGround;
    @Getter
    @Setter
    private boolean isSpawned;

    private Locale locale;

    private Server server;
    private Attributes attributes;
    private AdventureSettings adventureSettings;
    private GameMode gameMode;
    private DeviceInfo deviceInfo;
    private InetSocketAddress address;
    private PlayerConnection playerConnection;

    private ContainerInventory currentInventory;
    private PlayerInventory playerInventory;
    private CursorInventory cursorInventory;

    private List<UUID> emotes = new ArrayList<>();

    public Player( Server server, Connection connection ) {
        this.server = server;
        this.attributes = new Attributes();
        this.adventureSettings = new AdventureSettings( this );
        this.gameMode = server.getDefaultGamemode();
        this.address = connection.getSender();
        this.playerConnection = new PlayerConnection( this, server, connection );

        this.playerInventory = new PlayerInventory( this );
        this.cursorInventory = new CursorInventory( this );
    }

    @Override
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

    public Server getServer() {
        return this.server;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public Attribute getAttribute( AttributeType attributeType ) {
        for ( Attribute attribute : this.attributes.getAttributes() ) {
            if ( attribute.getAttributeType() == attributeType ) {
                return attribute;
            }
        }
        return null;
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

    public void setAddress( InetSocketAddress address ) {
        this.address = address;
    }

    public ContainerInventory getCurrentInventory() {
        return this.currentInventory;
    }

    @Override
    public PlayerInventory getInventory() {
        return this.playerInventory;
    }

    public CursorInventory getCursorInventory() {
        return this.cursorInventory;
    }

    public int getViewDistance() {
        return this.viewDistance;
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

    public void openInventory( Inventory inventory, BlockPosition position ) {
        if ( inventory instanceof ContainerInventory ) {
            ContainerInventory containerInventory = (ContainerInventory) inventory;

            if ( this.currentInventory != null ) {
                this.closeInventory( this.currentInventory );
            }
            this.playerConnection.openInventory( containerInventory, position );
            containerInventory.addViewer( this );

            this.currentInventory = containerInventory;
        }
    }

    public void openInventory( Inventory inventory ) {
        this.openInventory( inventory, this.location.toBlockPosition() );
    }

    public void closeInventory( int windowId, boolean isServerSide ) {
        if ( this.currentInventory != null ) {
            this.currentInventory.removeViewer( this );
            this.playerConnection.closeInventory( windowId, isServerSide );
            this.currentInventory = null;
        }
    }

    public void closeInventory( Inventory inventory ) {
        if ( inventory instanceof ContainerInventory ) {
            if ( this.currentInventory == inventory ) {
                this.closeInventory( WindowId.OPEN_CONTAINER.getId(), true );
            }
        }
    }

    public Inventory getInventory( WindowId windowId ) {
        switch ( windowId ) {
            case PLAYER:
                return this.getInventory();
            case CURSOR_DEPRECATED:
                return this.getCursorInventory();
            default:
                return this.getCurrentInventory();
        }
    }

    //TODO Implement world teleport

    private void switchWorld( World world ) {
        World currentWorld = this.getWorld();

        if ( currentWorld != world ) {
            this.getChunk().removeEntity( this );
            currentWorld.removePlayer( this );

            this.playerConnection.despawnForAll();

            this.playerConnection.getChunkLoadQueue().clear();
            this.playerConnection.getLoadedChunks().clear();
            this.playerConnection.getLoadingChunks().clear();
            world.addPlayer( this );
            this.setLocation( new Location( world, world.getSpawnLocation() ) );
            this.getChunk().addEntity( this );
            this.playerConnection.spawnToAll();
            this.playerConnection.needNewChunks();
        }
    }

    public void teleport( Player player ) {
        this.playerConnection.movePlayer( player, PlayerMovePacket.Mode.RESET );
    }

    public void teleport( Player player, PlayerMovePacket.Mode mode ) {
        this.playerConnection.movePlayer( player, mode );
    }

    public void teleport( Location location ) {
        this.teleport( location, PlayerMovePacket.Mode.RESET );
    }

    public void teleport( Location location, PlayerMovePacket.Mode mode ) {
        this.switchWorld( location.getWorld() );
        this.playerConnection.movePlayer( location, mode );
    }

    public void teleport( Vector vector ) {
        this.playerConnection.movePlayer( vector, PlayerMovePacket.Mode.RESET );
    }

    public void teleport( Vector vector, PlayerMovePacket.Mode mode ) {
        this.playerConnection.movePlayer( vector, mode );
    }
}
