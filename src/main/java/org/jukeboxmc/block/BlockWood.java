package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.WoodType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Axis;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWood extends Block {

    public BlockWood() {
        super( "minecraft:wood" );
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        int meta = itemIndHand.getMeta();

        if ( meta < 6 ) {
            this.setStripped( false );
        } else {
            this.setStripped( true );
        }

        if ( meta == 0 || meta == 8 ) {
            this.setWoodType( WoodType.OAK );
        } else if ( meta == 1 || meta == 9 ) {
            this.setWoodType( WoodType.SPRUCE );
        } else if ( meta == 2 || meta == 10 ) {
            this.setWoodType( WoodType.BIRCH );
        } else if ( meta == 3 || meta == 11 ) {
            this.setWoodType( WoodType.JUNGLE );
        } else if ( meta == 4 || meta == 12 ) {
            this.setWoodType( WoodType.ACACIA );
        } else if ( meta == 5 || meta == 13 ) {
            this.setWoodType( WoodType.DARK_OAK );
        }

        if ( blockFace == BlockFace.UP || blockFace == BlockFace.DOWN ) {
            this.setAxis( Axis.Y );
        } else if ( blockFace == BlockFace.NORTH || blockFace == BlockFace.SOUTH ) {
            this.setAxis( Axis.Z );
        } else {
            this.setAxis( Axis.X );
        }

        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getWoodType().ordinal() );
    }

    public void setWoodType( WoodType woodType ) {
        this.setState( "wood_type", woodType.name().toLowerCase() );
    }

    public WoodType getWoodType() {
        return this.stateExists( "wood_type" ) ? WoodType.valueOf( this.getStringState( "wood_type" ).toUpperCase() ) : WoodType.OAK;
    }

    public void setStripped( boolean value ) {
        this.setState( "stripped_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isStripped() {
        return this.stateExists( "stripped_bit" ) && this.getByteState( "stripped_bit" ) == 1;
    }

    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ).toUpperCase() ) : Axis.Y;
    }
}
