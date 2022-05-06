package org.jukeboxmc.block;

import com.nukkitx.nbt.NBTInputStream;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtType;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jukeboxmc.Bootstrap;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.zip.GZIPInputStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPalette {

    private static final AtomicInteger RUNTIME_COUNTER = new AtomicInteger( 0 );
    public static final Int2ObjectMap<Block> RUNTIME_TO_BLOCK = new Int2ObjectOpenHashMap<>(8 * 8, Hash.VERY_FAST_LOAD_FACTOR );
    public static final Int2ObjectMap<NbtMap> BLOCK_PALETTE = new Int2ObjectLinkedOpenHashMap<>(8 * 8, Hash.VERY_FAST_LOAD_FACTOR );

    public static Object2IntMap<BlockData> BLOCK_DATA = new Object2IntOpenHashMap<>(8 * 8, Hash.VERY_FAST_LOAD_FACTOR );
    public static Object2IntMap<String> DEFAULTS = new Object2IntOpenHashMap<>(8 * 8, Hash.VERY_FAST_LOAD_FACTOR );

    public static void init() {
        InputStream resourceAsStream = Bootstrap.class.getClassLoader().getResourceAsStream( "block_palette.nbt" );
        if ( resourceAsStream != null ) {
            try ( NBTInputStream nbtReader = new NBTInputStream( new DataInputStream( new GZIPInputStream( resourceAsStream ) ) ) ) {
                NbtMap nbtMap = (NbtMap) nbtReader.readTag();
                for ( NbtMap blockMap : nbtMap.getList( "blocks", NbtType.COMPOUND ) ) {
                    int runtimeId = RUNTIME_COUNTER.getAndIncrement();
                    BLOCK_PALETTE.put( runtimeId, blockMap );

                    BLOCK_DATA.put( new BlockData( blockMap.getString( "name" ), blockMap.getCompound( "states" ) ), runtimeId );
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

    public static int getRuntimeId( String identifier, NbtMap states ) {
        return BLOCK_DATA.getOrDefault( new BlockData( identifier, states ), 134 );
    }

    public static Integer getRuntimeId( NbtMap blockMap ) {
        for ( int runtimeId : BLOCK_PALETTE.keySet() ) {
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
