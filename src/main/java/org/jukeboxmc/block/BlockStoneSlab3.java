package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.StoneSlab3Type;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemStoneSlab3;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStoneSlab3 extends BlockSlab {

    public BlockStoneSlab3() {
        super( "minecraft:stone_slab3" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockStoneSlab3 && ( (BlockStoneSlab3) targetBlock ).isTopSlot() && ( (BlockStoneSlab3) targetBlock ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( blockPosition, new BlockDoubleStoneSlab3().setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            } else if ( block instanceof BlockStoneSlab3 && ( (BlockStoneSlab3) block ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( placePosition, new BlockDoubleStoneSlab3().setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockStoneSlab3 && !( (BlockStoneSlab3) targetBlock ).isTopSlot() && ( (BlockStoneSlab3) targetBlock ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( blockPosition, new BlockDoubleStoneSlab3().setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            } else if ( block instanceof BlockStoneSlab3 && ( (BlockStoneSlab3) block ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( placePosition, new BlockDoubleStoneSlab3().setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            }
        } else {
            if ( block instanceof BlockStoneSlab3 && ( (BlockStoneSlab3) block ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( placePosition, new BlockDoubleStoneSlab3().setStoneSlabType( this.getStoneSlabType() ) );
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
    public ItemStoneSlab3 toItem() {
        return new ItemStoneSlab3( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.STONE_SLAB3;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    public BlockStoneSlab3 setStoneSlabType( StoneSlab3Type stoneSlabType ) {
        return this.setState( "stone_slab_type_3", stoneSlabType.name().toLowerCase() );
    }

    public StoneSlab3Type getStoneSlabType() {
        return this.stateExists( "stone_slab_type_3" ) ? StoneSlab3Type.valueOf( this.getStringState( "stone_slab_type_3" ) ) : StoneSlab3Type.END_STONE_BRICK;
    }
}
