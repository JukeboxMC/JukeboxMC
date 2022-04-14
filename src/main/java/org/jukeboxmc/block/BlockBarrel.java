package org.jukeboxmc.block;

import org.apache.commons.math3.util.FastMath;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntityBarrel;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.inventory.BarrelInventory;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemBarrel;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBarrel extends Block {

    public BlockBarrel() {
        super( "minecraft:barrel" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        if ( FastMath.abs( player.getX() - this.getLocation().getX() ) < 2 && FastMath.abs( player.getZ() - this.getLocation().getZ() ) < 2 ) {
            double y = player.getY() + player.getEyeHeight();

            if ( y - this.getLocation().getY() > 2 ) {
                this.setBlockFace( BlockFace.UP );
            } else if ( this.getLocation().getY() - y > 0 ) {
                this.setBlockFace( BlockFace.DOWN );
            } else {
                this.setBlockFace( player.getDirection().toBlockFace().opposite() );
            }
        } else {
            this.setBlockFace( player.getDirection().toBlockFace().opposite() );
        }
        this.setOpen( false );
        world.setBlock( placePosition, this );
        BlockEntityType.BARREL.<BlockEntityBarrel>createBlockEntity( this ).spawn();
        return true;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        BlockEntityBarrel blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            blockEntity.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
            return true;
        }
        return false;
    }

    @Override
    public boolean onBlockBreak( Vector breakPosition ) {
        BlockEntityBarrel blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            BarrelInventory inventory = blockEntity.getBarrelInventory();
            for ( Item content : inventory.getContents() ) {
                if ( content != null && !content.getItemType().equals( ItemType.AIR ) ){
                    this.location.getWorld().dropItem( content, breakPosition, null ).spawn();
                }
            }
            inventory.clear();
            inventory.getViewer().clear();
        }
        return super.onBlockBreak( breakPosition );
    }

    @Override
    public ItemBarrel toItem() {
        return new ItemBarrel();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BARREL;
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntityBarrel getBlockEntity() {
        return (BlockEntityBarrel) this.world.getBlockEntity( this.location, this.location.getDimension() );
    }

    @Override
    public double getHardness() {
        return 2.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }

    public void setOpen( boolean value ) {
        this.setState( "open_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isOpen() {
        return this.stateExists( "open_bit" ) && this.getByteState( "open_bit" ) == 1;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
