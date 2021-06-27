package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemDeepslateBrickWall;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateBrickWall extends BlockWall {

    public BlockDeepslateBrickWall() {
        super( "minecraft:deepslate_brick_wall" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.updateWall();
        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
    }

    @Override
    public ItemDeepslateBrickWall toItem() {
        return new ItemDeepslateBrickWall();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_BRICK_WALL;
    }
}
