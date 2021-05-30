package org.jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;
import org.jukeboxmc.network.packet.BlockEntityDataPacket;
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

    public void setCompound( NbtMap compound ) {
        this.isMoveable = compound.getBoolean( "isMovable", true );
    }

    public NbtMapBuilder toCompound() {
        NbtMapBuilder compound = NbtMap.builder();
        Vector position = this.block.getLocation();
        compound.putString( "id", BlockEntityType.getId( this.getClass() ) );
        compound.putInt( "x", position.getFloorX() );
        compound.putInt( "y", position.getFloorY() );
        compound.putInt( "z", position.getFloorZ() );
        compound.putBoolean( "isMovable", this.isMoveable );
        return compound;
    }

    public void spawn() {
        World world = this.block.getWorld();
        Vector location = this.block.getLocation();

        BlockEntityDataPacket blockEntityDataPacket = new BlockEntityDataPacket();
        blockEntityDataPacket.setBlockPosition( location );
        blockEntityDataPacket.setNbt( this.toCompound().build() );
        world.sendDimensionPacket( blockEntityDataPacket, location.getDimension() );
        world.setBlockEntity( location, this, location.getDimension() );
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
