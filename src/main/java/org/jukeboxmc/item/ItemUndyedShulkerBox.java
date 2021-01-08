package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockUndyedShulkerBox;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemUndyedShulkerBox extends Item {

    public ItemUndyedShulkerBox() {
        super( "minecraft:undyed_shulker_box", 205 );
    }

    @Override
    public BlockUndyedShulkerBox getBlock() {
        return new BlockUndyedShulkerBox();
    }
}
