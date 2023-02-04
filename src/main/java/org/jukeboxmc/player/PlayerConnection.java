package org.jukeboxmc.player;

import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.network.util.DisconnectReason;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockServerSession;
import com.nukkitx.protocol.bedrock.data.*;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.packet.*;
import org.jukeboxmc.Server;
import org.jukeboxmc.crafting.CraftingManager;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.event.network.PacketReceiveEvent;
import org.jukeboxmc.event.network.PacketSendEvent;
import org.jukeboxmc.event.player.PlayerQuitEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.network.Network;
import org.jukeboxmc.network.handler.HandlerRegistry;
import org.jukeboxmc.network.handler.PacketHandler;
import org.jukeboxmc.player.data.LoginData;
import org.jukeboxmc.player.manager.PlayerChunkManager;
import org.jukeboxmc.util.BiomeDefinitions;
import org.jukeboxmc.util.CreativeItems;
import org.jukeboxmc.util.EntityIdentifiers;
import org.jukeboxmc.util.ItemPalette;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerConnection {

    private static final SyncedPlayerMovementSettings SYNCED_PLAYER_MOVEMENT_SETTINGS = new SyncedPlayerMovementSettings();

    static {
        SYNCED_PLAYER_MOVEMENT_SETTINGS.setMovementMode( AuthoritativeMovementMode.CLIENT );
        SYNCED_PLAYER_MOVEMENT_SETTINGS.setRewindHistorySize( 0 );
        SYNCED_PLAYER_MOVEMENT_SETTINGS.setServerAuthoritativeBlockBreaking( false );
    }

    private final Server server;
    private final BedrockServerSession session;

    private final AtomicBoolean loggedIn;
    private final AtomicBoolean spawned;

    private LoginData loginData;
    private final Player player;

    private String disconnectMessage;

    private final PlayerChunkManager playerChunkManager;

    public PlayerConnection( Server server, BedrockServerSession session ) {
        this.server = server;
        this.session = session;
        this.session.getHardcodedBlockingId().set( Item.create( ItemType.SHIELD ).getRuntimeId() );
        this.session.addDisconnectHandler( disconnectReason -> this.server.getScheduler().execute( () -> this.onDisconnect( disconnectReason ) ) );
        this.loggedIn = new AtomicBoolean( false );
        this.spawned = new AtomicBoolean( false );
        this.player = new Player( server, this );
        this.playerChunkManager = new PlayerChunkManager( this.player );
        session.setPacketCodec( Network.CODEC );
        session.setBatchHandler( ( bedrockSession, byteBuf, packets ) -> {
            for ( BedrockPacket packet : packets ) {
                try {
                    server.getScheduler().execute( () -> {
                        PacketReceiveEvent packetReceiveEvent = new PacketReceiveEvent( this.player, packet );
                        server.getPluginManager().callEvent( packetReceiveEvent );
                        if ( packetReceiveEvent.isCancelled() ) {
                            return;
                        }
                        PacketHandler<BedrockPacket> packetHandler = (PacketHandler<BedrockPacket>) HandlerRegistry.getPacketHandler( packetReceiveEvent.getPacket().getClass() );
                        if ( packetHandler != null ) {
                            packetHandler.handle( packetReceiveEvent.getPacket(), this.server, this.player );
                        } else {
                            this.server.getLogger().info( "Handler missing for packet: " + packet.getClass().getSimpleName() );
                        }
                    } );
                } catch ( Throwable throwable ) {
                    throwable.printStackTrace();
                }
            }
        } );
    }

    public void update() {
        if ( this.isClosed() || !this.loggedIn.get() ) {
            return;
        }

        if ( this.spawned.get() ) {
            this.playerChunkManager.queueNewChunks();
        }

        this.playerChunkManager.sendQueued();

        if ( this.playerChunkManager.getChunksSent() >= 25 && !this.spawned.get() && this.player.getTeleportLocation() == null ) {
            this.doFirstSpawn();
        }
    }

    private void onDisconnect( DisconnectReason disconnectReason ) {
        this.server.removePlayer( this.player );

        this.player.getWorld().removeEntity( this.player );
        this.player.getChunk().removeEntity( this.player );

        this.player.getInventory().removeViewer( this.player );
        this.player.getArmorInventory().removeViewer( this.player );
        this.player.getCursorInventory().removeViewer( this.player );

        this.server.removeFromTabList( this.player );

        this.playerChunkManager.clear();

        this.player.close();

        PlayerQuitEvent playerQuitEvent = new PlayerQuitEvent( this.player, "§e" + this.player.getName() + " left the game" );
        Server.getInstance().getPluginManager().callEvent( playerQuitEvent );
        if ( playerQuitEvent.getQuitMessage() != null && !playerQuitEvent.getQuitMessage().isEmpty() ) {
            this.server.broadcastMessage( playerQuitEvent.getQuitMessage() );
        }
        this.server.getLogger().info( this.player.getName() + " logged out reason: " + ( this.disconnectMessage == null ? this.parseDisconnectMessage( disconnectReason ) : this.disconnectMessage ) );
    }

    private void doFirstSpawn() {
        this.spawned.set( true );

        this.player.getWorld().addEntity( this.player );

        SetEntityDataPacket setEntityDataPacket = new SetEntityDataPacket();
        setEntityDataPacket.setRuntimeEntityId( this.player.getEntityId() );
        setEntityDataPacket.getMetadata().putAll( this.player.getMetadata().getEntityDataMap() );
        setEntityDataPacket.setTick( this.server.getCurrentTick() );
        this.sendPacket( setEntityDataPacket );

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

        this.server.addToTabList( this.player );
        if ( this.server.getOnlinePlayers().size() > 1 ) {
            PlayerListPacket playerListPacket = new PlayerListPacket();
            playerListPacket.setAction( PlayerListPacket.Action.ADD );
            this.server.getPlayerListEntry().forEach( ( uuid, entry ) -> {
                if ( uuid != this.player.getUUID() ) {
                    playerListPacket.getEntries().add( entry );
                }
            } );
            this.player.getPlayerConnection().sendPacket( playerListPacket );
        }

        this.player.getInventory().addViewer( this.player );
        this.player.getInventory().sendContents( this.player );

        this.player.getCursorInventory().addViewer( this.player );
        this.player.getCursorInventory().sendContents( this.player );

        this.player.getArmorInventory().addViewer( this.player );
        this.player.getArmorInventory().sendContents( this.player );

        PlayStatusPacket playStatusPacket = new PlayStatusPacket();
        playStatusPacket.setStatus( PlayStatusPacket.Status.PLAYER_SPAWN );
        this.sendPacket( playStatusPacket );

        SetTimePacket setTimePacket = new SetTimePacket();
        setTimePacket.setTime( this.player.getWorld().getWorldTime() );
        this.sendPacket( setTimePacket );

        for ( Player onlinePlayer : this.server.getOnlinePlayers() ) {
            if ( onlinePlayer != null && onlinePlayer.getDimension() == this.player.getDimension() ) {
                this.player.spawn( onlinePlayer );
                onlinePlayer.spawn( this.player );
            }
        }

        this.server.getLogger().info(
                this.player.getName() + " logged in [World=" + this.player.getWorld().getName() + ", X=" +
                        this.player.getBlockX() + ", Y=" + this.player.getBlockY() + ", Z=" + this.player.getBlockZ() +
                        ", Dimension=" + this.player.getLocation().getDimension().name() + "]" );
    }

    public void initializePlayer() {
        this.loggedIn.set( true );

        StartGamePacket startGamePacket = new StartGamePacket();
        startGamePacket.setServerEngine( "JukeboxMC" );
        startGamePacket.setUniqueEntityId( this.player.getEntityId() );
        startGamePacket.setRuntimeEntityId( this.player.getEntityId() );
        startGamePacket.setPlayerGameType( this.player.getGameMode().toGameType() );
        startGamePacket.setPlayerPosition( this.player.getLocation().toVector3f().add( 0, 2, 0 ) ); //TODO
        startGamePacket.setDefaultSpawn( this.player.getLocation().toVector3i().add( 0, 2, 0 ) ); //TODO
        startGamePacket.setRotation( Vector2f.from( this.player.getPitch(), this.player.getYaw() ) );
        startGamePacket.setSeed( 0L ); //TODO
        startGamePacket.setDimensionId( this.player.getLocation().getDimension().ordinal() );
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
        startGamePacket.setItemEntries( ItemPalette.getEntries() );
        startGamePacket.setXblBroadcastMode( GamePublishSetting.PUBLIC );
        startGamePacket.setPlatformBroadcastMode( GamePublishSetting.PUBLIC );
        startGamePacket.setDefaultPlayerPermission( PlayerPermission.MEMBER );
        startGamePacket.setServerChunkTickRange( 4 );
        startGamePacket.setVanillaVersion( Network.CODEC.getMinecraftVersion() );
        startGamePacket.setPremiumWorldTemplateId( "" );
        startGamePacket.setMultiplayerCorrelationId( "" );
        startGamePacket.setInventoriesServerAuthoritative( true );
        startGamePacket.setPlayerMovementSettings( SYNCED_PLAYER_MOVEMENT_SETTINGS );
        startGamePacket.setBlockRegistryChecksum( 0L );
        startGamePacket.setPlayerPropertyData( NbtMap.EMPTY );
        startGamePacket.setWorldTemplateId( new UUID( 0, 0 ) );
        startGamePacket.setChatRestrictionLevel( ChatRestrictionLevel.NONE );
        startGamePacket.setDisablingPlayerInteractions( false );
        startGamePacket.setClientSideGenerationEnabled( false );
        this.sendPacket( startGamePacket );

        AvailableEntityIdentifiersPacket availableEntityIdentifiersPacket = new AvailableEntityIdentifiersPacket();
        availableEntityIdentifiersPacket.setIdentifiers( EntityIdentifiers.getIdentifiers() );
        this.sendPacket( availableEntityIdentifiersPacket );

        BiomeDefinitionListPacket biomeDefinitionListPacket = new BiomeDefinitionListPacket();
        biomeDefinitionListPacket.setDefinitions( BiomeDefinitions.getBiomeDefinitions() );
        this.sendPacket( biomeDefinitionListPacket );

        CreativeContentPacket creativeContentPacket = new CreativeContentPacket();
        creativeContentPacket.setContents( CreativeItems.getCreativeItems().toArray( new ItemData[0] ) );
        this.sendPacket( creativeContentPacket );

        CraftingManager craftingManager = this.server.getCraftingManager();
        CraftingDataPacket craftingDataPacket = new CraftingDataPacket();
        craftingDataPacket.getCraftingData().addAll( craftingManager.getCraftingData() );
        craftingDataPacket.getPotionMixData().addAll( craftingDataPacket.getPotionMixData() );
        craftingDataPacket.getContainerMixData().addAll( craftingManager.getContainerMixData() );
        craftingDataPacket.setCleanRecipes( true );
        this.sendPacket( craftingDataPacket );
    }

    private String parseDisconnectMessage( DisconnectReason disconnectReason ) {
        switch ( disconnectReason ) {
            case ALREADY_CONNECTED -> {
                return "Already connected";
            }
            case SHUTTING_DOWN -> {
                return "Shutdown";
            }
            default -> {
                return "Disconnect";
            }
        }
    }

    public void disconnect() {
        this.onDisconnect( DisconnectReason.DISCONNECTED );
        this.session.disconnect();
    }

    public void disconnect( String message ) {
        this.session.disconnect( this.disconnectMessage = message );
        this.onDisconnect( null );
    }

    public void disconnect( String message, boolean hideReason ) {
        this.session.disconnect( this.disconnectMessage = message, hideReason );
        this.onDisconnect( null );
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

    public Server getServer() {
        return this.server;
    }

    public BedrockServerSession getSession() {
        return this.session;
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean isLoggedIn() {
        return this.loggedIn.get();
    }

    public boolean isSpawned() {
        return this.spawned.get();
    }

    public LoginData getLoginData() {
        return this.loginData;
    }

    public void setLoginData( LoginData loginData ) {
        if ( this.loginData == null ) {
            this.loginData = loginData;
            this.player.setName( loginData.getDisplayName() );
            this.player.setNameTag( loginData.getDisplayName() );
            this.player.setUUID( loginData.getUuid() );
            this.player.setSkin( loginData.getSkin() );
            this.player.setDeviceInfo( loginData.getDeviceInfo() );
        }
    }

    public PlayerChunkManager getPlayerChunkManager() {
        return this.playerChunkManager;
    }
}
