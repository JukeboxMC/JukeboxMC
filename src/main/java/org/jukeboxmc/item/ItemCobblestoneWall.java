package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCobblestoneWall;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCobblestoneWall extends Item {

    public ItemCobblestoneWall() {
        super( "minecraft:cobblestone_wall", 139 );
    }

    @Override
    public Block getBlock() {
        return new BlockCobblestoneWall();
    }
}
