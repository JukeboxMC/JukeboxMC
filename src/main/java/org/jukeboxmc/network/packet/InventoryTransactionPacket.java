package org.jukeboxmc.network.packet;

import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class InventoryTransactionPacket extends Packet { //TODO

    @Override
    public int getPacketId() {
        return Protocol.INVENTORY_TRANSACTION_PACKET;
    }

    @Override
    public void read() {
        super.read();
    }
}
