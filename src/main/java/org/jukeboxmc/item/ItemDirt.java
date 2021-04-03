package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDirt;
import org.jukeboxmc.nbt.NbtMapBuilder;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDirt extends Item {

    public ItemDirt() {
        super( "minecraft:dirt", 3 );
    }

    @Override
    public BlockDirt getBlock() {
        return new BlockDirt();
    }

    public void setDirtType( DirtType dirtType ) {
        this.setMeta( dirtType.ordinal() );
    }

    public DirtType getDirtType() {
        return DirtType.values()[this.getMeta()];
    }

    public enum DirtType {
        DIRT,
        COARSA
    }
}
