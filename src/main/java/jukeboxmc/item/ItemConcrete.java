package jukeboxmc.item;

import org.jukeboxmc.block.BlockConcrete;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.BlockColor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemConcrete extends Item {

    public ItemConcrete( int blockRuntimeId ) {
        super( "minecraft:concrete", blockRuntimeId );
    }

    @Override
    public BlockConcrete getBlock() {
        return (BlockConcrete) BlockType.getBlock( this.blockRuntimeId );
    }

    public BlockColor getColor() {
        return this.getBlock().getColor();
    }

}
