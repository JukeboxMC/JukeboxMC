package jukeboxmc.block;

import org.jukeboxmc.item.ItemConduit;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockConduit extends BlockWaterlogable {

    public BlockConduit() {
        super( "minecraft:conduit" );
    }

    @Override
    public ItemConduit toItem() {
        return new ItemConduit();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CONDUIT;
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
