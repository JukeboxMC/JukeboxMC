package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemBarrel;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBarrel extends Block {

    public BlockBarrel() {
        super( "minecraft:barrel" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        if ( Math.abs( player.getX() - this.getLocation().getX() ) < 2 && Math.abs( player.getZ() - this.getLocation().getZ() ) < 2 ) {
            double y = player.getY() + player.getEyeHeight();

            if ( y - this.getLocation().getY() > 2 ) {
                this.setBlockFace( BlockFace.UP );
            } else if ( this.getLocation().getY() - y > 0 ) {
                this.setBlockFace( BlockFace.DOWN );
            } else {
                this.setBlockFace( player.getDirection().toBlockFace().opposite() );
            }
        } else {
            this.setBlockFace( player.getDirection().toBlockFace().opposite() );
        }
        this.setOpen( false );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemBarrel toItem() {
        return new ItemBarrel();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BARREL;
    }

    public void setOpen( boolean value ) {
        this.setState( "open_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isOpen() {
        return this.stateExists( "open_bit" ) && this.getByteState( "open_bit" ) == 1;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
