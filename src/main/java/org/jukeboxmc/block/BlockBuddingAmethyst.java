package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemBuddingAmethyst;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBuddingAmethyst extends Block{

    public BlockBuddingAmethyst() {
        super( "minecraft:budding_amethyst" );
    }

    @Override
    public ItemBuddingAmethyst toItem() {
        return new ItemBuddingAmethyst();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BUDDING_AMETHYST;
    }
}
