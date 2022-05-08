package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBuddingAmethyst;
import org.jukeboxmc.item.type.ItemToolType;

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
    public BlockType getType() {
        return BlockType.BUDDING_AMETHYST;
    }

    @Override
    public double getHardness() {
        return 1.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }
}
