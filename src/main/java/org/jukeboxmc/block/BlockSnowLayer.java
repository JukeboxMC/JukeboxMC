package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemSnowLayer;
import org.jukeboxmc.item.ItemSnowball;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

import java.util.Collections;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSnowLayer extends Block {

    public BlockSnowLayer() {
        super( "minecraft:snow_layer" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block block = world.getBlock( blockPosition );

        if (!(world.getBlock( placePosition.subtract( 0, 1, 0 ) ).isSolid())) {
            return false;
        }

        if ( block instanceof BlockSnowLayer ) {
            BlockSnowLayer blockSnowLayer = (BlockSnowLayer) block;
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

    @Override
    public ItemSnowLayer toItem() {
        return new ItemSnowLayer( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SNOW_LAYER;
    }

    @Override
    public boolean isSolid() {
        return this.getHeight() == 7;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public double getHardness() {
        return 0.1;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.SHOVEL;
    }

    @Override
    public List<Item> getDrops( Item itemInHand ) {
        if ( itemInHand.getTierType().ordinal() >= this.getTierType().ordinal() ) {
            int amount;
            if ( this.getHeight() == 0 || this.getHeight() == 1 || this.getHeight() == 2 ) {
                amount = 1;
            } else if ( this.getHeight() == 3 || this.getHeight() == 4 ) {
                amount = 2;
            } else if ( this.getHeight() == 5 || this.getHeight() == 6 ) {
                amount = 3;
            } else {
                amount = 4;
            }
            return Collections.singletonList( new ItemSnowball().setAmount( amount ) );
        }
        return Collections.emptyList();
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
