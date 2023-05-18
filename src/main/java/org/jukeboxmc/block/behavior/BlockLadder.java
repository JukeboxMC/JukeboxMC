package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLadder extends Block implements Waterlogable {

    public BlockLadder( Identifier identifier ) {
        super( identifier );
    }

    public BlockLadder( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        if ( !targetBlock.isTransparent() && blockFace != BlockFace.UP && blockFace != BlockFace.DOWN ) {
            this.setBlockFace( blockFace );
            if (world.getBlock(placePosition) instanceof BlockWater blockWater && blockWater.getLiquidDepth() == 0) {
                world.setBlock(placePosition, Block.create(BlockType.WATER), 1, false);
            }
            world.setBlock(placePosition, this);
        }
        return false;
    }

    @Override
    public int getWaterLoggingLevel() {
        return 1;
    }

    public void setBlockFace(BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
