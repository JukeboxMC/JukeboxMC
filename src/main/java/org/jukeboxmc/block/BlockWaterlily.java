package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWaterlily;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaterlily extends Block {

    public BlockWaterlily() {
        super( "minecraft:waterlily" );
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block block = world.getBlock( blockPosition );

        if ( block.getBlockType() == BlockType.WATER || block.getBlockType() == BlockType.FLOWING_WATER ) {
            world.setBlock( placePosition, this );
            return true;
        }
        return false;
    }

    @Override
    public ItemWaterlily toItem() {
        return new ItemWaterlily();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WATERLILY;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
