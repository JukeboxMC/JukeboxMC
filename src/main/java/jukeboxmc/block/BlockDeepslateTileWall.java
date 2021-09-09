package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemDeepslateTileWall;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateTileWall extends BlockWall {

    public BlockDeepslateTileWall() {
        super( "minecraft:deepslate_tile_wall" );
    }
    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.updateWall();
        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
    }

    @Override
    public ItemDeepslateTileWall toItem() {
        return new ItemDeepslateTileWall();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_TILE_WALL;
    }
}
