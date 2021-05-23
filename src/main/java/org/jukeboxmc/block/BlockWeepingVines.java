package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWeepingVines;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWeepingVines extends Block {

    public BlockWeepingVines() {
        super( "minecraft:weeping_vines" );
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block block = world.getBlock( placePosition ).getSide( BlockFace.UP );

        if ( blockFace != BlockFace.UP && block.getBlockType() != BlockType.AIR) {
            world.setBlock( placePosition, this );
            return true;
        }
        return false;
    }

    @Override
    public ItemWeepingVines toItem() {
        return new ItemWeepingVines();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WEEPING_VINES;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public void setWeepingVinesAge( int value ) {
        this.setState( "weeping_vines_age", value );
    }

    public int getWeepingVinesAge() {
        return this.stateExists( "weeping_vines_age" ) ? this.getIntState( "weeping_vines_age" ) : 0;
    }
}
