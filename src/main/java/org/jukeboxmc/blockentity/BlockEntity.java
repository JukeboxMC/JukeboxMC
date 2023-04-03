package org.jukeboxmc.blockentity;

import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.nbt.NbtMapBuilder;
import org.cloudburstmc.protocol.bedrock.packet.BlockEntityDataPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntity {

    protected final Block block;
    protected final Dimension dimension;
    protected final BlockEntityType blockEntityType;
    protected boolean isMovable = true;
    protected boolean isSpawned;


    public BlockEntity( Block block, BlockEntityType blockEntityType ) {
        this.block = block;
        this.dimension = block.getLocation().getDimension();
        this.blockEntityType = blockEntityType;
    }

    public static <T extends BlockEntity> T create( BlockEntityType blockEntityType, Block block ) {
        try {
            Constructor<? extends BlockEntity> constructor = BlockEntityRegistry.getBlockEntityClass( blockEntityType ).getConstructor( Block.class, BlockEntityType.class );
            constructor.setAccessible( true );
            return (T) constructor.newInstance( block, blockEntityType );
        } catch ( NoSuchMethodException | InstantiationException | IllegalAccessException |
                  InvocationTargetException e ) {
            throw new RuntimeException( e );
        }
    }

    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        return false;
    }

    public void update( long currentTick ) {

    }

    public void fromCompound( NbtMap compound ) {
        this.isMovable = compound.getBoolean( "isMovable", true );
    }

    public NbtMapBuilder toCompound() {
        NbtMapBuilder compound = NbtMap.builder();
        Vector position = this.block.getLocation();
        compound.putString( "id", BlockEntityRegistry.getBlockEntityType( this.blockEntityType ) );
        compound.putInt( "x", position.getBlockX() );
        compound.putInt( "y", position.getBlockY() );
        compound.putInt( "z", position.getBlockZ() );
        compound.putBoolean( "isMovable", this.isMovable );
        compound.putString( "CustomName", "" );
        return compound;
    }

    public void fromItem( Item item, NbtMapBuilder builder ) {
        builder.putString( "Name", item.getIdentifier().getFullName() );
        builder.putShort( "Damage", (short) item.getMeta() );
        builder.putByte( "Count", (byte) item.getAmount() );

        if ( item.getNbt() != null ) {
            NbtMap nbt = item.getNbt();
            builder.putCompound( "tag", nbt );
        }
    }

    public Item toItem( NbtMap compound ) {
        if ( compound == null ) {
            return Item.create( ItemType.AIR );
        }

        short data = compound.getShort( "Damage", (short) 0 );
        byte amount = compound.getByte( "Count", (byte) 0 );
        String name = compound.getString( "Name", null );

        NbtMap tag = compound.getCompound( "tag", NbtMap.EMPTY );

        if ( name != null ) {
            return Item.create( Identifier.fromString( name ) ).setAmount( amount ).setMeta( data ).setNbt( tag );
        }
        return Item.create( ItemType.AIR );
    }

    public BlockEntity spawn() {
        World world = this.block.getLocation().getWorld();
        Vector location = this.block.getLocation();
        BlockEntityDataPacket blockEntityDataPacket = new BlockEntityDataPacket();
        blockEntityDataPacket.setBlockPosition( location.toVector3i() );
        blockEntityDataPacket.setData( this.toCompound().build() );
        world.setBlockEntity( location, this, location.getDimension() );
        world.sendDimensionPacket( blockEntityDataPacket, location.getDimension() );
        Server.getInstance().broadcastPacket( blockEntityDataPacket );
        this.isSpawned = true;
        return this;
    }

    public void update( Player player ) {
        BlockEntityDataPacket blockEntityDataPacket = new BlockEntityDataPacket();
        blockEntityDataPacket.setBlockPosition( this.block.getLocation().toVector3i() );
        blockEntityDataPacket.setData( this.toCompound().build() );
        player.getPlayerConnection().sendPacket( blockEntityDataPacket );
    }

    public Block getBlock() {
        return this.block;
    }

    public BlockEntityType getBlockEntityType() {
        return this.blockEntityType;
    }

    public boolean isSpawned() {
        return this.isSpawned;
    }

    public void setSpawned( boolean spawned ) {
        isSpawned = spawned;
    }
}
