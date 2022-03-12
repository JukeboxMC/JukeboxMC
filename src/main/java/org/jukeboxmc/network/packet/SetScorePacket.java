package org.jukeboxmc.network.packet;

import lombok.*;
import org.jukeboxmc.utils.BinaryStream;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class SetScorePacket extends Packet {

    private byte type;
    private List<ScoreEntry> entries;

    @Override
    public int getPacketId() {
        return Protocol.SET_SCORE_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );
        stream.writeByte( this.type );

        stream.writeUnsignedVarInt( this.entries.size() );
        for ( ScoreEntry entry : this.entries ) {
            stream.writeSignedVarLong( entry.scoreId );
            stream.writeString( entry.objective );
            stream.writeLInt( entry.score );

            if ( this.type == 0 ) {
                stream.writeByte( entry.entityType );
                switch ( entry.entityType ) {
                    case 3 -> stream.writeString( entry.fakeEntity );
                    case 1, 2 -> stream.writeSignedVarLong( entry.entityId );
                }
            }
        }
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class ScoreEntry {
        private final long scoreId;
        private final String objective;
        private final int score;

        private byte entityType;
        private String fakeEntity;
        private long entityId;
    }
}
