package jukeboxmc.block;

import org.jukeboxmc.item.ItemMagentaGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMagentaGlazedTerracotta extends BlockTerracotta {

    public BlockMagentaGlazedTerracotta() {
        super( "minecraft:magenta_glazed_terracotta" );
    }

    @Override
    public ItemMagentaGlazedTerracotta toItem() {
        return new ItemMagentaGlazedTerracotta();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MAGENTA_GLAZED_TERRACOTTA;
    }

}
