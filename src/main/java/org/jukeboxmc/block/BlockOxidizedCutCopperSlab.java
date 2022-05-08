package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemOxidizedCutCopperSlab;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockOxidizedCutCopperSlab extends BlockSlab {

    public BlockOxidizedCutCopperSlab() {
        super( "minecraft:oxidized_cut_copper_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockWeatheredCutCopperSlab && ( (BlockWeatheredCutCopperSlab) targetBlock ).isTopSlot() ) {
                world.setBlock( blockPosition, new BlockOxidizedDoubleCutCopperSlab() );
                return true;
            } else if ( block instanceof BlockWeatheredCutCopperSlab ) {
                world.setBlock( placePosition, new BlockOxidizedDoubleCutCopperSlab() );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockWeatheredCutCopperSlab && !( (BlockWeatheredCutCopperSlab) targetBlock ).isTopSlot() ) {
                world.setBlock( blockPosition, new BlockOxidizedDoubleCutCopperSlab() );
                return true;
            } else if ( block instanceof BlockWeatheredCutCopperSlab ) {
                world.setBlock( placePosition, new BlockOxidizedDoubleCutCopperSlab() );
                return true;
            }
        } else {
            if ( block instanceof BlockWeatheredCutCopperSlab ) {
                world.setBlock( placePosition, new BlockOxidizedDoubleCutCopperSlab() );
                return true;
            } else {
                this.setTopSlot( clickedPosition.getY() > 0.5 && !world.getBlock( blockPosition ).canBeReplaced( this ) );
            }
        }
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemOxidizedCutCopperSlab toItem() {
        return new ItemOxidizedCutCopperSlab();
    }

    @Override
    public BlockType getType() {
        return BlockType.OXIDIZED_CUT_COPPER_SLAB;
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
