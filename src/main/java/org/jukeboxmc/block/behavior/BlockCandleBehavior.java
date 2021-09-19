package org.jukeboxmc.block.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemCandleBehavior;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.LevelSound;
import org.jukeboxmc.world.World;

import java.util.Collections;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class BlockCandleBehavior extends Block {

    public BlockCandleBehavior( String identifier ) {
        super( identifier );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block clickedBlock = world.getBlock( blockPosition );
        if ( clickedBlock instanceof BlockCandleBehavior ) {
            BlockCandleBehavior blockCandle = (BlockCandleBehavior) clickedBlock;
            if ( blockCandle.getIdentifier().equals( itemIndHand.getIdentifier() ) ) {
                int candles = blockCandle.getCandles();
                if ( candles < 3 ) {
                    blockCandle.setCandles( candles + 1 );
                    world.setBlock( blockPosition, blockCandle );
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        if ( itemInHand instanceof ItemCandleBehavior ) {
            return false;
        }
        if ( this.isLit() && !itemInHand.getItemType().equals( ItemType.FLINT_AND_STEEL ) ) {
            this.setLit( false );
            this.world.setBlock( blockPosition, this );
            this.world.playSound( blockPosition, LevelSound.FIZZ );
            return true;
        } else if ( !this.isLit() && itemInHand.getItemType().equals( ItemType.FLINT_AND_STEEL ) ) {
            this.setLit( true );
            this.world.setBlock( blockPosition, this );
            this.world.playSound( blockPosition, LevelSound.IGNITE );
            return true;
        }
        return false;
    }

    @Override
    public double getHardness() {
        return 0.1;
    }

    @Override
    public boolean canBeReplaced( Block block ) {
        return block instanceof BlockCandleBehavior;
    }

    @Override
    public List<Item> getDrops( Item itemInHand ) {
        return Collections.singletonList( this.toItem().setAmount( this.getCandles() + 1 ) );
    }

    public BlockCandleBehavior setCandles( int value ) {
        return this.setState( "candles", value );
    }

    public int getCandles() {
        return this.stateExists( "candles" ) ? this.getIntState( "candles" ) : 0;
    }

    public BlockCandleBehavior setLit( boolean value ) {
        return this.setState( "lit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isLit() {
        return this.stateExists( "lit" ) && this.getByteState( "lit" ) == 1;
    }
}
