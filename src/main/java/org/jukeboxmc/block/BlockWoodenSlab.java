package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.WoodType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWoodenSlab;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWoodenSlab extends BlockSlab {

    public BlockWoodenSlab() {
        super( "minecraft:wooden_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockWoodenSlab ) {
                BlockWoodenSlab blockSlab = (BlockWoodenSlab) targetBlock;
                if ( blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, new BlockDoubleWoodenSlab().setWoodType( this.getWoodType() ) );
                    return true;
                }
            } else if ( block instanceof BlockWoodenSlab ) {
                world.setBlock( placePosition, new BlockDoubleWoodenSlab().setWoodType( this.getWoodType() ) );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockWoodenSlab ) {
                BlockWoodenSlab blockSlab = (BlockWoodenSlab) targetBlock;
                if ( !blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, new BlockDoubleWoodenSlab().setWoodType( this.getWoodType() ) );
                    return true;
                }
            } else if ( block instanceof BlockWoodenSlab ) {
                world.setBlock( placePosition, new BlockDoubleWoodenSlab().setWoodType( this.getWoodType() ) );
                return true;
            }
        } else {
            if ( block instanceof BlockWoodenSlab ) {
                world.setBlock( placePosition, new BlockDoubleWoodenSlab().setWoodType( this.getWoodType() ) );
                return true;
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        world.setBlock( placePosition, block, 1 );
        return true;
    }

    @Override
    public ItemWoodenSlab toItem() {
        return new ItemWoodenSlab(this.runtimeId);
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WOODEN_SLAB;
    }

    public BlockWoodenSlab setWoodType( WoodType woodType ) {
       return this.setState( "wood_type", woodType.name().toLowerCase() );
    }

    public WoodType getWoodType() {
        return this.stateExists( "wood_type" ) ? WoodType.valueOf( this.getStringState( "wood_type" ) ) : WoodType.OAK;
    }

}
