package jukeboxmc.item;

import org.jukeboxmc.block.BlockRedMushroomBlock;
import org.jukeboxmc.block.BlockType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedMushroomBlock extends Item {

    public ItemRedMushroomBlock( int blockRuntimeId ) {
        super( "minecraft:red_mushroom_block", blockRuntimeId );
    }

    @Override
    public BlockRedMushroomBlock getBlock() {
        return (BlockRedMushroomBlock) BlockType.getBlock( this.blockRuntimeId );
    }
}
