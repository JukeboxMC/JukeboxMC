package org.jukeboxmc.blockentity;

import org.cloudburstmc.protocol.bedrock.packet.ContainerSetDataPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.crafting.recipes.SmeltingRecipe;
import org.jukeboxmc.inventory.FurnaceInventory;
import org.jukeboxmc.inventory.Inventory;
import org.jukeboxmc.inventory.WindowId;
import org.jukeboxmc.item.Burnable;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SmeltingComponent extends BlockEntity {

    private static final int CONTAINER_PROPERTY_TICK_COUNT = 0;
    private static final int CONTAINER_PROPERTY_LIT_TIME = 1;
    private static final int CONTAINER_PROPERTY_LIT_DURATION = 2;

    private short cookTime;
    private short burnTime;
    private short burnDuration;

    private Item output;

    private Inventory inventory;

    public SmeltingComponent( Block block, BlockEntityType blockEntityType ) {
        super( block, blockEntityType );
    }

    public void initInventory( Inventory inventory ) {
        this.inventory = inventory;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        this.sendDataProperties( player );
        return true;
    }

    @Override
    public void update( long currentTick ) {
        Item input = this.inventory.getItem( 0 );
        Item fuelItem = this.inventory.getItem( 1 );
        Item outputItem = this.inventory.getItem( 2 );

        if ( input != null && !( input.getType().equals( ItemType.AIR ) ) && fuelItem != null && !fuelItem.getType().equals( ItemType.AIR ) && outputItem.getAmount() < 64 ) {
            this.checkForRecipe( input );
        }
        if ( this.output != null && !this.output.getType().equals( ItemType.AIR ) && !input.getType().equals( ItemType.AIR ) && outputItem.getAmount() < 64 && this.burnTime > 0 ) {
            this.cookTime++;
            if ( this.cookTime >= (this.inventory instanceof FurnaceInventory ? 200 : 100) ) {
                Item itemStack = this.inventory.getItem( 2 );
                if ( itemStack.getType() != this.output.getType() ) {
                    this.inventory.setItem( 2, this.output.setAmount( 1 ) );
                } else {
                    itemStack.setAmount( itemStack.getAmount() + 1 );
                    this.inventory.setItem( 2, itemStack );
                }

                this.inventory.setItem( 0, input.decreaseAmount() );
                this.cookTime = 0;
                this.broadcastCookTime();
            } else if ( this.cookTime % 20 == 0 ) {
                this.broadcastCookTime();
            }
        }

        if ( this.burnDuration > 0 ) {
            this.burnTime--;

            boolean didRefuel = false;
            if ( this.burnTime == 0 ) {
                this.burnDuration = 0;
                if ( this.checkForRefuel() ) {
                    didRefuel = true;
                    this.broadcastFuelInfo();
                }
            }

            if ( !didRefuel && ( this.burnTime == 0 || this.burnTime % 20 == 0 ) ) {
                this.broadcastFuelInfo();
            }
        } else {
            if ( this.checkForRefuel() ) {
                this.broadcastFuelInfo();
            }
        }
    }

    private void checkForRecipe( Item input ) {
        this.output = null;
    }

    private boolean checkForRefuel() {
        if ( this.canProduceOutput() ) {
            Item fuelItem = this.inventory.getItem( 1 );
            if ( fuelItem instanceof Burnable burnableItem) {
                Duration duration = burnableItem.getBurnTime();
                if ( duration != null ) {
                    if ( fuelItem.getAmount() > 0 ) {
                        this.inventory.setItem( 1, fuelItem.decreaseAmount() );

                        int diff = this.inventory instanceof FurnaceInventory ? 1 : 2;
                        this.burnDuration = (short) Math.ceil( ( (double) duration.toMillis() ) / (double) diff );
                        this.burnTime = this.burnDuration;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean canProduceOutput() {
        if ( this.output == null ) {
            return false;
        }

        Item input = this.inventory.getItem( 0 );
        if ( input.getType() == ItemType.AIR || input.getAmount() == 0 ) {
            return false;
        }

        Item itemStack = this.inventory.getItem( 2 );
        if ( itemStack.getType() == this.output.getType() ) {
            return itemStack.getAmount() <= itemStack.getMaxStackSize();
        }
        return true;
    }

    private void sendTickProgress( Player player ) {
        ContainerSetDataPacket containerData = new ContainerSetDataPacket();
        containerData.setWindowId( (byte) WindowId.OPEN_CONTAINER.getId() );
        containerData.setProperty( CONTAINER_PROPERTY_TICK_COUNT );
        containerData.setValue( this.cookTime );
        player.getPlayerConnection().sendPacket( containerData );
    }

    private void sendFuelInfo( Player player ) {
        ContainerSetDataPacket containerData = new ContainerSetDataPacket();
        containerData.setWindowId( (byte) WindowId.OPEN_CONTAINER.getId() );
        containerData.setProperty( CONTAINER_PROPERTY_LIT_TIME );
        containerData.setValue( this.burnTime );
        player.getPlayerConnection().sendPacket( containerData );

        containerData = new ContainerSetDataPacket();
        containerData.setWindowId( (byte) WindowId.OPEN_CONTAINER.getId() );
        containerData.setProperty( CONTAINER_PROPERTY_LIT_DURATION );
        containerData.setValue( this.burnDuration );
        player.getPlayerConnection().sendPacket( containerData );
    }

    private void sendDataProperties( Player player ) {
        ContainerSetDataPacket containerData = new ContainerSetDataPacket();
        containerData.setWindowId( (byte) WindowId.OPEN_CONTAINER.getId() );
        containerData.setProperty( CONTAINER_PROPERTY_TICK_COUNT );
        containerData.setValue( this.cookTime );
        player.getPlayerConnection().sendPacket( containerData );

        containerData = new ContainerSetDataPacket();
        containerData.setWindowId( (byte) WindowId.OPEN_CONTAINER.getId() );
        containerData.setProperty( CONTAINER_PROPERTY_LIT_TIME );
        containerData.setValue( this.burnTime );
        player.getPlayerConnection().sendPacket( containerData );

        containerData = new ContainerSetDataPacket();
        containerData.setWindowId( (byte) WindowId.OPEN_CONTAINER.getId() );
        containerData.setProperty( CONTAINER_PROPERTY_LIT_DURATION );
        containerData.setValue( this.burnDuration );
        player.getPlayerConnection().sendPacket( containerData );
    }

    private void broadcastCookTime() {
        for ( Player viewer : this.inventory.getViewer() ) {
            this.sendTickProgress( viewer );
        }
    }

    private void broadcastFuelInfo() {
        for ( Player viewer : this.inventory.getViewer() ) {
            this.sendFuelInfo( viewer );
        }
    }
}
