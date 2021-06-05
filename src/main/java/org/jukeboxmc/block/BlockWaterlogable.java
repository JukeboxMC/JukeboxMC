package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class BlockWaterlogable extends Block {

    public BlockWaterlogable( String identifier ) {
        super( identifier );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block block = world.getBlock( placePosition );
        if( block instanceof BlockFlowingWater || block instanceof BlockWater ) {
            if( super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace ) ) {
                world.setBlock( placePosition, block, 1, player.getDimension() );
                return true;
            }
            return false;
        }
        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
    }

    @Override
    public boolean onBlockBreak( Vector breakPosition, boolean isCreative ) {
        Block block = this.world.getBlock( breakPosition, 1 );
        if( block instanceof BlockFlowingWater || block instanceof BlockWater ) {
            if( super.onBlockBreak( breakPosition, isCreative ) ) {
                this.world.setBlock( breakPosition, block, 0, this.location.getDimension() );
                this.world.setBlock( breakPosition, BlockType.AIR.getBlock(), 1, this.location.getDimension() );
                return true;
            }

            return false;
        }

        return super.onBlockBreak( breakPosition, isCreative );
    }

    public int getWaterloggingLevel() {
        return 1;
    }
}
