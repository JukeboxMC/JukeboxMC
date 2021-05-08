package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.WoodType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
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
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        this.setWoodType( WoodType.values()[itemIndHand.getMeta()] );

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
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getWoodType().ordinal() );
    }

    public void setWoodType( WoodType woodType ) {
        this.setState( "wood_type", woodType.name().toLowerCase() );
    }

    public WoodType getWoodType() {
        return this.stateExists( "wood_type" ) ? WoodType.valueOf( this.getStringState( "wood_type" ).toUpperCase() ) : WoodType.OAK;
    }

}
