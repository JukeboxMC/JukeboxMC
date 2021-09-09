package jukeboxmc.item;

import org.jukeboxmc.block.BlockDeepslateTileWall;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeepslateTileWall extends Item{

    public ItemDeepslateTileWall() {
        super( "minecraft:deepslate_tile_wall" );
    }

    @Override
    public BlockDeepslateTileWall getBlock() {
        return new BlockDeepslateTileWall();
    }
}
