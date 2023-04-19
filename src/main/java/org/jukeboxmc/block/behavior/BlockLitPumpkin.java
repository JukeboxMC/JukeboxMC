package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.util.Identifier;

import java.util.Collections;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLitPumpkin extends Block {

    public BlockLitPumpkin( Identifier identifier ) {
        super( identifier );
    }

    public BlockLitPumpkin( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public List<Item> getDrops( Item item ) {
        return Collections.singletonList( this.toItem() );
    }
}
