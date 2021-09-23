package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemExposedCutCopperSlab;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockExposedCutCopperSlab extends BlockSlab {

    public BlockExposedCutCopperSlab() {
        super( "minecraft:exposed_cut_copper_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockExposedCutCopperSlab && ( (BlockExposedCutCopperSlab) targetBlock ).isTopSlot() ) {
                world.setBlock( blockPosition, new BlockExposedDoubleCutCopperSlab() );
                return true;
            } else if ( block instanceof BlockExposedCutCopperSlab ) {
                world.setBlock( placePosition, new BlockExposedDoubleCutCopperSlab() );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockExposedCutCopperSlab && !( (BlockExposedCutCopperSlab) targetBlock ).isTopSlot() ) {
                world.setBlock( blockPosition, new BlockExposedDoubleCutCopperSlab() );
                return true;
            } else if ( block instanceof BlockExposedCutCopperSlab ) {
                world.setBlock( placePosition, new BlockExposedDoubleCutCopperSlab() );
                return true;
            }
        } else {
            if ( block instanceof BlockExposedCutCopperSlab ) {
                world.setBlock( placePosition, new BlockExposedDoubleCutCopperSlab() );
                return true;
            } else {
                this.setTopSlot( clickedPosition.getY() > 0.5 && !world.getBlock( blockPosition ).canBeReplaced( this ) );
            }
        }
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemExposedCutCopperSlab toItem() {
        return new ItemExposedCutCopperSlab();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.EXPOSED_CUT_COPPER_SLAB;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public ItemTierType getTierType() {
        return ItemTierType.STONE;
    }

    @Override
    public double getHardness() {
        return 3;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }
}
