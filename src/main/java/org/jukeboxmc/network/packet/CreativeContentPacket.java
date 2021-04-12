package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemStone;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.network.Protocol;

import java.util.Base64;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CreativeContentPacket extends Packet {

    @Override
    public int getPacketId() {
        return Protocol.CREATIVE_CONTENT_PACKET;
    }

    @Override
    public void write() {
        super.write();

        this.writeUnsignedVarInt(ItemType.getCreativeItems().size());

        for (Map<String, Object> stringObjectMap : ItemType.getCreativeItems()) {
            int id = ItemType.itemIdByName((String) stringObjectMap.get("id"));
            int data = (int) (double) stringObjectMap.getOrDefault("damage", 0D);
            int blockRuntimeId = (int) (double) stringObjectMap.getOrDefault("blockRuntimeId", 0D);

            this.writeUnsignedVarInt(0); // NetID
            this.writeItemInstance(id, data, 1, stringObjectMap.containsKey("nbt_b64") ? Base64.getDecoder().decode(((String) stringObjectMap.get("nbt_b64")).getBytes()) : new byte[0], blockRuntimeId);
        }
    }
}