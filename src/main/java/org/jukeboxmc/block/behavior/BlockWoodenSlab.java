package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.WoodType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemWoodenSlab;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWoodenSlab extends BlockSlab {

    public BlockWoodenSlab( Identifier identifier ) {
        super( identifier );
    }

    public BlockWoodenSlab( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock(@NotNull Player player, @NotNull World world, Vector blockPosition, @NotNull Vector placePosition, @NotNull Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockWoodenSlab && ( (BlockWoodenSlab) targetBlock ).isTopSlot() && ( (BlockWoodenSlab) targetBlock ).getWoodType().equals( this.getWoodType() ) ) {
                world.setBlock( blockPosition, Block.<BlockDoubleWoodenSlab>create( BlockType.DOUBLE_WOODEN_SLAB ).setWoodType( this.getWoodType() ) );
                return true;
            } else if ( block instanceof BlockWoodenSlab && ( (BlockWoodenSlab) block ).getWoodType().equals( this.getWoodType() ) ) {
                world.setBlock( placePosition, Block.<BlockDoubleWoodenSlab>create( BlockType.DOUBLE_WOODEN_SLAB ).setWoodType( this.getWoodType() ) );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockWoodenSlab && !( (BlockWoodenSlab) targetBlock ).isTopSlot() && ( (BlockWoodenSlab) targetBlock ).getWoodType().equals( this.getWoodType() ) ) {
                world.setBlock( blockPosition, Block.<BlockDoubleWoodenSlab>create( BlockType.DOUBLE_WOODEN_SLAB ).setWoodType( this.getWoodType() ) );
                return true;
            } else if ( block instanceof BlockWoodenSlab && ( (BlockWoodenSlab) block ).getWoodType().equals( this.getWoodType() ) ) {
                world.setBlock( placePosition, Block.<BlockDoubleWoodenSlab>create( BlockType.DOUBLE_WOODEN_SLAB ).setWoodType( this.getWoodType() ) );
                return true;
            }
        } else {
            if ( block instanceof BlockWoodenSlab && ( (BlockWoodenSlab) block ).getWoodType().equals( this.getWoodType() ) ) {
                world.setBlock( placePosition, Block.<BlockDoubleWoodenSlab>create( BlockType.DOUBLE_WOODEN_SLAB ).setWoodType( this.getWoodType() ) );
                return true;
            } else {
                this.setTopSlot( clickedPosition.getY() > 0.5 && !world.getBlock( blockPosition ).canBeReplaced( this ) );
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public Item toItem() {
        return Item.<ItemWoodenSlab>create( ItemType.WOODEN_SLAB ).setWoodType( this.getWoodType() );
    }

    public BlockWoodenSlab setWoodType(@NotNull WoodType woodType ) {
        return this.setState( "wood_type", woodType.name().toLowerCase() );
    }

    public @NotNull WoodType getWoodType() {
        return this.stateExists( "wood_type" ) ? WoodType.valueOf( this.getStringState( "wood_type" ) ) : WoodType.OAK;
    }
}
