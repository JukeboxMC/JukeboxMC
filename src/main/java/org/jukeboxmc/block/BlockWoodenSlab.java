package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.WoodType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWoodenSlab;
import org.jukeboxmc.item.type.ItemToolType;
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
            if ( targetBlock instanceof BlockWoodenSlab && ( (BlockWoodenSlab) targetBlock ).isTopSlot() && ( (BlockWoodenSlab) targetBlock ).getWoodType().equals( this.getWoodType() ) ) {
                world.setBlock( blockPosition, new BlockDoubleWoodenSlab().setWoodType( this.getWoodType() ) );
                return true;
            } else if ( block instanceof BlockWoodenSlab && ( (BlockWoodenSlab) block ).getWoodType().equals( this.getWoodType() ) ) {
                world.setBlock( placePosition, new BlockDoubleWoodenSlab().setWoodType( this.getWoodType() ) );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockWoodenSlab && !( (BlockWoodenSlab) targetBlock ).isTopSlot() && ( (BlockWoodenSlab) targetBlock ).getWoodType().equals( this.getWoodType() ) ) {
                world.setBlock( blockPosition, new BlockDoubleWoodenSlab().setWoodType( this.getWoodType() ) );
                return true;
            } else if ( block instanceof BlockWoodenSlab && ( (BlockWoodenSlab) block ).getWoodType().equals( this.getWoodType() ) ) {
                world.setBlock( placePosition, new BlockDoubleWoodenSlab().setWoodType( this.getWoodType() ) );
                return true;
            }
        } else {
            if ( block instanceof BlockWoodenSlab && ( (BlockWoodenSlab) block ).getWoodType().equals( this.getWoodType() ) ) {
                world.setBlock( placePosition, new BlockDoubleWoodenSlab().setWoodType( this.getWoodType() ) );
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
    public ItemWoodenSlab toItem() {
        BlockWoodenSlab blockWoodenSlab = new BlockWoodenSlab();
        blockWoodenSlab.setWoodType( this.getWoodType() );
        blockWoodenSlab.setTopSlot( false );
        return new ItemWoodenSlab( blockWoodenSlab.getRuntimeId() );
    }

    @Override
    public BlockType getType() {
        return BlockType.WOODEN_SLAB;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }

    public BlockWoodenSlab setWoodType( WoodType woodType ) {
       return this.setState( "wood_type", woodType.name().toLowerCase() );
    }

    public WoodType getWoodType() {
        return this.stateExists( "wood_type" ) ? WoodType.valueOf( this.getStringState( "wood_type" ) ) : WoodType.OAK;
    }

}
