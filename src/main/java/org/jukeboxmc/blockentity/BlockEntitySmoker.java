package org.jukeboxmc.blockentity;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.inventory.SmokerInventory;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntitySmoker extends BlockEntity implements InventoryHolder {

    private final @NotNull SmokerInventory smokerInventory;

    public BlockEntitySmoker(@NotNull Block block, BlockEntityType blockEntityType ) {
        super( block, blockEntityType );
        this.smokerInventory = new SmokerInventory( this );
    }

    @Override
    public boolean interact(@NotNull Player player, @NotNull Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( this.smokerInventory, blockPosition );
        return true;
    }

    public @NotNull SmokerInventory getSmokerInventory() {
        return this.smokerInventory;
    }
}
