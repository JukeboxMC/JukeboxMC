package jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.utils.BinaryStream;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class EmoteListPacket extends Packet {

    private long entityRuntimeId;
    private List<UUID> emotes = new ArrayList<>();

    @Override
    public int getPacketId() {
        return Protocol.EMOTE_LIST_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read(stream);
        this.entityRuntimeId = stream.readUnsignedVarLong();
        int count = stream.readUnsignedVarInt();
        for ( int i = 0; i < count; i++ ) {
            this.emotes.add( stream.readUUID() );
        }
    }
}
