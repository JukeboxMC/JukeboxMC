package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemPiston;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPiston extends BlockWaterlogable {

    public BlockPiston() {
        super( "minecraft:piston" );
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
                this.setBlockFace( player.getDirection().toBlockFace() );
            }
        } else {
            this.setBlockFace( player.getDirection().toBlockFace() );
        }
        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
    }

    @Override
    public ItemPiston toItem() {
        return new ItemPiston();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.PISTON;
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
