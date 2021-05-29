package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.QuartzType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemQuartzBlock;
import org.jukeboxmc.math.Axis;
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
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
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
    public ItemQuartzBlock toItem() {
        return new ItemQuartzBlock(this.runtimeId);
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.QUARTZ_BLOCK;
    }

    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ) ) : Axis.Y;
    }

    public BlockQuartzBlock setQuartzType( QuartzType quartzType ) {
       return this.setState( "chisel_type", quartzType.name().toLowerCase() );
    }

    public QuartzType getQuartzType() {
        return this.stateExists( "chisel_type" ) ? QuartzType.valueOf( this.getStringState( "chisel_type" ) ) : QuartzType.DEFAULT;
    }

}
