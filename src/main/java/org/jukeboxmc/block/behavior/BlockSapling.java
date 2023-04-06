package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.SaplingType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemSapling;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSapling extends Block {

    public BlockSapling( Identifier identifier ) {
        super( identifier );
    }

    public BlockSapling( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public Item toItem() {
        return Item.<ItemSapling>create( ItemType.SAPLING ).setSaplingType( this.getSaplingType() );
    }

    public BlockSapling setSaplingType( SaplingType saplingType ) {
        return this.setState( "sapling_type", saplingType.name().toLowerCase() );
    }

    public SaplingType getSaplingType() {
        return this.stateExists( "sapling_type" ) ? SaplingType.valueOf( this.getStringState( "sapling_type" ) ) : SaplingType.OAK;
    }

    public void setAge( boolean value ) {
        this.setState( "age_bit", value ? 1 : 0 );
    }

    public boolean hasAge() {
        return this.stateExists( "age_bit" ) && this.getIntState( "age_bit" ) == 1;
    }
}
