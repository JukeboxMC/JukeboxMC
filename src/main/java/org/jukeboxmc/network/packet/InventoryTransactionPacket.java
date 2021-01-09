package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.Protocol;
import org.jukeboxmc.utils.BinaryStream;
/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class InventoryTransactionPacket extends Packet {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_MISMATCH = 1;
    public static final int TYPE_USE_ITEM = 2;
    public static final int TYPE_USE_ITEM_ON_ENTITY = 3;
    public static final int TYPE_RELEASE_ITEM = 4;

    private int requestId;
    private SlotChange[] slotChange;
    private int type;
    private boolean hasNetworkIds;
    private Transaction[] transactions;
    private int actionType;
    private BlockPosition blockPosition;
    private BlockFace blockFace;
    private int hotbarSlot;
    private Item itemInHand;
    private Vector playerPosition;
    private Vector clickPosition;
    private int blockRuntimeId;

    private long entityId;
    private Vector firstVector;
    private Vector secondVector;

    @Override
    public int getPacketId() {
        return Protocol.INVENTORY_TRANSACTION_PACKET;
    }

    @Override
    public void read() {
        super.read();
        this.requestId = this.readSignedVarInt();
        if ( this.requestId != 0 ) {
            int length = this.readUnsignedVarInt();
            this.slotChange = new SlotChange[length];
            for ( int i = 0; i < length; i++ ) {
                this.slotChange[i] = new SlotChange();
                this.slotChange[i].read( this );
            }
        }

        this.type = this.readUnsignedVarInt();
        this.hasNetworkIds = this.readBoolean();

        int actionCount = this.readUnsignedVarInt();
        this.transactions = new Transaction[actionCount];
        for ( int i = 0; i < actionCount; i++ ) {
            Transaction networkTransaction = new Transaction();
            networkTransaction.read( this, this.hasNetworkIds );
            this.transactions[i] = networkTransaction;
        }

        switch ( this.type ) {
            case TYPE_USE_ITEM:
                this.actionType = this.readUnsignedVarInt();
                this.blockPosition = new BlockPosition( this.readSignedVarInt(), this.readUnsignedVarInt(), this.readSignedVarInt() );
                this.blockFace = this.readBlockFace();
                this.hotbarSlot = this.readSignedVarInt();
                this.itemInHand = this.readItem();
                this.playerPosition = new Vector( this.readLFloat(), this.readLFloat(), this.readLFloat() );
                this.clickPosition = new Vector( this.readLFloat(), this.readLFloat(), this.readLFloat() );
                this.blockRuntimeId = this.readUnsignedVarInt();
                break;
            case TYPE_USE_ITEM_ON_ENTITY:
                this.entityId = this.readUnsignedVarInt();
                this.actionType = this.readUnsignedVarInt();
                this.hotbarSlot = this.readSignedVarInt();
                this.itemInHand = this.readItem();
                this.firstVector = new Vector( this.readLFloat(), this.readLFloat(), this.readLFloat() );
                this.secondVector = new Vector( this.readLFloat(), this.readLFloat(), this.readLFloat() );
                break;
            case TYPE_RELEASE_ITEM:
                this.actionType = this.readUnsignedVarInt();
                this.hotbarSlot = this.readSignedVarInt();
                this.itemInHand = this.readItem();
                this.playerPosition = new Vector( this.readLFloat(), this.readLFloat(), this.readLFloat() );
                break;
            default:
                break;
        }
    }

    @Data
    public static class SlotChange {
        private byte containerId;
        private byte[] slotChanged;

        public void read( BinaryStream stream ) {
            this.containerId = stream.readByte();
            int count = stream.readUnsignedVarInt();
            this.slotChanged = new byte[count];
            stream.readBytes( this.slotChanged );
        }
    }

    @Data
    public static class Transaction {
        private static final int SOURCE_CONTAINER = 0;
        private static final int SOURCE_WORLD = 2;
        private static final int SOURCE_CREATIVE = 3;
        private static final int SOURCE_CRAFTING_GRID = 100;
        private static final int SOURCE_WTF_IS_DIS = 99999;

        private int sourceType;
        private int windowId;
        private int unknown;
        private int slot;
        private Item oldItem;
        private Item newItem;

        private int newNetworkId;

        public void read( BinaryStream stream, boolean hasNetworkID ) {
            this.sourceType = stream.readUnsignedVarInt();

            switch ( this.sourceType ) {
                case SOURCE_CONTAINER:
                case SOURCE_WTF_IS_DIS:
                case SOURCE_CRAFTING_GRID:
                    this.windowId = stream.readSignedVarInt();
                    break;
                case SOURCE_WORLD:
                    this.unknown = stream.readUnsignedVarInt();
                    break;
                default:
                    break;
            }

            this.slot = stream.readUnsignedVarInt();
            this.oldItem = stream.readItem();
            this.newItem = stream.readItem();

            if ( hasNetworkID ) {
                this.newNetworkId = stream.readSignedVarInt();
            }
        }
    }
}
