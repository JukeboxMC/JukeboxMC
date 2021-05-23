package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemSnowLayer;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSnowLayer extends Block {

    public BlockSnowLayer() {
        super( "minecraft:snow_layer" );
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block block = world.getBlock( placePosition ).getSide( blockFace.opposite() );

        if ( block instanceof BlockSnowLayer ) {
            BlockSnowLayer blockSnowLayer = (BlockSnowLayer) block;
            if ( blockSnowLayer.getHeight() != 7 ) {
                blockSnowLayer.setHeight( blockSnowLayer.getHeight() + 1 );
                world.setBlock( blockPosition, blockSnowLayer );
            } else {
                block = world.getBlock( placePosition );
                if ( block instanceof BlockSnowLayer ) {
                    blockSnowLayer = (BlockSnowLayer) block;
                    if ( blockSnowLayer.getHeight() != 7 ) {
                        blockSnowLayer.setHeight( blockSnowLayer.getHeight() + 1 );
                        world.setBlock( placePosition, blockSnowLayer );
                    } else {
                        this.setHeight( 0 );
                        world.setBlock( placePosition, this );
                    }
                } else {
                    this.setHeight( 0 );
                    world.setBlock( placePosition, this );
                }
            }
            return true;
        } else {
            this.setHeight( 0 );
            world.setBlock( placePosition, this );
            return true;
        }
    }

    @Override
    public boolean canBeReplaced( Block block ) {
        return block instanceof BlockSnowLayer;
    }

    @Override
    public ItemSnowLayer toItem() {
        return new ItemSnowLayer( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SNOW_LAYER;
    }

    @Override
    public boolean isSolid() {
        return this.getHeight() == 1;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public void setCovered( boolean value ) {
        this.setState( "covered_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isCovered() {
        return this.stateExists( "covered_bit" ) && this.getByteState( "covered_bit" ) == 1;
    }

    public BlockSnowLayer setHeight( int value ) {
        return this.setState( "height", value );
    }

    public int getHeight() {
        return this.stateExists( "height" ) ? this.getIntState( "height" ) : 0;
    }
}
