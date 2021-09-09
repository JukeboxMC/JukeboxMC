package jukeboxmc.block;

import org.jukeboxmc.item.ItemSoulSoil;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSoulSoil extends Block {

    public BlockSoulSoil() {
        super( "minecraft:soul_soil" );
    }

    @Override
    public ItemSoulSoil toItem() {
        return new ItemSoulSoil();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SOUL_SOIL;
    }

}
