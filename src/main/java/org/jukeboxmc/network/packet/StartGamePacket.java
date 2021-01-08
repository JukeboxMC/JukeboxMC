package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.Protocol;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.world.GameRules;

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
    private Location position;
    private String worldId = "";
    private String worldName;
    private Vector worldSpawn;

    private Map<String, GameRules<?>> gamerules = new HashMap<>();

    @Override
    public int getPacketId() {
        return Protocol.START_GAME_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeSignedVarLong( this.entityId );
        this.writeUnsignedVarLong( this.entityRuntimeId );
        this.writeSignedVarInt( this.gameMode.ordinal() );

        //Position
        this.writeLFloat( this.position.getX() );
        this.writeLFloat( this.position.getY() );
        this.writeLFloat( this.position.getZ() );
        this.writeLFloat( this.position.getPitch() ); //Pitch
        this.writeLFloat( this.position.getYaw() ); //Yaw

        this.writeSignedVarInt( 0 ); //Seed

        this.writeLShort( 0x00 ); //Default biome
        this.writeString( "plains" ); //Biome
        this.writeSignedVarInt( 0 ); //Dimension

        this.writeSignedVarInt( 1 ); //Generator
        this.writeSignedVarInt( 0 ); //GameMode
        this.writeSignedVarInt( 0 ); //Difficulty

        //Worldspawn
        this.writeSignedVarInt( (int) this.worldSpawn.getX() );
        this.writeUnsignedVarInt( (int) this.worldSpawn.getY() );
        this.writeSignedVarInt( (int) this.worldSpawn.getZ() );

        this.writeBoolean( true ); //Achievement disabled

        this.writeSignedVarInt( 0 ); //Day cycle time
        this.writeSignedVarInt( 0 ); //Edu odder
        this.writeBoolean( false ); //Edu features
        this.writeString( "" ); //Edu product id

        this.writeLFloat( 0 ); //Rain level
        this.writeLFloat( 0 ); //Lightning level

        this.writeBoolean( false ); //Confirmed platform locked
        this.writeBoolean( true ); //Multi player game
        this.writeBoolean( true ); //Broadcast to lan

        this.writeSignedVarInt( 4 ); //Xbl broadcast mode
        this.writeSignedVarInt( 4 ); //Platform broadcast mode

        this.writeBoolean( true ); //Commands enabed
        this.writeBoolean( false ); //Texture required

        this.writeGameRules( this.gamerules );

        this.writeInt( 0 ); //Experiment count
        this.writeBoolean( false ); //Experiments previously toggled?

        this.writeBoolean( false ); //Bonus chest
        this.writeBoolean( false ); //Start with map

        this.writeSignedVarInt( 1 ); //Player permission

        this.writeInt( 0 ); //Chunk tick range

        this.writeBoolean( false ); //Locked behavior
        this.writeBoolean( false ); //Locked texture
        this.writeBoolean( false ); //From locked template
        this.writeBoolean( false ); //Msa gamer tags only
        this.writeBoolean( false ); //From world template
        this.writeBoolean( false ); //World template option locked
        this.writeBoolean( false ); //Only spawn v1 villagers

        this.writeString( Protocol.MINECRAFT_VERSION );
        this.writeInt( 16 ); //Limited world height
        this.writeInt( 16 ); //Limited world length
        this.writeBoolean( false ); //Has new nether
        this.writeBoolean( false ); //Experimental gameplay

        this.writeString( this.worldId );
        this.writeString( this.worldName );
        this.writeString( "" );  //Template content identity

        this.writeBoolean( false ); //Is trial
        this.writeUnsignedVarInt( 0 ); //Server auth movement

        this.writeLLong( 0 ); //World ticks (for time)
        this.writeSignedVarInt( 0 ); //Enchantment seed

        this.writeUnsignedVarInt( 0 ); //Custom blocks

        List<Map<String, Object>> itemPalette = ItemType.getItemPalette();
        this.writeUnsignedVarInt( itemPalette.size() ); //Item palette
        for( Map<String, Object> item : itemPalette ) {
            this.writeString( (String) item.get( "name" ) );
            this.writeLShort( (int) (double) item.get( "id" ) );
            this.writeBoolean( false );
        }

        this.writeString( "" );
        this.writeBoolean( false ); //New inventory system
    }
}
