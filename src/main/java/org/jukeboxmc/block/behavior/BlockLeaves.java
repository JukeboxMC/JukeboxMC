package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.LeafType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemLeaves;
import org.jukeboxmc.util.Identifier;

import java.util.Collections;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLeaves extends Block {

    public BlockLeaves( Identifier identifier ) {
        super( identifier );
    }

    @Override
    public Item toItem() {
        return Item.<ItemLeaves>create( ItemType.LEAVES ).setLeafType( this.getLeafType() );
    }

    @Override
    public List<Item> getDrops( Item item ) {
        if ( this.isCorrectToolType( item ) ) {
            return Collections.singletonList( this.toItem() );
        }
        return Collections.emptyList();
    }

    public BlockLeaves( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    public void setPersistent( boolean value ) {
        this.setState( "persistent_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isPersistent() {
        return this.stateExists( "persistent_bit" ) && this.getByteState( "persistent_bit" ) == 1;
    }

    public void setUpdate( boolean value ) {
        this.setState( "update_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isUpdate() {
        return this.stateExists( "update_bit" ) && this.getByteState( "update_bit" ) == 1;
    }

    public BlockLeaves setLeafType( LeafType leafType ) {
        return this.setState( "old_leaf_type", leafType.name().toLowerCase() );
    }

    public LeafType getLeafType() {
        return this.stateExists( "old_leaf_type" ) ? LeafType.valueOf( this.getStringState( "old_leaf_type" ) ) : LeafType.OAK;
    }
}
