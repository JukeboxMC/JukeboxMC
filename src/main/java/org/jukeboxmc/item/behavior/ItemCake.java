package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

public class ItemCake extends Item {

    public ItemCake(Identifier identifier) {
        super(identifier);
    }

    public ItemCake(ItemType itemType) {
        super(itemType);
    }

    @Override
    public Block toBlock() {
        return Block.create(BlockType.CAKE);
    }
}
