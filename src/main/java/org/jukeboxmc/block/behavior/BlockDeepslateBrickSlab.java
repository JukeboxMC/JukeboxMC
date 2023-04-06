package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateBrickSlab extends BlockSlab {

    public BlockDeepslateBrickSlab( Identifier identifier ) {
        super( identifier );
    }

    public BlockDeepslateBrickSlab( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockDeepslateBrickSlab blockSlab ) {
                if ( blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, Block.create( BlockType.DEEPSLATE_BRICK_DOUBLE_SLAB ) );
                    return true;
                }
            } else if ( block instanceof BlockDeepslateBrickSlab ) {
                world.setBlock( placePosition, Block.create( BlockType.DEEPSLATE_BRICK_DOUBLE_SLAB ) );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockDeepslateBrickSlab blockSlab ) {
                if ( !blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, Block.create( BlockType.DEEPSLATE_BRICK_DOUBLE_SLAB ) );
                    return true;
                }
            } else if ( block instanceof BlockDeepslateBrickSlab ) {
                world.setBlock( placePosition, Block.create( BlockType.DEEPSLATE_BRICK_DOUBLE_SLAB ) );
                return true;
            }
        } else {
            if ( block instanceof BlockDeepslateBrickSlab ) {
                world.setBlock( placePosition, Block.create( BlockType.DEEPSLATE_BRICK_DOUBLE_SLAB ) );
                return true;
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace );
        world.setBlock( placePosition, this );
        return true;
    }
}
