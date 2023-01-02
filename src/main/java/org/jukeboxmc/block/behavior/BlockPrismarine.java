package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.PrismarineType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemPrismarine;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPrismarine extends Block {

    public BlockPrismarine( Identifier identifier ) {
        super( identifier );
    }

    public BlockPrismarine( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public Item toItem() {
        return Item.<ItemPrismarine>create( ItemType.PRISMARINE ).setPrismarineType( this.getPrismarineType() );
    }

    public BlockPrismarine setPrismarineType( PrismarineType prismarineType ) {
        return this.setState( "prismarine_block_type", prismarineType.name().toLowerCase() );
    }

    public PrismarineType getPrismarineType() {
        return this.stateExists( "prismarine_block_type" ) ? PrismarineType.valueOf( this.getStringState( "prismarine_block_type" ) ) : PrismarineType.DEFAULT;
    }
}
