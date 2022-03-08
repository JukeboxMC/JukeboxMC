package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.Inventory;
import org.jukeboxmc.inventory.WindowId;
import org.jukeboxmc.inventory.transaction.InventoryAction;
import org.jukeboxmc.inventory.transaction.action.CreativeInventoryAction;
import org.jukeboxmc.inventory.transaction.action.DropItemAction;
import org.jukeboxmc.inventory.transaction.action.SlotChangeAction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
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
    private boolean hasNetworkIds = false;
    private Transaction[] transactions;
    private int actionType;
    private Vector blockPosition;
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
    public void read( BinaryStream stream ) {
        super.read( stream );
        this.requestId = stream.readSignedVarInt();
        if ( this.requestId != 0 ) {
            int length = stream.readUnsignedVarInt();
            this.slotChange = new SlotChange[length];
            for ( int i = 0; i < length; i++ ) {
                this.slotChange[i] = new SlotChange();
                this.slotChange[i].read( stream );
            }
        }

        this.type = stream.readUnsignedVarInt();

        int actionCount = stream.readUnsignedVarInt();
        this.transactions = new Transaction[actionCount];
        for ( int i = 0; i < actionCount; i++ ) {
            Transaction networkTransaction = new Transaction();
            networkTransaction.read( stream, this.hasNetworkIds );
            this.transactions[i] = networkTransaction;
        }

        switch ( this.type ) {
            case TYPE_USE_ITEM:
                this.actionType = stream.readUnsignedVarInt();
                this.blockPosition = new Vector( stream.readSignedVarInt(), stream.readUnsignedVarInt(), stream.readSignedVarInt() );
                this.blockFace = stream.readBlockFace();
                this.hotbarSlot = stream.readSignedVarInt();
                this.itemInHand = stream.readItem();
                this.playerPosition = new Vector( stream.readLFloat(), stream.readLFloat(), stream.readLFloat() );
                this.clickPosition = new Vector( stream.readLFloat(), stream.readLFloat(), stream.readLFloat() );
                this.blockRuntimeId = stream.readUnsignedVarInt();
                break;
            case TYPE_USE_ITEM_ON_ENTITY:
                this.entityId = stream.readUnsignedVarInt();
                this.actionType = stream.readUnsignedVarInt();
                this.hotbarSlot = stream.readSignedVarInt();
                this.itemInHand = stream.readItem();
                this.firstVector = new Vector( stream.readLFloat(), stream.readLFloat(), stream.readLFloat() );
                this.secondVector = new Vector( stream.readLFloat(), stream.readLFloat(), stream.readLFloat() );
                break;
            case TYPE_RELEASE_ITEM:
                this.actionType = stream.readUnsignedVarInt();
                this.hotbarSlot = stream.readSignedVarInt();
                this.itemInHand = stream.readItem();
                this.playerPosition = new Vector( stream.readLFloat(), stream.readLFloat(), stream.readLFloat() );
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

        public InventoryAction createInventory( Player player ) {
            switch ( this.sourceType ) {
                case SOURCE_CONTAINER:
                    Inventory inventory = player.getInventory( WindowId.getWindowIdById( this.windowId ), this.slot );
                    if ( inventory != null ) {
                        return new SlotChangeAction( inventory, this.slot, this.oldItem, this.newItem );
                    }
                    return null;
                case SOURCE_WORLD:
                    return new DropItemAction( this.oldItem, this.newItem );
                case SOURCE_CREATIVE:
                    return new CreativeInventoryAction( this.oldItem, this.newItem );
                default:
                    break;
            }
            return null;
        }
    }
}
