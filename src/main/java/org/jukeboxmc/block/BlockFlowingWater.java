package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWater;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFlowingWater extends BlockLiquid {

    public BlockFlowingWater() {
        super( "minecraft:flowing_water" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        world.setBlock( placePosition, this, 0, false );
        world.scheduleBlockUpdate( new Location( world, placePosition ), this.getTickRate() );
        return true;
    }

    @Override
    public ItemWater toItem() {
        return new ItemWater();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WATER;
    }

    @Override
    public boolean canBeReplaced( Block block ) {
        return true;
    }

    @Override
    public int getTickRate() {
        return 5;
    }

    @Override
    public boolean usesWaterLogging() {
        return true;
    }

    @Override
    public BlockLiquid getBlock( int liquidDepth ) {
        BlockFlowingWater blockFlowingWater = new BlockFlowingWater();
        blockFlowingWater.setLiquidDepth( liquidDepth );
        return blockFlowingWater;
    }
}
