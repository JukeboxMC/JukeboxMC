package org.jukeboxmc.inventory;

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SmithingTableInventory extends ContainerInventory {

    public SmithingTableInventory(InventoryHolder holder) {
        super(holder, -1, 3);
    }

    @Override
    public Player getInventoryHolder() {
        return (Player) this.holder;
    }

    @Override
    public InventoryType getType() {
        return InventoryType.SMITHING_TABLE;
    }

    @Override
    public ContainerType getWindowTypeId() {
        return ContainerType.SMITHING_TABLE;
    }

    @Override
    public void setItem(int slot, Item item, boolean sendContent) {
        super.setItem(slot - 51, item, sendContent);
    }

    @Override
    public Item getItem(int slot) {
        return super.getItem(slot - 51);
    }
}
