package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.BlockCrops;
import org.jukeboxmc.block.data.UpdateReason;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.event.block.BlockGrowEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.util.Utils;
import org.jukeboxmc.world.World;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class BlockCropsStem extends BlockCrops {

    private static final double[][] DROP_CHANCES = new double[][]{
            { .8130, .1742, .0124, .0003 }, //0
            { .6510, .3004, .0462, .0024 }, //1
            { .5120, .3840, .0960, .0080 }, //2
            { .3944, .4302, .1564, .0190 }, //3
            { .2913, .4444, .2222, .0370 }, //4
            { .2160, .4320, .2880, .0640 }, //5
            { .1517, .3982, .3484, .1016 }, //6
            { .1016, .3484, .3982, .1517 }  //7
    };

    static {
        for ( double[] dropChance : DROP_CHANCES ) {
            double last = dropChance[0];
            for ( int i = 1; i < dropChance.length; i++ ) {
                last += dropChance[i];
                assert last <= 1.0;
                dropChance[i] = last;
            }
        }
    }

    public BlockCropsStem( Identifier identifier ) {
        super( identifier );
    }

    public BlockCropsStem( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public long onUpdate( UpdateReason updateReason ) {
        World world = this.location.getWorld();
        if ( updateReason.equals( UpdateReason.NORMAL ) ) {
            Block blockDown = this.getSide( BlockFace.DOWN );
            if ( !blockDown.getType().equals( BlockType.FARMLAND ) ) {
                world.setBlock( this.location, Block.create( BlockType.AIR ) );
                for ( Item item : this.getDrops( Item.create( ItemType.AIR ) ) ) {
                    world.dropItem( item, this.location.add( 0.5f, 0, 0.5f ), null ).spawn();
                }
                return -1;
            }
            BlockFace blockFace = this.getBlockFace();
            if ( blockFace.isHorizontal() && !this.getSide( blockFace ).getType().equals( this.getFruitType() ) ) {
                this.setBlockFace( BlockFace.DOWN );
                return -1;
            }
        } else if ( updateReason.equals( UpdateReason.RANDOM ) ) {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            if ( random.nextInt( 1, 2 ) == 1 ) {
                int growth = this.getGrowth();
                if ( growth < 7 ) {
                    BlockCropsStem block = (BlockCropsStem) this.clone();
                    block.setGrowth( growth + 1, false );
                    BlockGrowEvent blockGrowEvent = new BlockGrowEvent( this, block );
                    Server.getInstance().getPluginManager().callEvent( blockGrowEvent );
                    if ( !blockGrowEvent.isCancelled() ) {
                        this.setGrowth( growth + 1, true );
                    }
                    return -1;
                } else {
                    if ( !this.getBlockFace().equals( BlockFace.DOWN ) && !this.getBlockFace().equals( BlockFace.UP ) )
                        return -1;
                    BlockFace[] horizontal = BlockFace.getHorizontal();
                    for ( BlockFace blockFace : horizontal ) {
                        Block block = this.getSide( blockFace );
                        if ( block.getType().equals( BlockType.PUMPKIN ) ) {
                            return -1;
                        }
                    }

                    BlockFace blockFace = horizontal[random.nextInt( horizontal.length )];
                    Block sideBlock = this.getSide( blockFace );
                    Block blockDown = sideBlock.getSide( BlockFace.DOWN );
                    if ( sideBlock.getType().equals( BlockType.AIR ) && ( blockDown.getType().equals( BlockType.FARMLAND ) || blockDown.getType().equals( BlockType.GRASS ) || blockDown.getType().equals( BlockType.DIRT ) ) ) {
                        BlockGrowEvent blockGrowEvent = new BlockGrowEvent( sideBlock, this.getType().equals( BlockType.MELON_STEM ) ? Block.create( BlockType.MELON_BLOCK ) : Block.create( BlockType.PUMPKIN ) );
                        Server.getInstance().getPluginManager().callEvent( blockGrowEvent );
                        if ( !blockGrowEvent.isCancelled() ) {
                            this.setBlockFace( blockFace );
                            world.setBlock( sideBlock.getLocation(), blockGrowEvent.getNewState() );
                        }
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public List<Item> getDrops( Item item ) {
        double[] dropChance = DROP_CHANCES[Utils.clamp(getGrowth(), 0, DROP_CHANCES.length)];

        double random = ThreadLocalRandom.current().nextDouble();
        int amount = 0;
        while (random > dropChance[amount]) {
            amount++;
        }

        if (amount == 0) {
            return Collections.emptyList();
        }

        return Collections.singletonList( this.toSeedsItem().setAmount( amount ) );
    }

    public abstract BlockType getFruitType();

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
