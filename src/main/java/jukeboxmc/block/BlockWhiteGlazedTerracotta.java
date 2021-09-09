package jukeboxmc.block;

import org.jukeboxmc.item.ItemWhiteGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWhiteGlazedTerracotta extends BlockTerracotta {

    public BlockWhiteGlazedTerracotta() {
        super( "minecraft:white_glazed_terracotta" );
    }

    @Override
    public ItemWhiteGlazedTerracotta toItem() {
        return new ItemWhiteGlazedTerracotta();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WHITE_GLAZED_TERRACOTTA;
    }

}
