package org.jukeboxmc.item.behavior;

import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAir extends Item {

    public ItemAir( ItemType itemType ) {
        super( itemType );
        this.amount = 0;
        this.stackNetworkId = 0;
    }

    @Override
    public ItemData toItemData() {
        return ItemData.AIR;
    }
}
