package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.Protocol;

/**
 * @author Kaooot
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class NpcDialoguePacket extends Packet {

    private long entityId;
    private Action action;
    private String dialogue;
    private String sceneName;
    private String npcName;
    private String actionJson;

    @Override
    public int getPacketId() {
        return Protocol.NPC_DIALOGUE_PACKET;
    }

    @Override
    public void read() {
        this.entityId = this.readUnsignedVarLong();
        this.action = Action.values()[this.readInt()];
        this.dialogue = this.readString();
        this.sceneName = this.readString();
        this.npcName = this.readString();
        this.actionJson = this.readString();
    }

    @Override
    public void write() {
        this.writeUnsignedVarLong( this.entityId );
        this.writeInt( this.action.ordinal() );
        this.writeString( this.dialogue );
        this.writeString( this.sceneName );
        this.writeString( this.npcName );
        this.writeString( this.actionJson );
    }

    public enum Action {
        OPEN,
        CLOSE
    }
}