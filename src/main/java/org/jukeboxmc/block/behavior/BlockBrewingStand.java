package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.blockentity.BlockEntityBrewingStand;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.inventory.BrewingStandInventory;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBrewingStand extends Block implements Waterlogable {

    public BlockBrewingStand( Identifier identifier ) {
        super( identifier );
    }

    public BlockBrewingStand( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        if (world.getBlock(placePosition) instanceof BlockWater blockWater && blockWater.getLiquidDepth() == 0) {
            world.setBlock(placePosition, Block.create(BlockType.WATER), 1, false);
        }
        world.setBlock(placePosition, this);
        BlockEntity.create( BlockEntityType.BREWING_STAND, this ).spawn();
        return true;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        BlockEntityBrewingStand blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            blockEntity.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
            return true;
        }
        return false;
    }

    @Override
    public void onBlockBreak( Vector breakPosition ) {
        BlockEntityBrewingStand blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            BrewingStandInventory inventory = blockEntity.getBrewingStandInventory();
            for ( Item content : inventory.getContents() ) {
                if ( content != null && !content.getType().equals( ItemType.AIR ) ){
                    this.location.getWorld().dropItem( content, breakPosition, null ).spawn();
                }
            }
            inventory.clear();
            inventory.getViewer().clear();
        }
        super.onBlockBreak( breakPosition );
    }

    @Override
    public BlockEntityBrewingStand getBlockEntity() {
        return (BlockEntityBrewingStand) this.location.getWorld().getBlockEntity( this.location, this.location.getDimension() );
    }

    @Override
    public int getWaterLoggingLevel() {
        return 1;
    }

    public void setBrewingStandSlotA(boolean value ) {
        this.setState( "brewing_stand_slot_a_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isBrewingStandSlotA() {
        return this.stateExists( "brewing_stand_slot_a_bit" ) && this.getByteState( "brewing_stand_slot_a_bit" ) == 1;
    }

    public void setBrewingStandSlotB( boolean value ) {
        this.setState( "brewing_stand_slot_a_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isBrewingStandSlotB() {
        return this.stateExists( "brewing_stand_slot_b_bit" ) && this.getByteState( "brewing_stand_slot_b_bit" ) == 1;
    }

    public void setBrewingStandSlotC( boolean value ) {
        this.setState( "brewing_stand_slot_a_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isBrewingStandSlotC() {
        return this.stateExists( "brewing_stand_slot_c_bit" ) && this.getByteState( "brewing_stand_slot_c_bit" ) == 1;
    }
}
