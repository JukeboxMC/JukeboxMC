package jukeboxmc.block;

import org.jukeboxmc.item.ItemAzalea;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAzalea extends Block{

    public BlockAzalea() {
        super( "minecraft:azalea" );
    }

    @Override
    public ItemAzalea toItem() {
        return new ItemAzalea();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.AZALEA;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
