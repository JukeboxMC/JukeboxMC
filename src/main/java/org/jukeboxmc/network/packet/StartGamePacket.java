package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.utils.BedrockResourceLoader;
import org.jukeboxmc.utils.BinaryStream;
import org.jukeboxmc.world.Difficulty;
import org.jukeboxmc.world.GameRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class StartGamePacket extends Packet {

    private long entityId;
    private long entityRuntimeId;
    private GameMode gameMode;
    private Vector position;
    private float yaw;
    private float pitch;
    private String worldId = "";
    private String worldName;
    private Vector worldSpawn;
    private String serverEngine;
    private Difficulty difficulty;

    private Map<GameRule<?>, Object> gamerules = new HashMap<>();

    @Override
    public int getPacketId() {
        return Protocol.START_GAME_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeSignedVarLong( this.entityId );
        stream.writeUnsignedVarLong( this.entityRuntimeId );
        stream.writeSignedVarInt( this.gameMode.ordinal() );

        //Position
        stream.writeLFloat( this.position.getX() );
        stream.writeLFloat( this.position.getY() );
        stream.writeLFloat( this.position.getZ() );
        stream.writeLFloat( this.pitch );
        stream.writeLFloat( this.yaw );

        stream.writeSignedVarInt( 0 ); //Seed

        stream.writeLShort( 0x00 ); //Default biome
        stream.writeString( "plains" ); //Biome
        stream.writeSignedVarInt( 0 ); //Dimension

        stream.writeSignedVarInt( 1 ); //Generator
        stream.writeSignedVarInt( 0 ); //GameMode
        stream.writeSignedVarInt( this.difficulty.ordinal() ); //Difficulty

        //Worldspawn
        stream.writeSignedVarInt( (int) this.worldSpawn.getX() );
        stream.writeUnsignedVarInt( (int) this.worldSpawn.getY() );
        stream.writeSignedVarInt( (int) this.worldSpawn.getZ() );

        stream.writeBoolean( true ); //Achievement disabled

        stream.writeSignedVarInt( 0 ); //Day cycle time
        stream.writeSignedVarInt( 0 ); //Edu odder
        stream.writeBoolean( false ); //Edu features
        stream.writeString( "" ); //Edu product id

        stream.writeLFloat( 0 ); //Rain level
        stream.writeLFloat( 0 ); //Lightning level

        stream.writeBoolean( false ); //Confirmed platform locked
        stream.writeBoolean( true ); //Multi player game
        stream.writeBoolean( true ); //Broadcast to lan

        stream.writeSignedVarInt( 4 ); //Xbl broadcast mode
        stream.writeSignedVarInt( 4 ); //Platform broadcast mode

        stream.writeBoolean( true ); //Commands enabed
        stream.writeBoolean( false ); //Texture required

        stream.writeGameRules( this.gamerules );

        stream.writeInt( 0 ); //Experiment count
        stream.writeBoolean( false ); //Experiments previously toggled?

        stream.writeBoolean( false ); //Bonus chest
        stream.writeBoolean( false ); //Start with map

        stream.writeSignedVarInt( 1 ); //Player permission

        stream.writeInt( 0 ); //Chunk tick range

        stream.writeBoolean( false ); //Locked behavior
        stream.writeBoolean( false ); //Locked texture
        stream.writeBoolean( false ); //From locked template
        stream.writeBoolean( false ); //Msa gamer tags only
        stream.writeBoolean( false ); //From world template
        stream.writeBoolean( false ); //World template option locked
        stream.writeBoolean( false ); //Only spawn v1 villagers

        stream.writeString( Protocol.MINECRAFT_VERSION );
        stream.writeInt( 16 ); //Limited world height
        stream.writeInt( 16 ); //Limited world length
        stream.writeBoolean( false ); //Has new nether
        stream.writeBoolean( false ); //Experimental gameplay

        stream.writeString( this.worldId );
        stream.writeString( this.worldName );
        stream.writeString( "" );  //Template content identity

        stream.writeBoolean( false ); //Is trial
        stream.writeUnsignedVarInt( 0 ); //Server auth movement
        stream.writeSignedVarInt( 0 ); //RewindHistorySize
        stream.writeBoolean( false ); // isServerAuthoritativeBlockBreaking

        stream.writeLLong( 0 ); //World ticks (for time)
        stream.writeSignedVarInt( 0 ); //Enchantment seed

        stream.writeUnsignedVarInt( 0 ); //Custom blocks

        List<Map<String, Object>> itemPalette = BedrockResourceLoader.getItemPalettes();
        stream.writeUnsignedVarInt( itemPalette.size() ); //Item palette
        for( Map<String, Object> item : itemPalette ) {
            stream.writeString( (String) item.get( "name" ) );
            stream.writeLShort( (int) (double) item.get( "id" ) );
            stream.writeBoolean( false );
        }

        stream.writeString( "" );
        stream.writeBoolean( false ); //New inventory system
        stream.writeString( this.serverEngine );
    }
}
