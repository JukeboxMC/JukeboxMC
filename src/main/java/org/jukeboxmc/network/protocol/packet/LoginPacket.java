package org.jukeboxmc.network.protocol.packet;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import org.jukeboxmc.network.raknet.utils.BinaryStream;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class LoginPacket extends Packet {

    private int protocol;

    @Override
    public int getPacketId() {
        return 0x01;
    }

    @Override
    public void read() {
        this.protocol = this.readInt();

        this.setBuffer( this.readBytes( new BinaryStream( this.getBuffer() ).readUnsignedVarInt() ) );

        String chainToken = this.readString();

        Gson gson = new Gson();
        Map map = gson.fromJson( chainToken, Map.class );
        List<String> chains = ( (List<?>) map.get( "chain" ) ).stream().map( Object::toString ).collect( Collectors.toList() );
        for ( String token : chains ) {
            //System.out.println( this.readToken( token ) );
        }

        String skinToken = this.readString();

       // System.out.println( this.readToken( skinToken ) );
    }

    private String readToken( String token ) {
        String[] base = token.split( "\\." );
        if ( base.length < 2 )
            return null;
        return new String( Base64.getDecoder().decode( base[1] ) );
    }

}
