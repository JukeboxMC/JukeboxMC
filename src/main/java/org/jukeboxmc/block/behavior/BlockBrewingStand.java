package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
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
public class BlockBrewingStand extends Block {

    public BlockBrewingStand( Identifier identifier ) {
        super( identifier );
    }

    public BlockBrewingStand( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock(@NotNull Player player, @NotNull World world, Vector blockPosition, @NotNull Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        boolean value = super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace );
        if ( value ) {
            BlockEntity.create( BlockEntityType.BREWING_STAND, this ).spawn();
        }
        return value;
    }

    @Override
    public boolean interact(@NotNull Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        BlockEntityBrewingStand blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            blockEntity.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
            return true;
        }
        return false;
    }

    @Override
    public void onBlockBreak(@NotNull Vector breakPosition ) {
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

    public void setBrewingStandSlotA( boolean value ) {
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
