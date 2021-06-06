package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemLava;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFlowingLava extends BlockLiquid {

    public BlockFlowingLava() {
        super( "minecraft:flowing_lava" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        world.setBlock( placePosition, this, 0, false );
        world.scheduleBlockUpdate( new Location( world, placePosition ), this.getTickRate() );
        return true;
    }

    @Override
    public ItemLava toItem() {
        return new ItemLava();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LAVA;
    }

    @Override
    public boolean canBeReplaced( Block block ) {
        return true;
    }

    @Override
    public int getTickRate() {
        if ( this.location.getDimension() == Dimension.NETHER ) {
            return 10;
        }
        return 30;
    }

    @Override
    public BlockLiquid getBlock( int liquidDepth ) {
        BlockFlowingLava blockFlowingLava = new BlockFlowingLava();
        blockFlowingLava.setLiquidDepth( liquidDepth );
        return blockFlowingLava;
    }
}
