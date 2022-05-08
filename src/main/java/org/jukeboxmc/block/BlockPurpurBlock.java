package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.PurpurType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemPurpurBlock;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Axis;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPurpurBlock extends Block {

    public BlockPurpurBlock() {
        super( "minecraft:purpur_block" );
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

        world.setBlock( placePosition, this, 0 );
        return true;
    }

    @Override
    public ItemPurpurBlock toItem() {
        return new ItemPurpurBlock( this.runtimeId );
    }

    @Override
    public BlockType getType() {
        return BlockType.PURPUR_BLOCK;
    }

    @Override
    public double getHardness() {
        return 1.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ) ) : Axis.Y;
    }

    public BlockPurpurBlock setPurpurType( PurpurType purpurType ) {
       return this.setState( "chisel_type", purpurType.name().toLowerCase() );
    }

    public PurpurType getPurpurType() {
        return this.stateExists( "chisel_type" ) ? PurpurType.valueOf( this.getStringState( "chisel_type" ) ) : PurpurType.DEFAULT;
    }

}
