package org.jukeboxmc.blockentity;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtMapBuilder;
import com.nukkitx.protocol.bedrock.packet.BlockEntityDataPacket;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAir;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class BlockEntity {

    protected Block block;
    private boolean isMoveable = true;

    public BlockEntity( Block block ) {
        this.block = block;
    }

    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        return false;
    }

    public void update( long currentTick ) {

    }

    public void fromCompound( NbtMap compound ) {
        this.isMoveable = compound.getBoolean( "isMovable", true );
    }

    public NbtMapBuilder toCompound() {
        NbtMapBuilder compound = NbtMap.builder();
        Vector position = this.block.getLocation();
        compound.putString( "id", BlockEntityType.getId( this.getClass() ) );
        compound.putInt( "x", position.getBlockX() );
        compound.putInt( "y", position.getBlockY() );
        compound.putInt( "z", position.getBlockZ() );
        compound.putBoolean( "isMovable", this.isMoveable );
        return compound;
    }

    public BlockEntity spawn() {
        World world = this.block.getWorld();
        Vector location = this.block.getLocation();
        BlockEntityDataPacket blockEntityDataPacket = new BlockEntityDataPacket();
        blockEntityDataPacket.setBlockPosition( location.toVector3i() );
        blockEntityDataPacket.setData( this.toCompound().build() );
        world.sendDimensionPacket( blockEntityDataPacket, location.getDimension() );
        world.setBlockEntity( location, this, location.getDimension() );
        return this;
    }

    public void fromItem( Item item, NbtMapBuilder builder ) {
        builder.putString( "Name", item.getIdentifier() );
        builder.putShort( "Damage", (short) item.getMeta() );
        builder.putByte( "Count", (byte) item.getAmount() );

        if ( item.getNBT() != null ) {
            NbtMap nbt = item.getNBT();
            builder.putCompound( "tag", nbt );
        }
    }

    public Item toItem( NbtMap compound ) {
        if ( compound == null ) {
            return new ItemAir();
        }

        short data = compound.getShort( "Damage", (short) 0 );
        byte amount = compound.getByte( "Count", (byte) 0 );
        String name = compound.getString( "Name", null );

        NbtMap tag = compound.getCompound( "tag", NbtMap.EMPTY );

        if ( name != null ) {
            return new Item( name )
                    .setAmount( amount )
                    .setMeta( data )
                    .setNBT( tag );
        }
        return new ItemAir();
    }

    public void update( Player player ) {
        BlockEntityDataPacket blockEntityDataPacket = new BlockEntityDataPacket();
        blockEntityDataPacket.setBlockPosition( this.block.getLocation().toVector3i() );
        blockEntityDataPacket.setData( this.toCompound().build() );
        player.sendPacket( blockEntityDataPacket );
    }

    public Block getBlock() {
        return this.block;
    }

    public void setMoveable( boolean moveable ) {
        this.isMoveable = moveable;
    }

    public boolean isMoveable() {
        return this.isMoveable;
    }
}
