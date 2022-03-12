package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class ModalResponsePacket extends Packet {

    private int formId;
    private String json;

    @Override
    public int getPacketId() {
        return Protocol.MODAL_RESPONSE_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read( stream );
        this.formId = stream.readSignedVarInt();
        this.json = stream.readString();
    }
}
