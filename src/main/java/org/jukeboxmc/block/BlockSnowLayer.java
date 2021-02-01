package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSnowLayer extends Block {

    public BlockSnowLayer() {
        super( "minecraft:snow_layer" );
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block block = world.getBlock( blockPosition );

        if ( block instanceof BlockSnowLayer ) {
            BlockSnowLayer blockSnowLayer = (BlockSnowLayer) block;
            if ( blockSnowLayer.getHeight() != 7 ) {
                this.setHeight( blockSnowLayer.getHeight() + 1 );
                world.setBlock( blockPosition, this );
            }
        } else {
            this.setHeight( 0 );
            world.setBlock( placePosition, this );
        }
        return true;
    }

    public void setCovered( boolean value ) {
        this.setState( "covered_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isCovered() {
        return this.stateExists( "covered_bit" ) && this.getByteState( "covered_bit" ) == 1;
    }

    public void setHeight( int value ) { //0-7
        this.setState( "height", value );
    }

    public int getHeight() {
        return this.stateExists( "height" ) ? this.getIntState( "height" ) : 0;
    }
}
