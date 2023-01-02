package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFence extends Block {

    public BlockFence( Identifier identifier ) {
        super( identifier );
    }

    public BlockFence( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        boolean north = this.canConnect( this.getSide( Direction.NORTH ) );
        boolean south = this.canConnect( this.getSide( Direction.SOUTH ) );
        boolean west = this.canConnect( this.getSide( Direction.WEST ) );
        boolean east = this.canConnect( this.getSide( Direction.EAST ) );
        float n = north ? 0 : 0.375f;
        float s = south ? 1 : 0.625f;
        float w = west ? 0 : 0.375f;
        float e = east ? 1 : 0.625f;
        return new AxisAlignedBB(
                this.location.getX() + w,
                this.location.getY(),
                this.location.getZ() + n,
                this.location.getX() + e,
                this.location.getY() + 1.5f,
                this.location.getZ() + s
        );
    }

    public boolean canConnect( Block block ) {
        return ( block instanceof BlockFence || block instanceof BlockFenceGate ) || block.isSolid() && !block.isTransparent();
    }
}
