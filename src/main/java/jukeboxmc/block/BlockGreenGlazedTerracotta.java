package jukeboxmc.block;

import org.jukeboxmc.item.ItemGreenGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGreenGlazedTerracotta extends BlockTerracotta {

    public BlockGreenGlazedTerracotta() {
        super( "minecraft:green_glazed_terracotta" );
    }

    @Override
    public ItemGreenGlazedTerracotta toItem() {
        return new ItemGreenGlazedTerracotta();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GREEN_GLAZED_TERRACOTTA;
    }

}
