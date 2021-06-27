package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemPolishedDeepslateWall;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPolishedDeepslateWall extends BlockWall {

    public BlockPolishedDeepslateWall() {
        super( "minecraft:polished_deepslate_wall" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.updateWall();
        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
    }

    @Override
    public ItemPolishedDeepslateWall toItem() {
        return new ItemPolishedDeepslateWall();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.POLISHED_DEEPSLATE_WALL;
    }
}
