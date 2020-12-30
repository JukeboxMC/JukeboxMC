package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.Protocol;
import org.jukeboxmc.player.info.DeviceInfo;
import org.jukeboxmc.player.skin.Skin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerListPacket extends Packet {

    private Type type;
    private List<Entry> entries = new ArrayList<>();

    @Override
    public int getPacketId() {
        return Protocol.PLAYER_LIST_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeByte( this.type.ordinal() );
        this.writeUnsignedVarInt( this.entries.size() );

        if ( this.type == Type.ADD ) {
            for ( Entry entry : this.entries ) {
                this.writeUUID( entry.getUuid() );
                this.writeSignedVarLong( entry.getEntityId() );
                this.writeString( entry.getName() );

                this.writeString( entry.getXboxId() );
                this.writeString( entry.getDeviceInfo().getDeviceId() );
                this.writeLInt( entry.getDeviceInfo().getDevice().getId() );

                this.writeSkin( entry.getSkin() );

                this.writeBoolean( false );
                this.writeBoolean( false );
            }
        } else {
            for ( Entry entry : this.entries ) {
                this.writeUUID( entry.getUuid() );
            }
        }
    }

    public enum Type {
        ADD,
        REMOVE
    }

    @Data
    public static class Entry {
        private UUID uuid;
        private long entityId = 0;
        private String name;
        private DeviceInfo deviceInfo;
        private String xboxId;
        private Skin skin;

        public Entry( UUID uuid ) {
            this.uuid = uuid;
        }

        public Entry( UUID uuid, long entityId, String name, DeviceInfo deviceInfo, String xboxId, Skin skin ) {
            this.uuid = uuid;
            this.entityId = entityId;
            this.name = name;
            this.deviceInfo = deviceInfo;
            this.xboxId = xboxId;
            this.skin = skin;
        }
    }
}
