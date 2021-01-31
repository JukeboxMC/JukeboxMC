package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.StoneSlab2Type;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemStoneSlab2;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStoneSlab2 extends BlockSlab {

    public BlockStoneSlab2() {
        super( "minecraft:stone_slab2" );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        this.setStoneSlabType( StoneSlab2Type.values()[itemIndHand.getMeta()] );

        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockStoneSlab2 ) {
                BlockStoneSlab2 blockSlab = (BlockStoneSlab2) targetBlock;
                if ( blockSlab.isTopSlot() && blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( blockPosition, new BlockDoubleStoneSlab2().setStoneSlabType( this.getStoneSlabType() ) );
                    return;
                }
            } else if ( block instanceof BlockStoneSlab2 ) {
                BlockStoneSlab2 blockSlab = (BlockStoneSlab2) block;
                if ( blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( blockPosition, new BlockDoubleStoneSlab2().setStoneSlabType( this.getStoneSlabType() ) );
                    return;
                }
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockStoneSlab2 ) {
                BlockStoneSlab2 blockSlab = (BlockStoneSlab2) targetBlock;
                if ( !blockSlab.isTopSlot() && blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( blockPosition, new BlockDoubleStoneSlab2().setStoneSlabType( this.getStoneSlabType() ) );
                    return;
                }
            } else if ( block instanceof BlockStoneSlab2 ) {
                BlockStoneSlab2 blockSlab = (BlockStoneSlab2) block;
                if ( blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( blockPosition, new BlockDoubleStoneSlab2().setStoneSlabType( this.getStoneSlabType() ) );
                    return;
                }
            }
        } else {
            if ( block instanceof BlockStoneSlab2 ) {
                BlockStoneSlab2 blockSlab = (BlockStoneSlab2) block;
                if ( blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( blockPosition, new BlockDoubleStoneSlab2().setStoneSlabType( this.getStoneSlabType() ) );
                    return;
                }
            }
        }
        world.setBlock( placePosition, this );
    }

    @Override
    public Item toItem() {
        return new ItemStoneSlab2().setMeta( this.getStoneSlabType().ordinal() );
    }

    public void setStoneSlabType( StoneSlab2Type stoneSlabType ) {
        this.setState( "stone_slab_type_2", stoneSlabType.name().toLowerCase() );
    }

    public StoneSlab2Type getStoneSlabType() {
        return this.stateExists( "stone_slab_type_2" ) ? StoneSlab2Type.valueOf( this.getStringState( "stone_slab_type_2" ).toUpperCase() ) : StoneSlab2Type.RED_SANDSTONE;
    }
}
