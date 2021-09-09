package jukeboxmc.block;

import org.jukeboxmc.item.ItemSealantern;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSealantern extends Block {

    public BlockSealantern() {
        super( "minecraft:sealantern" );
    }

    @Override
    public ItemSealantern toItem() {
        return new ItemSealantern();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SEALANTERN;
    }

}
