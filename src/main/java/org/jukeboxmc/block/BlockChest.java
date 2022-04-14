package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.blockentity.BlockEntityChest;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.inventory.ChestInventory;
import org.jukeboxmc.inventory.ContainerInventory;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemChest;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockChest extends BlockWaterlogable {

    public BlockChest() {
        super( "minecraft:chest" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setBlockFace( player.getDirection().toBlockFace().opposite() );
        boolean value = super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        if ( value ) {
            BlockEntityType.CHEST.<BlockEntityChest>createBlockEntity( this ).spawn();

            for ( Direction direction : Direction.values() ) {
                Block side = this.getSide( direction );
                if ( side.getBlockType().equals( this.getBlockType() )) {
                    BlockEntityChest blockEntity = this.getBlockEntity();
                    BlockEntityChest sideBlockEntity = (BlockEntityChest) side.getBlockEntity();
                    if ( !blockEntity.isPaired() && !sideBlockEntity.isPaired()) {
                        blockEntity.pair( sideBlockEntity );
                        blockEntity.update( player );
                        sideBlockEntity.update( player );
                    }

                }
            }
        }
        return value;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        BlockEntityChest blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            blockEntity.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
            return true;
        }
        return false;
    }

    @Override
    public boolean onBlockBreak( Vector breakPosition ) {
        BlockEntityChest blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            if ( blockEntity.isPaired() ) {
                blockEntity.unpair();
            }
            ChestInventory chestInventory = blockEntity.getChestInventory();
            for ( Item content : chestInventory.getContents() ) {
                if ( content != null && !content.getItemType().equals( ItemType.AIR ) ){
                    this.location.getWorld().dropItem( content, breakPosition, null ).spawn();
                }
            }
            chestInventory.clear();
            chestInventory.getViewer().clear();
        }
        return super.onBlockBreak( breakPosition );
    }

    @Override
    public ItemChest toItem() {
        return new ItemChest();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CHEST;
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntityChest getBlockEntity() {
        return (BlockEntityChest) this.world.getBlockEntity( this.location, this.location.getDimension() );
    }

    @Override
    public double getHardness() {
        return 2.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
