package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.StoneSlabType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemStoneSlab;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStoneSlab extends BlockSlab {

    public BlockStoneSlab() {
        super( "minecraft:stone_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        this.setStoneSlabType( StoneSlabType.values()[itemIndHand.getMeta()] );

        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockStoneSlab ) {
                BlockStoneSlab blockSlab = (BlockStoneSlab) targetBlock;
                if ( blockSlab.isTopSlot() && blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( blockPosition, new BlockDoubleStoneSlab().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            } else if ( block instanceof BlockStoneSlab ) {
                BlockStoneSlab blockSlab = (BlockStoneSlab) block;
                if ( blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( placePosition, new BlockDoubleStoneSlab().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockStoneSlab ) {
                BlockStoneSlab blockSlab = (BlockStoneSlab) targetBlock;
                if ( !blockSlab.isTopSlot() && blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( blockPosition, new BlockDoubleStoneSlab().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            } else if ( block instanceof BlockStoneSlab ) {
                BlockStoneSlab blockSlab = (BlockStoneSlab) block;
                if ( blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( placePosition, new BlockDoubleStoneSlab().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            }
        } else {
            if ( block instanceof BlockStoneSlab ) {
                BlockStoneSlab blockSlab = (BlockStoneSlab) block;
                if ( blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( placePosition, new BlockDoubleStoneSlab().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            }
        }
        world.setBlock( placePosition, this );
        return true;
    }


    @Override
    public Item toItem() {
        return new ItemStoneSlab().setMeta( this.getStoneSlabType().ordinal() );
    }

    public void setStoneSlabType( StoneSlabType stoneSlabType ) {
        this.setState( "stone_slab_type", stoneSlabType.name().toLowerCase() );
    }

    public StoneSlabType getStoneSlabType() {
        return this.stateExists( "stone_slab_type" ) ? StoneSlabType.valueOf( this.getStringState( "stone_slab_type" ).toUpperCase() ) : StoneSlabType.SMOOTH_STONE;
    }

}
