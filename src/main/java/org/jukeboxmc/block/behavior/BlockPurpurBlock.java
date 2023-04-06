package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.Axis;
import org.jukeboxmc.block.data.PurpurType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemPurpurBlock;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPurpurBlock extends Block {

    public BlockPurpurBlock( Identifier identifier ) {
        super( identifier );
    }

    public BlockPurpurBlock( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
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
    public Item toItem() {
        return Item.<ItemPurpurBlock>create( ItemType.PURPUR_BLOCK ).setPurpurType( this.getPurpurType() );
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
