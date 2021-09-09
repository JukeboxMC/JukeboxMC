package jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.utils.BedrockResourceLoader;
import org.jukeboxmc.utils.BinaryStream;

import java.util.Base64;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class CreativeContentPacket extends Packet {

    @Override
    public int getPacketId() {
        return Protocol.CREATIVE_CONTENT_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);

        stream.writeUnsignedVarInt( BedrockResourceLoader.getCreativeItems().size() );

        int i = 0;
        for ( Map<String, Object> stringObjectMap : BedrockResourceLoader.getCreativeItems() ) {
            int id = BedrockResourceLoader.getItemIdByName().get( (String) stringObjectMap.get( "id" ) );
            int damage = (int) (double) stringObjectMap.getOrDefault( "damage", 0D );
            int blockRuntimeId = (int) (double) stringObjectMap.getOrDefault( "blockRuntimeId", 0D );
            stream.writeUnsignedVarInt( i++ );

            byte[] nbt = stringObjectMap.containsKey( "nbt_b64" ) ? Base64.getMimeDecoder().decode( ( ( (String) stringObjectMap.get( "nbt_b64" ) ) ).getBytes() ) : new byte[0];
            stream.writeItemInstance( id, damage, blockRuntimeId, nbt );
        }
    }
}