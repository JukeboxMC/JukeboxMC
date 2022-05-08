package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemChorusPlant;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockChorusPlant extends Block {

    public BlockChorusPlant() {
        super( "minecraft:chorus_plant" );
    }

    @Override
    public ItemChorusPlant toItem() {
        return new ItemChorusPlant();
    }

    @Override
    public BlockType getType() {
        return BlockType.CHORUS_PLANT;
    }

}
