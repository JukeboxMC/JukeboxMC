package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemCandle;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCandle extends Block {

    public BlockCandle( Identifier identifier ) {
        super( identifier );
    }

    public BlockCandle( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block clickedBlock = world.getBlock( blockPosition );
        if ( clickedBlock instanceof BlockCandle blockCandle ) {
            if ( blockCandle.getIdentifier().equals( itemInHand.getIdentifier() ) ) {
                int candles = blockCandle.getCandles();
                if ( candles < 3 ) {
                    blockCandle.setCandles( candles + 1 );
                    world.setBlock( blockPosition, blockCandle, 0 );
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        world.setBlock( placePosition, this, 0 );
        return true;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        if ( itemInHand instanceof ItemCandle ) {
            return false;
        }
        if ( this.isLit() && !itemInHand.getType().equals( ItemType.FLINT_AND_STEEL ) ) {
            this.setLit( false );
            this.location.getWorld().playSound( blockPosition, SoundEvent.FIZZ );
            return true;
        } else if ( !this.isLit() && itemInHand.getType().equals( ItemType.FLINT_AND_STEEL ) ) {
            this.setLit( true );
            this.location.getWorld().playSound( blockPosition, SoundEvent.IGNITE );
            return true;
        }
        return false;
    }


    public BlockCandle setCandles( int value ) {
        return this.setState( "candles", value );
    }

    public int getCandles() {
        return this.stateExists( "candles" ) ? this.getIntState( "candles" ) : 0;
    }

    public BlockCandle setLit( boolean value ) {
        return this.setState( "lit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isLit() {
        return this.stateExists( "lit" ) && this.getByteState( "lit" ) == 1;
    }
}
