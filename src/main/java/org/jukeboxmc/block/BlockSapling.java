package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSapling extends Block {

    public BlockSapling() {
        super( "minecraft:sapling" );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setSaplingType( SaplingType.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getSaplingType().ordinal() );
    }

    public void setSaplingType( SaplingType saplingType ) {
        this.setState( "sapling_type", saplingType.name().toLowerCase() );
    }

    public SaplingType getSaplingType() {
        return this.stateExists( "sapling_type" ) ? SaplingType.valueOf( this.getStringState( "sapling_type" ).toUpperCase() ) : SaplingType.OAK;
    }

    public void setAge( boolean value ) {
        this.setState( "age_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean hasAge() {
        return this.stateExists( "age_bit" ) && this.getByteState( "age_bit" ) == 1;
    }
}
