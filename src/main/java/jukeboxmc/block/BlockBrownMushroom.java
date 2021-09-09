package jukeboxmc.block;

import org.jukeboxmc.item.ItemBrownMushroom;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBrownMushroom extends Block {

    public BlockBrownMushroom() {
        super( "minecraft:brown_mushroom" );
    }

    @Override
    public ItemBrownMushroom toItem() {
        return new ItemBrownMushroom();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BROWN_MUSHROOM;
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
