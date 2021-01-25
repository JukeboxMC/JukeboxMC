package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
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
public class BlockQuartzBlock extends Block {

    public BlockQuartzBlock() {
        super( "minecraft:quartz_block" );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setChiselType( ChiselType.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getChiselType().ordinal() );
    }

    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ).toUpperCase() ) : Axis.Y;
    }

    public void setChiselType( ChiselType chiselType ) {
        this.setState( "chisel_type", chiselType.name().toLowerCase() );
    }

    public ChiselType getChiselType() {
        return this.stateExists( "chisel_type" ) ? ChiselType.valueOf( this.getStringState( "chisel_type" ).toUpperCase() ) : ChiselType.DEFAULT;
    }

    public enum ChiselType {
        DEFAULT,
        CHISELED,
        LINES,
        SMOOTH
    }
}
