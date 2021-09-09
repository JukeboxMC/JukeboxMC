package jukeboxmc.block;

import org.jukeboxmc.item.ItemDeadbush;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeadbush extends BlockWaterlogable {

    public BlockDeadbush() {
        super( "minecraft:deadbush" );
    }

    @Override
    public ItemDeadbush toItem() {
        return new ItemDeadbush();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEADBUSH;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
