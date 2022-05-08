package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemDeepslateBrickSlab;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateBrickSlab extends BlockSlab {

    public BlockDeepslateBrickSlab() {
        super( "minecraft:deepslate_brick_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockDeepslateBrickSlab && ( (BlockDeepslateBrickSlab) targetBlock ).isTopSlot() ) {
                world.setBlock( blockPosition, new BlockDeepslateBrickDoubleSlab() );
                return true;
            } else if ( block instanceof BlockDeepslateBrickSlab ) {
                world.setBlock( placePosition, new BlockDeepslateBrickDoubleSlab() );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockDeepslateBrickSlab && !( (BlockDeepslateBrickSlab) targetBlock ).isTopSlot() ) {
                world.setBlock( blockPosition, new BlockDeepslateBrickDoubleSlab() );
                return true;
            } else if ( block instanceof BlockDeepslateBrickSlab ) {
                world.setBlock( placePosition, new BlockDeepslateBrickDoubleSlab() );
                return true;
            }
        } else {
            if ( block instanceof BlockDeepslateBrickSlab ) {
                world.setBlock( placePosition, new BlockDeepslateBrickDoubleSlab() );
                return true;
            } else {
                this.setTopSlot( clickedPosition.getY() > 0.5 && !world.getBlock( blockPosition ).canBeReplaced( this ) );
            }
        }
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemDeepslateBrickSlab toItem() {
        return new ItemDeepslateBrickSlab();
    }

    @Override
    public BlockType getType() {
        return BlockType.DEEPSLATE_BRICK_SLAB;
    }

    @Override
    public double getHardness() {
        return 3.5;
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
