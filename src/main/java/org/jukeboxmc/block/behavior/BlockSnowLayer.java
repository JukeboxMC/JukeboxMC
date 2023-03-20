package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSnowLayer extends Block {

    public BlockSnowLayer( Identifier identifier ) {
        super( identifier );
    }

    public BlockSnowLayer( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock(@NotNull Player player, @NotNull World world, Vector blockPosition, @NotNull Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block block = world.getBlock( blockPosition );

        if (!(world.getBlock( placePosition.subtract( 0, 1, 0 ) ).isSolid())) {
            return false;
        }

        if ( block instanceof BlockSnowLayer blockSnowLayer) {
            if ( blockSnowLayer.getHeight() != 7 ) {
                blockSnowLayer.setHeight( blockSnowLayer.getHeight() + 1 );
                world.setBlock( blockPosition, blockSnowLayer );
            } else {
                block = world.getBlock( placePosition );
                if ( block instanceof BlockSnowLayer ) {
                    blockSnowLayer = (BlockSnowLayer) block;
                    if ( blockSnowLayer.getHeight() != 7 ) {
                        blockSnowLayer.setHeight( blockSnowLayer.getHeight() + 1 );
                        world.setBlock( placePosition, blockSnowLayer );
                    } else {
                        this.setHeight( 0 );
                        world.setBlock( placePosition, this );
                    }
                } else {
                    this.setHeight( 0 );
                    world.setBlock( placePosition, this );
                }
            }
        } else {
            this.setHeight( 0 );
            world.setBlock( placePosition, this );
        }
        return true;
    }

    @Override
    public boolean canBeReplaced( Block block ) {
        if ( block instanceof BlockSnowLayer ) {
            return this.getHeight() != 7;
        } else {
            return false;
        }
    }

    public void setCovered( boolean value ) {
        this.setState( "covered_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isCovered() {
        return this.stateExists( "covered_bit" ) && this.getByteState( "covered_bit" ) == 1;
    }

    public BlockSnowLayer setHeight( int value ) {
        return this.setState( "height", value );
    }

    public int getHeight() {
        return this.stateExists( "height" ) ? this.getIntState( "height" ) : 0;
    }
}
