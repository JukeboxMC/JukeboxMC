package jukeboxmc.block;

import org.jukeboxmc.item.ItemPinkGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPinkGlazedTerracotta extends BlockTerracotta {

    public BlockPinkGlazedTerracotta() {
        super( "minecraft:pink_glazed_terracotta" );
    }

    @Override
    public ItemPinkGlazedTerracotta toItem() {
        return new ItemPinkGlazedTerracotta();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.PINK_GLAZED_TERRACOTTA;
    }

}
