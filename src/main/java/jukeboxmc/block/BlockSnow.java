package jukeboxmc.block;

import org.jukeboxmc.item.ItemSnow;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSnow extends Block {

    public BlockSnow() {
        super( "minecraft:snow" );
    }

    @Override
    public ItemSnow toItem() {
        return new ItemSnow();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SNOW;
    }

}
