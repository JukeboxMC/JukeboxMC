package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.StoneSlab4Type;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemStoneSlab2;
import org.jukeboxmc.item.ItemStoneSlab4;
import org.jukeboxmc.item.type.ItemToolType;
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
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockStoneSlab4 && ( (BlockStoneSlab4) targetBlock ).isTopSlot() && ( (BlockStoneSlab4) targetBlock ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( blockPosition, new BlockDoubleStoneSlab4().setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            } else if ( block instanceof BlockStoneSlab4 && ( (BlockStoneSlab4) block ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( placePosition, new BlockDoubleStoneSlab4().setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockStoneSlab4 && !( (BlockStoneSlab4) targetBlock ).isTopSlot() && ( (BlockStoneSlab4) targetBlock ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( blockPosition, new BlockDoubleStoneSlab4().setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            } else if ( block instanceof BlockStoneSlab4 && ( (BlockStoneSlab4) block ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( placePosition, new BlockDoubleStoneSlab4().setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            }
        } else {
            if ( block instanceof BlockStoneSlab4 && ( (BlockStoneSlab4) block ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( placePosition, new BlockDoubleStoneSlab4().setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            } else {
                this.setTopSlot( clickedPosition.getY() > 0.5 && !world.getBlock( blockPosition ).canBeReplaced( this ) );
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemStoneSlab4 toItem() {
        BlockStoneSlab4 blockStoneSlab = new BlockStoneSlab4();
        blockStoneSlab.setStoneSlabType( this.getStoneSlabType() );
        blockStoneSlab.setTopSlot( false );
        return new ItemStoneSlab4( blockStoneSlab.getRuntimeId() );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.STONE_SLAB4;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    public BlockStoneSlab4 setStoneSlabType( StoneSlab4Type stoneSlabType ) {
        return this.setState( "stone_slab_type_4", stoneSlabType.name().toLowerCase() );
    }

    public StoneSlab4Type getStoneSlabType() {
        return this.stateExists( "stone_slab_type_4" ) ? StoneSlab4Type.valueOf( this.getStringState( "stone_slab_type_4" ) ) : StoneSlab4Type.STONE;
    }

}
