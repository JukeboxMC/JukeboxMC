package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemCutCopperSlab;
import org.jukeboxmc.item.ItemTierType;
import org.jukeboxmc.item.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCutCopperSlab extends BlockSlab {

    public BlockCutCopperSlab() {
        super( "minecraft:cut_copper_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockCutCopperSlab && ( (BlockCutCopperSlab) targetBlock ).isTopSlot() ) {
                world.setBlock( blockPosition, new BlockDoubleCutCopperSlab() );
                return true;
            } else if ( block instanceof BlockCutCopperSlab ) {
                world.setBlock( placePosition, new BlockDoubleCutCopperSlab() );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockCutCopperSlab && !( (BlockCutCopperSlab) targetBlock ).isTopSlot() ) {
                world.setBlock( blockPosition, new BlockDoubleCutCopperSlab() );
                return true;
            } else if ( block instanceof BlockCutCopperSlab ) {
                world.setBlock( placePosition, new BlockDoubleCutCopperSlab() );
                return true;
            }
        } else {
            if ( block instanceof BlockCutCopperSlab ) {
                world.setBlock( placePosition, new BlockDoubleCutCopperSlab() );
                return true;
            } else {
                this.setTopSlot( clickedPosition.getY() > 0.5 && !world.getBlock( blockPosition ).canBeReplaced( this ) );
            }
        }
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemCutCopperSlab toItem() {
        return new ItemCutCopperSlab();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CUT_COPPER_SLAB;
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
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }
}
