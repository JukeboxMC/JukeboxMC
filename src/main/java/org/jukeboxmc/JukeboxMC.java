package org.jukeboxmc;

import io.netty.util.ResourceLeakDetector;
import lombok.Getter;
import org.jukeboxmc.block.BlockBedrock;
import org.jukeboxmc.block.BlockDirt;
import org.jukeboxmc.block.BlockPalette;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.console.TerminalConsole;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.network.packet.CreativeContentPacket;
import org.jukeboxmc.network.raknet.utils.Zlib;
import org.jukeboxmc.utils.BinaryStream;
import org.jukeboxmc.world.chunk.Chunk;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class JukeboxMC {

    @Getter
    private static JukeboxMC instance;
    @Getter
    private Server server;
    @Getter
    private InetSocketAddress address;

    public static void main( String[] args ) {
        instance = new JukeboxMC();
    }

    private JukeboxMC() {
        System.out.println( "Server wird gestartet...." );
        ResourceLeakDetector.setLevel( ResourceLeakDetector.Level.DISABLED );

        BlockType.init();
        ItemType.init();

        this.address = new InetSocketAddress( "0.0.0.0", 19132 );
        this.server = new Server( this.address );

        TerminalConsole terminalConsole = new TerminalConsole( this.server );
        terminalConsole.getConsoleThread().start();

        //TDOO Implement all not registered items
        for ( Map<String, Object> stringObjectMap : ItemType.getItemPalette() ) {
            int id = (int) (double) stringObjectMap.get( "id" );

            Item item = ItemType.getItemFormNetworkId( id );
            if ( item == null ) {
                System.out.println( id );
            }
        }

        System.out.println( "JukeboxMC läuft nun auf dem Port " + this.address.getPort() );
    }

    private void initDebug() {
        final List<NbtMap> blocks = BlockPalette.searchBlocks( blockMap -> blockMap.getString( "name" ).equals( "minecraft:wood" ) );

        for ( NbtMap nbtMap : blocks ) {
            if ( nbtMap.getCompound( "states" ).getString( "wood_type" ).equalsIgnoreCase( "birch" ) && nbtMap.getCompound( "states" ).getString( "pillar_axis" ).equalsIgnoreCase( "y" ) && nbtMap.getInt( "stripped_bit" ) == 0 )
                System.out.println( blocks.indexOf( nbtMap ) );
        }
    }
}
