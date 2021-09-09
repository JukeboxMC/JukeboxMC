package jukeboxmc.item;

import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.BlockWool;
import org.jukeboxmc.block.type.BlockColor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWool extends Item {

    public ItemWool( int blockRuntimeId ) {
        super( "minecraft:wool", blockRuntimeId );
    }

    @Override
    public BlockWool getBlock() {
        return (BlockWool) BlockType.getBlock( this.blockRuntimeId );
    }

    public BlockColor getColor() {
        return this.getBlock().getColor();
    }
}
