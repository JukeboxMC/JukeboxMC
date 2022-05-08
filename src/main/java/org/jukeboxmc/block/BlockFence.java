package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.block.type.WoodType;
import org.jukeboxmc.item.ItemFence;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.AxisAlignedBB;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFence extends BlockWaterlogable {

    public BlockFence() {
        super( "minecraft:fence" );
    }

    @Override
    public ItemFence toItem() {
        return new ItemFence();
    }

    @Override
    public BlockType getType() {
        return BlockType.FENCE;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
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

    public BlockFence setWoodType( WoodType woodType ) {
        this.setState( "wood_type", woodType.name().toLowerCase() );
        return this;
    }

    public WoodType getWoodType() {
        return this.stateExists( "wood_type" ) ? WoodType.valueOf( this.getStringState( "wood_type" ) ) : WoodType.OAK;
    }
}
