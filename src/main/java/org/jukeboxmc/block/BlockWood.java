package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.WoodType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWood;
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
    public ItemWood toItem() {
        return new ItemWood( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WOOD;
    }

    public BlockWood setWoodType( WoodType woodType ) {
       return this.setState( "wood_type", woodType.name().toLowerCase() );
    }

    public WoodType getWoodType() {
        return this.stateExists( "wood_type" ) ? WoodType.valueOf( this.getStringState( "wood_type" ) ) : WoodType.OAK;
    }

    public BlockWood setStripped( boolean value ) {
       return this.setState( "stripped_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isStripped() {
        return this.stateExists( "stripped_bit" ) && this.getByteState( "stripped_bit" ) == 1;
    }

    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ) ) : Axis.Y;
    }

}
