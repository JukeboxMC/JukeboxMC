package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAcaciaDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaDoorBlock extends Item {

    public ItemAcaciaDoorBlock() {
        super( "minecraft:item.acacia_door", 196 );
    }


    @Override
    public BlockAcaciaDoor getBlock() {
        return new BlockAcaciaDoor();
    }
}
