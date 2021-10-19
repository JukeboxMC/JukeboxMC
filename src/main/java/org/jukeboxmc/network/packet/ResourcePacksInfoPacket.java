package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.Server;
import org.jukeboxmc.resourcepack.ResourcePack;
import org.jukeboxmc.utils.BinaryStream;

import java.util.Collection;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class ResourcePacksInfoPacket extends Packet {

    private boolean forceAccept;
    private boolean scripting;
    private boolean forceServerPacks;

    @Override
    public int getPacketId() {
        return Protocol.RESOURCE_PACKS_INFO_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);

        stream.writeBoolean( this.forceAccept );
        stream.writeBoolean( this.scripting );
        stream.writeBoolean( this.forceServerPacks );
        stream.writeLShort( 0 ); // behaviour packs amount

        Collection<ResourcePack> resourcePacks = Server.getInstance().getResourcePackManager().retrieveResourcePacks();
        stream.writeLShort( resourcePacks.size() );
        for ( ResourcePack resourcePack : resourcePacks ) {
            stream.writeString( resourcePack.getUuid() );
            stream.writeString( resourcePack.getVersion() );
            stream.writeLLong( resourcePack.getSize() );
            stream.writeString( "" ); // encryption key
            stream.writeString( "" ); // sub name
            stream.writeString( "" ); // content identity
            stream.writeBoolean( false ); // scripting
            stream.writeBoolean( false ); // is raytracing capable
        }
    }
}
