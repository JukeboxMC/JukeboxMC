package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

import java.util.Collections;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGrass extends Block {

    public BlockGrass( Identifier identifier ) {
        super( identifier );
    }

    public BlockGrass( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public @NotNull List<Item> getDrops(Item item ) {
        return Collections.singletonList( Item.create( ItemType.DIRT ) );
    }
}
