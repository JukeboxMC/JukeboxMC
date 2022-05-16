package org.jukeboxmc.player;

import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockServerSession;
import com.nukkitx.protocol.bedrock.data.AuthoritativeMovementMode;
import com.nukkitx.protocol.bedrock.data.GamePublishSetting;
import com.nukkitx.protocol.bedrock.data.PlayerPermission;
import com.nukkitx.protocol.bedrock.data.SyncedPlayerMovementSettings;
import com.nukkitx.protocol.bedrock.packet.*;
import it.unimi.dsi.fastutil.longs.*;
import org.apache.commons.math3.util.FastMath;
import org.jukeboxmc.Server;
import org.jukeboxmc.crafting.CraftingManager;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.event.network.PacketReceiveEvent;
import org.jukeboxmc.event.network.PacketSendEvent;
import org.jukeboxmc.event.player.PlayerJoinEvent;
import org.jukeboxmc.event.player.PlayerQuitEvent;
import org.jukeboxmc.item.ItemShield;
import org.jukeboxmc.network.Network;
import org.jukeboxmc.network.handler.HandlerRegistry;
import org.jukeboxmc.network.handler.PacketHandler;
import org.jukeboxmc.player.data.LoginData;
import org.jukeboxmc.util.*;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerConnection {

    private static final SyncedPlayerMovementSettings SYNCED_PLAYER_MOVEMENT_SETTINGS = new SyncedPlayerMovementSettings();
    private static final int SHIELD_RUNTIMEID = new ItemShield().getRuntimeId();

    private final Server server;
    private final BedrockServerSession session;

    private final AtomicBoolean loggedIn;
    private final AtomicBoolean spawned;

    private final Player player;

    private LoginData loginData;
    private String disconnectMessage;

    private volatile boolean requestedChunks = false;
    private final long[] chunksInRadius = new long[32 * 32 * 4];
    private final LongPriorityQueue chunkLoadQueue;

    private final LongSet loadedChunks = new LongOpenHashSet();

    static {
        SYNCED_PLAYER_MOVEMENT_SETTINGS.setMovementMode( AuthoritativeMovementMode.CLIENT );
        SYNCED_PLAYER_MOVEMENT_SETTINGS.setRewindHistorySize( 0 );
        SYNCED_PLAYER_MOVEMENT_SETTINGS.setServerAuthoritativeBlockBreaking( false );
    }

    public PlayerConnection( Server server, BedrockServerSession session ) {
        this.server = server;
        this.session = session;
        this.session.getHardcodedBlockingId().set( SHIELD_RUNTIMEID );
        this.session.addDisconnectHandler( disconnectReason -> server.getScheduler().execute( () -> this.disconnect( disconnectReason.name() ) ) );

        this.player = new Player( this );

        this.session.setBatchHandler( ( bedrockSession, byteBuf, packets ) -> {
            for ( BedrockPacket packet : packets ) {
                try {
                    PacketHandler<BedrockPacket> packetHandler = (PacketHandler<BedrockPacket>) HandlerRegistry.getPacketHandler( packet.getClass() );
                    if ( packetHandler != null ) {
                        PacketReceiveEvent packetReceiveEvent = new PacketReceiveEvent( this.player, packet );
                        server.getPluginManager().callEvent( packetReceiveEvent );
                        if ( packetReceiveEvent.isCancelled() ) {
                            return;
                        }
                        packetHandler.handle( packetReceiveEvent.getPacket(), this.server, this.player );
                    } else {
                        //System.out.println("Handler missing for packet: " + packet.getClass().getSimpleName());
                    }
                } catch ( Throwable throwable ) {
                    throwable.printStackTrace();
                }
            }
        } );

        this.loggedIn = new AtomicBoolean( false );
        this.spawned = new AtomicBoolean( false );

        ChunkComparator chunkComparator = new ChunkComparator( this.player );
        this.chunkLoadQueue = new LongArrayPriorityQueue( 4096, chunkComparator );
    }

    public void update() {
        if ( this.isClosed() || !this.loggedIn.get() ) {
            return;
        }

        this.needNewChunks();

        if ( !this.chunkLoadQueue.isEmpty() ) {
            int load = 0;
            while ( !this.chunkLoadQueue.isEmpty() && load <= 8 ) {
                long chunkHash = this.chunkLoadQueue.dequeueLong();
                if ( this.loadedChunks.contains( chunkHash ) ) {
                    continue;
                }
                this.requestChunk( Utils.fromHashX( chunkHash ), Utils.fromHashZ( chunkHash ) );
                load++;
            }
            this.chunkLoadQueue.clear();
        }

        if ( !this.spawned.get() && this.loadedChunks.size() >= 16 ) {
            this.doFirstSpawn();
        }
    }

    private void doFirstSpawn() {
        this.spawned.set( true );

        this.player.getWorld().addEntity( this.player );

        AdventureSettings adventureSettings = this.player.getAdventureSettings();

        if ( this.server.isOperatorInFile( this.player.getName() ) ) {
            adventureSettings.set( AdventureSettings.Type.OPERATOR, true );
        }

        adventureSettings.set( AdventureSettings.Type.WORLD_IMMUTABLE, this.player.getGameMode().ordinal() == 3 );
        adventureSettings.set( AdventureSettings.Type.ALLOW_FLIGHT, this.player.getGameMode().ordinal() > 0 );
        adventureSettings.set( AdventureSettings.Type.NO_CLIP, this.player.getGameMode().ordinal() == 3 );
        adventureSettings.set( AdventureSettings.Type.FLYING, this.player.getGameMode().ordinal() == 3 );
        adventureSettings.set( AdventureSettings.Type.ATTACK_MOBS, this.player.getGameMode().ordinal() < 2 );
        adventureSettings.set( AdventureSettings.Type.ATTACK_PLAYERS, this.player.getGameMode().ordinal() < 2 );
        adventureSettings.set( AdventureSettings.Type.NO_PVM, this.player.getGameMode().ordinal() == 3 );
        adventureSettings.update();

        this.player.sendCommandData();

        UpdateAttributesPacket updateAttributesPacket = new UpdateAttributesPacket();
        updateAttributesPacket.setRuntimeEntityId( this.player.getEntityId() );
        for ( Attribute attribute : this.player.getAttributes() ) {
            updateAttributesPacket.getAttributes().add( attribute.toNetwork() );
        }
        updateAttributesPacket.setTick( this.server.getCurrentTick() );
        this.sendPacket( updateAttributesPacket );

        this.player.sendEntityData();

        this.server.addToTablist( this.player );
        if ( this.server.getOnlinePlayers().size() > 1 ) {
            PlayerListPacket playerListPacket = new PlayerListPacket();
            playerListPacket.setAction( PlayerListPacket.Action.ADD );
            this.server.getPlayerListEntry().forEach( ( uuid, entry ) -> {
                if ( uuid != this.player.getUUID() ) {
                    playerListPacket.getEntries().add( entry );
                }
            } );
            this.player.sendPacket( playerListPacket );
        }

        this.player.getInventory().addViewer( this.player );
        this.player.getCursorInventory().addViewer( this.player );
        this.player.getArmorInventory().addViewer( this.player );
        this.player.getCraftingTableInventory().addViewer( this.player );
        this.player.getCartographyTableInventory().addViewer( this.player );
        this.player.getSmithingTableInventory().addViewer( this.player );
        this.player.getAnvilInventory().addViewer( this.player );

        PlayStatusPacket playStatusPacket = new PlayStatusPacket();
        playStatusPacket.setStatus( PlayStatusPacket.Status.PLAYER_SPAWN );
        this.sendPacket( playStatusPacket );

        for ( Player onlinePlayer : this.server.getOnlinePlayers() ) {
            if ( onlinePlayer != null && onlinePlayer.getDimension() == this.player.getDimension() ) {
                onlinePlayer.spawn( this.player );
                this.player.spawn( onlinePlayer );
            }
        }

        this.player.movePlayer( this.player.getLocation().add( 0, this.player.getEyeHeight(), 0 ), MovePlayerPacket.Mode.TELEPORT );

        PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent( this.player, "§e" + this.player.getName() + " has joined the game" );
        Server.getInstance().getPluginManager().callEvent( playerJoinEvent );
        if ( playerJoinEvent.getJoinMessage() != null && !playerJoinEvent.getJoinMessage().isEmpty() ) {
            Server.getInstance().broadcastMessage( playerJoinEvent.getJoinMessage() );
        }
        this.server.getLogger().info( this.player.getName() + " logged in [World=" + this.player.getWorld().getName() + ", X=" + this.player.getBlockX() + ", Y=" + this.player.getBlockY() + ", Z=" + this.player.getBlockZ() + ", Dimension=" + this.player.getDimension().name() + "]" );
    }

    public void disconnect( String reason ) {
        this.player.savePlayerData();

        this.player.getWorld().removeEntity( this.player );
        this.player.getChunk().removeEntity( this.player );

        //Remove inventory viewers
        this.player.getInventory().removeViewer( this.player );
        this.player.getArmorInventory().removeViewer( this.player );
        this.player.getCursorInventory().removeViewer( this.player );

        this.server.removeFromTablist( this.player );

        this.player.close();
        this.close( "Disconect" );
        this.server.removePlayer( this.player );

        PlayerQuitEvent playerQuitEvent = new PlayerQuitEvent( this.player, "§e" + this.player.getName() + " left the game" );
        Server.getInstance().getPluginManager().callEvent( playerQuitEvent );
        if ( playerQuitEvent.getQuitMessage() != null && !playerQuitEvent.getQuitMessage().isEmpty() ) {
            this.server.broadcastMessage( playerQuitEvent.getQuitMessage() );
        }
        this.server.getLogger().info( this.player.getName() + " logged out reason: " + ( this.disconnectMessage == null ? reason : this.disconnectMessage ) );
    }

    public void initializePlayer() {
        this.loggedIn.set( true );

        StartGamePacket startGamePacket = new StartGamePacket();
        startGamePacket.setServerEngine( "JukeboxMC" );
        startGamePacket.setUniqueEntityId( this.player.getEntityId() );
        startGamePacket.setRuntimeEntityId( this.player.getEntityId() );
        startGamePacket.setPlayerGameType( this.player.getGameMode().toGameType() );
        startGamePacket.setPlayerPosition( this.player.getLocation().toVector3f().add( 0, 2, 0 ) );
        startGamePacket.setDefaultSpawn( this.player.getSpawnLocation().toVector3i().add( 0, 2, 0 ) );
        startGamePacket.setRotation( Vector2f.from( this.player.getPitch(), this.player.getYaw() ) );
        startGamePacket.setSeed( this.player.getWorld().getSeed() );
        startGamePacket.setDimensionId( this.player.getDimension().ordinal() );
        startGamePacket.setTrustingPlayers( true );
        startGamePacket.setLevelGameType( this.server.getGameMode().toGameType() );
        startGamePacket.setDifficulty( this.player.getWorld().getDifficulty().ordinal() );
        startGamePacket.setAchievementsDisabled( true );
        startGamePacket.setDayCycleStopTime( 0 );
        startGamePacket.setRainLevel( 0 );
        startGamePacket.setLightningLevel( 0 );
        startGamePacket.setCommandsEnabled( true );
        startGamePacket.setMultiplayerGame( true );
        startGamePacket.setBroadcastingToLan( true );
        startGamePacket.getGamerules().addAll( this.player.getWorld().getGameRules().getGameRules() );
        startGamePacket.setLevelId( "" );
        startGamePacket.setLevelName( this.player.getWorld().getName() );
        startGamePacket.setGeneratorId( 1 );
        startGamePacket.setItemEntries( ItemPalette.getItemEntries() );
        startGamePacket.setXblBroadcastMode( GamePublishSetting.PUBLIC );
        startGamePacket.setPlatformBroadcastMode( GamePublishSetting.PUBLIC );
        startGamePacket.setDefaultPlayerPermission( PlayerPermission.MEMBER );
        startGamePacket.setServerChunkTickRange( 4 );
        startGamePacket.setVanillaVersion( Network.CODEC.getMinecraftVersion() );
        startGamePacket.setPremiumWorldTemplateId( "" );
        startGamePacket.setMultiplayerCorrelationId( "" );
        startGamePacket.setInventoriesServerAuthoritative( false );
        startGamePacket.setPlayerMovementSettings( SYNCED_PLAYER_MOVEMENT_SETTINGS );
        startGamePacket.setBlockRegistryChecksum( 0L );
        startGamePacket.setPlayerPropertyData( NbtMap.EMPTY );
        startGamePacket.setWorldTemplateId( UUID.randomUUID() );
        this.sendPacket( startGamePacket );

        AvailableEntityIdentifiersPacket availableEntityIdentifiersPacket = new AvailableEntityIdentifiersPacket();
        availableEntityIdentifiersPacket.setIdentifiers( EntityIdentifiers.getIdentifiers() );
        this.sendPacket( availableEntityIdentifiersPacket );

        BiomeDefinitionListPacket biomeDefinitionListPacket = new BiomeDefinitionListPacket();
        biomeDefinitionListPacket.setDefinitions( BiomeDefinitions.getBiomeDefinitions() );
        this.sendPacket( biomeDefinitionListPacket );

        CreativeContentPacket creativeContentPacket = new CreativeContentPacket();
        creativeContentPacket.setContents( CreativeItems.getCreativeItems() );
        this.sendPacket( creativeContentPacket );

        CraftingManager craftingManager = this.server.getCraftingManager();

        CraftingDataPacket craftingDataPacket = new CraftingDataPacket();
        craftingDataPacket.getCraftingData().addAll( craftingManager.getCraftingData() );
        craftingDataPacket.getPotionMixData().addAll( craftingDataPacket.getPotionMixData() );
        craftingDataPacket.getContainerMixData().addAll( craftingManager.getContainerMixData() );
        craftingDataPacket.setCleanRecipes( true );
        this.sendPacket( craftingDataPacket );
    }

    public void needNewChunks() {
        if ( this.requestedChunks ) return;

        this.requestedChunks = true;

        int currentXChunk = Utils.blockToChunk( this.player.getBlockX() );
        int currentZChunk = Utils.blockToChunk( this.player.getBlockZ() );
        int viewDistance = this.player.getViewDistance();

        try {
            int index = 0;
            for ( int sendXChunk = -viewDistance; sendXChunk <= viewDistance; sendXChunk++ ) {
                for ( int sendZChunk = -viewDistance; sendZChunk <= viewDistance; sendZChunk++ ) {
                    float distance = (float) FastMath.sqrt( sendZChunk * sendZChunk + sendXChunk * sendXChunk );
                    int chunkDistance = FastMath.round( distance );

                    if ( chunkDistance <= viewDistance ) {
                        long hash = Utils.toLong( currentXChunk + sendXChunk, currentZChunk + sendZChunk );

                        if ( !this.loadedChunks.contains( hash ) ) {
                            this.chunksInRadius[index++] = hash;
                        }
                    }
                }
            }
            final int chunkCount = index;
            if ( chunkCount > 0 ) {
                for ( int i = 0; i < chunkCount; i++ ) {
                    this.chunkLoadQueue.enqueue( this.chunksInRadius[i] );
                }
            }

            LongIterator iterator = this.loadedChunks.iterator();
            while ( iterator.hasNext() ) {
                long hash = iterator.nextLong();
                int x = Utils.fromHashX( hash );
                int z = Utils.fromHashZ( hash );

                if ( FastMath.abs( x - currentXChunk ) > viewDistance || FastMath.abs( z - currentZChunk ) > viewDistance ) {
                    this.player.getWorld().removeChunkLoader( x, z, this.player );
                    iterator.remove();
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            this.requestedChunks = false;
        }
    }

    public void requestChunk( int chunkX, int chunkZ ) {
        Chunk chunk = this.player.getWorld().getChunk( chunkX, chunkZ, true, true, true, this.player.getDimension() );

        long chunkHash = Utils.toLong( chunkX, chunkZ );
        if ( this.loadedChunks.contains( chunkHash ) ) {
            return;
        }

        if ( chunk.isPopulated() ) {
            this.sendChunk( chunk );
            return;
        }

        this.player.getWorld().addChunkLoader( chunkX, chunkZ, this.player );
    }

    public void sendNetworkPublisher() {
        NetworkChunkPublisherUpdatePacket packet = new NetworkChunkPublisherUpdatePacket();
        packet.setRadius( this.player.getViewDistance() << 4 );
        packet.setPosition( this.player.getLocation().toVector3i() );
        this.sendPacket( packet );
    }

    public int getSubY( int y ) {
        return ( y >> 4 ) + ( Math.abs( -64 ) >> 4 );
    }

    public void sendChunk( Chunk chunk ) {
        try {
            this.sendNetworkPublisher();
            this.sendPacket( chunk.createLevelChunkPacket() );
          /*
            if ( !chunk.getBlockEntities().isEmpty() ) {
                for ( BlockEntity blockEntity : chunk.getBlockEntities() ) {
                    BlockEntityDataPacket blockEntityDataPacket = new BlockEntityDataPacket();
                    blockEntityDataPacket.setBlockPosition( blockEntity.getBlock().getLocation().toVector3i() );
                    blockEntityDataPacket.setData( blockEntity.toCompound().build() );
                    this.sendPacket( blockEntityDataPacket );
                }
            }
           */
            long chunkHash = chunk.toChunkHash();
            this.loadedChunks.add( chunkHash );
        } catch ( Throwable e ) {
            try {
                e.printStackTrace();
                Thread.sleep( 1000 * 4 );
            } catch ( InterruptedException ex ) {
                throw new RuntimeException( ex );
            }
        }
    }

    public void resetChunks() {
        this.loadedChunks.clear();
        this.chunkLoadQueue.clear();
    }

    public boolean isChunkLoaded( int chunkX, int chunkZ ) {
        return this.loadedChunks.contains( Utils.toLong( chunkX, chunkZ ) );
    }

    public void close( String disconnectMessage ) {
        this.close( disconnectMessage, false );
    }

    public void close( String disconnectMessage, boolean hideScreen ) {
        this.session.disconnect( this.disconnectMessage = disconnectMessage, hideScreen );
    }

    public void sendPlayStatus( PlayStatusPacket.Status status ) {
        PlayStatusPacket playStatusPacket = new PlayStatusPacket();
        playStatusPacket.setStatus( status );
        this.sendPacketImmediately( playStatusPacket );
    }

    public void sendPacket( BedrockPacket packet ) {
        if ( !this.isClosed() && this.session.getPacketCodec() != null ) {
            PacketSendEvent packetSendEvent = new PacketSendEvent( this.player, packet );
            Server.getInstance().getPluginManager().callEvent( packetSendEvent );
            if ( packetSendEvent.isCancelled() ) {
                return;
            }
            this.session.sendPacket( packetSendEvent.getPacket() );
        }
    }

    public void sendPacketImmediately( BedrockPacket packet ) {
        if ( !this.isClosed() ) {
            this.session.sendPacketImmediately( packet );
        }
    }

    public boolean isClosed() {
        return this.session.isClosed();
    }

    public boolean isLoggedIn() {
        return this.loggedIn.get();
    }

    public boolean isSpawned() {
        return this.loggedIn.get();
    }

    public Server getServer() {
        return this.server;
    }

    public BedrockServerSession getSession() {
        return this.session;
    }

    public LoginData getLoginData() {
        return this.loginData;
    }

    public void setLoginData( LoginData loginData ) {
        if ( this.loginData == null ) {
            this.loginData = loginData;
            this.player.setNameTag( loginData.getDisplayName() );
            this.player.setUUID( loginData.getUuid() );
            this.player.setSkin( loginData.getSkin() );
            this.player.setDeviceInfo( loginData.getDeviceInfo() );

            this.player.loadPlayerData();
        }
    }

    public Player getPlayer() {
        return this.player;
    }
}
