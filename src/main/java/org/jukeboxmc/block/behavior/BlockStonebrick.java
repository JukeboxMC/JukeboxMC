package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.StoneBrickType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemStonebrick;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStonebrick extends Block {

    public BlockStonebrick( Identifier identifier ) {
        super( identifier );
    }

    public BlockStonebrick( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public Item toItem() {
        return Item.<ItemStonebrick>create( ItemType.STONEBRICK ).setStoneBrickType( this.getStoneBrickType() );
    }

    public BlockStonebrick setStoneBrickType( StoneBrickType stoneBrickType ) {
        return this.setState( "stone_brick_type", stoneBrickType.name().toLowerCase() );
    }

    public StoneBrickType getStoneBrickType() {
        return this.stateExists( "stone_brick_type" ) ? StoneBrickType.valueOf( this.getStringState( "stone_brick_type" ) ) : StoneBrickType.DEFAULT;
    }
}
