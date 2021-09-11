package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAnvil;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.AnvilDamage;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAnvil extends Item {

    public ItemAnvil( int blockRuntimeId ) {
        super( "minecraft:anvil", blockRuntimeId );
    }

    @Override
    public BlockAnvil getBlock() {
        return (BlockAnvil) BlockType.getBlock( this.blockRuntimeId );
    }

    public AnvilDamage getAnvilType() {
        return this.getBlock().getDamage();
    }

}
