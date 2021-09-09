package jukeboxmc.block;

import org.jukeboxmc.item.ItemRedGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedGlazedTerracotta extends BlockTerracotta {

    public BlockRedGlazedTerracotta() {
        super( "minecraft:red_glazed_terracotta" );
    }

    @Override
    public ItemRedGlazedTerracotta toItem() {
        return new ItemRedGlazedTerracotta();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.RED_GLAZED_TERRACOTTA;
    }

}
