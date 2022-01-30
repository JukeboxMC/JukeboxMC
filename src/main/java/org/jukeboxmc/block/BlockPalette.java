package org.jukeboxmc.block;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jukeboxmc.JukeboxMC;
import org.jukeboxmc.nbt.NBTInputStream;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtType;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.zip.GZIPInputStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPalette {

    private static final AtomicInteger RUNTIME_COUNTER = new AtomicInteger( 0 );
    public static final Map<Integer, Block> RUNTIME_TO_BLOCK = new LinkedHashMap<>();
    public static final Map<Integer, NbtMap> BLOCK_PALETTE = new LinkedHashMap<>();

    public static Map<BlockData, Integer> TEST = new HashMap<>();
    public static Map<String, Integer> DEFAULTS = new HashMap<>();

    public static final List<BlockData> BLOCK_DATA = new CopyOnWriteArrayList<>();

    public static void init() {
        InputStream resourceAsStream = JukeboxMC.class.getClassLoader().getResourceAsStream( "block_palette.nbt" );
        if ( resourceAsStream != null ) {
            try ( NBTInputStream nbtReader = new NBTInputStream( new DataInputStream( new GZIPInputStream( resourceAsStream ) ) ) ) {
                NbtMap nbtMap = (NbtMap) nbtReader.readTag();
                for ( NbtMap blockMap : nbtMap.getList( "blocks", NbtType.COMPOUND ) ) {
                    int runtimeId = RUNTIME_COUNTER.getAndIncrement();
                    BLOCK_PALETTE.put( runtimeId, blockMap );

                    TEST.put( new BlockData( blockMap.getString( "name" ), blockMap.getCompound( "states" ) ), runtimeId );
                    DEFAULTS.putIfAbsent( blockMap.getString( "name" ), runtimeId );
                }
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }

    public static NbtMap getBlockNBT( int runtimeId ) {
        return BLOCK_PALETTE.get( runtimeId );
    }

    public static int getRuntimeId( String identifier, NbtMap compound ) {
        return TEST.computeIfAbsent( new BlockData( identifier, compound ), ourData -> {
            if ( ourData.getStates().isEmpty() ) {
                return DEFAULTS.getOrDefault( identifier, 134 );
            }

            block_loop:
            for ( Map.Entry<BlockData, Integer> entry : TEST.entrySet() ) {
                BlockData otherData = entry.getKey();
                if ( otherData.getIdentifier().equalsIgnoreCase( ourData.getIdentifier() ) ) {
                    for ( Map.Entry<String, Object> stateEntry : ourData.getStates().entrySet() ) {
                        if ( !otherData.getStates().containsKey( stateEntry.getKey() ) ) {
                            continue block_loop;
                        }

                        if ( !otherData.getStates().get( stateEntry.getKey() ).equals( stateEntry.getKey() ) ) {
                            continue block_loop;
                        }
                    }

                    return entry.getValue();
                }
            }
            System.out.println( " " + ourData.identifier + " [" + compound + "] konnte nicht gefunden werden!" );
            return 134;
        } );
    }

   /* public static int getRuntimeId( String identifier, NbtMap states ) {
        return TEST.getOrDefault( new BlockData( identifier, states ), 134 );
    }*/

    public static Integer getRuntimeId( NbtMap blockMap ) {
        for ( Integer runtimeId : BLOCK_PALETTE.keySet() ) {
            if ( BLOCK_PALETTE.get( runtimeId ).equals( blockMap ) ) {
                return runtimeId;
            }
        }
        throw new NullPointerException( "Block was not found" );
    }

    public static List<NbtMap> searchBlocks( Predicate<NbtMap> predicate ) {
        List<NbtMap> blocks = new ArrayList<>();
        for ( NbtMap nbtMap : BLOCK_PALETTE.values() ) {
            if ( predicate.test( nbtMap ) ) {
                blocks.add( nbtMap );
            }
        }
        return Collections.unmodifiableList( blocks );
    }

    @Data
    @AllArgsConstructor
    public static class BlockData {

        private String identifier;
        private NbtMap states;

    }
}
