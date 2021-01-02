package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDoublePlant;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDoublePlant extends Item {

    public ItemDoublePlant() {
        super( "minecraft:double_plant", 175 );
    }

    @Override
    public Block getBlock() {
        return new BlockDoublePlant();
    }
}
