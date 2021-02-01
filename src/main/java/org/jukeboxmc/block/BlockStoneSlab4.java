package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.StoneSlab4Type;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemStoneSlab4;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStoneSlab4 extends BlockSlab {

    public BlockStoneSlab4() {
        super( "minecraft:stone_slab4" );
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        this.setStoneSlabType( StoneSlab4Type.values()[itemIndHand.getMeta()] );

        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockStoneSlab4 ) {
                BlockStoneSlab4 blockSlab = (BlockStoneSlab4) targetBlock;
                if ( blockSlab.isTopSlot() && blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( blockPosition, new BlockDoubleStoneSlab4().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            } else if ( block instanceof BlockStoneSlab4 ) {
                BlockStoneSlab4 blockSlab = (BlockStoneSlab4) block;
                if ( blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( placePosition, new BlockDoubleStoneSlab4().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockStoneSlab4 ) {
                BlockStoneSlab4 blockSlab = (BlockStoneSlab4) targetBlock;
                if ( !blockSlab.isTopSlot() && blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( blockPosition, new BlockDoubleStoneSlab4().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            } else if ( block instanceof BlockStoneSlab4 ) {
                BlockStoneSlab4 blockSlab = (BlockStoneSlab4) block;
                if ( blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( placePosition, new BlockDoubleStoneSlab4().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            }
        } else {
            if ( block instanceof BlockStoneSlab4 ) {
                BlockStoneSlab4 blockSlab = (BlockStoneSlab4) block;
                if ( blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( placePosition, new BlockDoubleStoneSlab4().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            }
        }
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public Item toItem() {
        return new ItemStoneSlab4().setMeta( this.getStoneSlabType().ordinal() );
    }

    public void setStoneSlabType( StoneSlab4Type stoneSlabType ) {
        this.setState( "stone_slab_type_4", stoneSlabType.name().toLowerCase() );
    }

    public StoneSlab4Type getStoneSlabType() {
        return this.stateExists( "stone_slab_type_4" ) ? StoneSlab4Type.valueOf( this.getStringState( "stone_slab_type_4" ).toUpperCase() ) : StoneSlab4Type.STONE;
    }

}
