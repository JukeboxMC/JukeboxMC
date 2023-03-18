package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBlackWool extends Block {

    public BlockBlackWool(Identifier identifier ) {
        super( identifier );
    }

    public BlockBlackWool(Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public Item toItem() {
        return Item.create( ItemType.BLACK_WOOL );
    }
}
