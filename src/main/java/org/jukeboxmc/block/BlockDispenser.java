package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemDispenser;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDispenser extends Block {

    public BlockDispenser() {
        super( "minecraft:dispenser" );
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setBlockFace( player.getDirection().toBlockFace().opposite() );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemDispenser toItem() {
        return new ItemDispenser();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DISPENSER;
    }

    public void setTriggered( boolean value ) {
        this.setState( "triggered_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isTriggered() {
        return this.stateExists( "triggered_bit" ) && this.getByteState( "triggered_bit" ) == 1;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
