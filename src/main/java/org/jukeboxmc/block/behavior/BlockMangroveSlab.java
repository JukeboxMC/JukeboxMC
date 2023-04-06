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
public class BlockMangroveSlab extends BlockSlab {

    public BlockMangroveSlab( Identifier identifier ) {
        super( identifier );
    }

    public BlockMangroveSlab( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockMangroveSlab blockSlab ) {
                if ( blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, Block.create( BlockType.MANGROVE_DOUBLE_SLAB ) );
                    return true;
                }
            } else if ( block instanceof BlockMangroveSlab ) {
                world.setBlock( placePosition, Block.create( BlockType.MANGROVE_DOUBLE_SLAB ) );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockMangroveSlab blockSlab ) {
                if ( !blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, Block.create( BlockType.MANGROVE_DOUBLE_SLAB ) );
                    return true;
                }
            } else if ( block instanceof BlockMangroveSlab ) {
                world.setBlock( placePosition, Block.create( BlockType.MANGROVE_DOUBLE_SLAB ) );
                return true;
            }
        } else {
            if ( block instanceof BlockMangroveSlab ) {
                world.setBlock( placePosition, Block.create( BlockType.MANGROVE_DOUBLE_SLAB ) );
                return true;
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace );
        world.setBlock( placePosition, this );
        return true;
    }
}
