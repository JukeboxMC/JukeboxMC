package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemRedstoneOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGlowingRedstoneOre extends Block {

    public BlockGlowingRedstoneOre() {
        super( "minecraft:lit_redstone_ore" );
    }

    @Override
    public ItemRedstoneOre toItem() {
        return new ItemRedstoneOre();
    }

    @Override
    public BlockType getType() {
        return BlockType.GLOWING_REDSTONE_ORE;
    }

}
