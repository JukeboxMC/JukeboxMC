package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.SkullType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSkull extends Item {

    public ItemSkull( Identifier identifier ) {
        super( identifier );
    }

    public ItemSkull( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public Block toBlock() {
        return Block.create( BlockType.SKULL );
    }

    public void setSkullType( SkullType skullType ) {
        this.setMeta( skullType.ordinal() );
    }

    public SkullType getSkullType() {
        return SkullType.values()[this.getMeta()];
    }
}
