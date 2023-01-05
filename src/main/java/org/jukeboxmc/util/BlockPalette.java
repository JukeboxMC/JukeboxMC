package org.jukeboxmc.util;

import com.nukkitx.nbt.NBTInputStream;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtType;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jukeboxmc.Bootstrap;
import org.jukeboxmc.block.Block;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.zip.GZIPInputStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPalette {

    public static final Int2ObjectMap<NbtMap> STATE_FROM_RUNTIME = new Int2ObjectLinkedOpenHashMap<>();
    public static final Int2ObjectMap<NbtMap> BLOCK_STATE_FROM_RUNTIME = new Int2ObjectLinkedOpenHashMap<>();
    public static final Object2ObjectMap<BlockData, Block> BLOCK_CACHE = new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectMap<Identifier, Integer> IDENTIFIER_TO_RUNTIME = new Object2ObjectOpenHashMap<>();
    public static final List<NbtMap> BLOCK_NBT = new LinkedList<>();

    public static void init() {
        AtomicInteger RUNTIME_COUNTER = new AtomicInteger( 0 );
        InputStream resourceAsStream = Bootstrap.class.getClassLoader().getResourceAsStream( "block_palette.nbt" );
        if ( resourceAsStream != null ) {
            try ( NBTInputStream nbtReader = new NBTInputStream( new DataInputStream( new GZIPInputStream( resourceAsStream ) ) ) ) {
                NbtMap nbtMap = (NbtMap) nbtReader.readTag();
                BLOCK_NBT.addAll( nbtMap.getList( "blocks", NbtType.COMPOUND ) );
                for ( NbtMap blockMap : nbtMap.getList( "blocks", NbtType.COMPOUND ) ) {
                    int runtimeId = RUNTIME_COUNTER.getAndIncrement();
                    BLOCK_STATE_FROM_RUNTIME.put( runtimeId, blockMap.getCompound( "states" ) );
                    STATE_FROM_RUNTIME.put( runtimeId, blockMap );
                    IDENTIFIER_TO_RUNTIME.putIfAbsent( Identifier.fromString( blockMap.getString( "name" ) ), runtimeId );
                }
            } catch ( IOException e ) {
                throw new RuntimeException( e );
            }
        }
    }

    public static int getRuntimeByIdentifier( Identifier identifier ) {
        return IDENTIFIER_TO_RUNTIME.get( identifier );
    }

    public static NbtMap getBlockNbt( int runtimeId ) {
        return BLOCK_NBT.get( runtimeId );
    }

    public static Block getBlockByNBT( NbtMap nbtMap ) {
        Identifier identifier = Identifier.fromString( nbtMap.getString( "name" ) );
        NbtMap blockStates = nbtMap.getCompound( "states" );
        BlockData blockData = new BlockData( identifier, blockStates );
        if ( BLOCK_CACHE.containsKey( blockData ) ) {
            return BLOCK_CACHE.get( blockData ).clone();
        }
        Block block = Block.create( identifier, blockStates );
        BLOCK_CACHE.put( blockData, block );
        return block;
    }

    public static Block getBlock( Identifier identifier, NbtMap blockStates ) {
        BlockData blockData = new BlockData( identifier, blockStates );
        if ( BLOCK_CACHE.containsKey( blockData ) ) {
            return BLOCK_CACHE.get( blockData ).clone();
        }
        Block block = Block.create( identifier, blockStates );
        BLOCK_CACHE.put( blockData, block );
        return block;
    }

    public static Integer getRuntimeId( NbtMap blockMap ) {
        for ( int runtimeId : STATE_FROM_RUNTIME.keySet() ) {
            if ( STATE_FROM_RUNTIME.get( runtimeId ).equals( blockMap ) ) {
                return runtimeId;
            }
        }
        throw new NullPointerException( "Block was not found" );
    }

    public static Integer getBlockRuntimeId( NbtMap blockMap ) {
        for ( int runtimeId : BLOCK_STATE_FROM_RUNTIME.keySet() ) {
            if ( BLOCK_STATE_FROM_RUNTIME.get( runtimeId ).equals( blockMap ) ) {
                return runtimeId;
            }
        }
        throw new NullPointerException( "Block was not found: " + blockMap );
    }

    public static List<NbtMap> searchBlocks( Predicate<NbtMap> predicate ) {
        List<NbtMap> blocks = new ArrayList<>();
        for ( NbtMap nbtMap : STATE_FROM_RUNTIME.values() ) {
            if ( predicate.test( nbtMap ) ) {
                blocks.add( nbtMap );
            }
        }
        return Collections.unmodifiableList( blocks );
    }

    @Data
    @AllArgsConstructor
    public static class BlockData {

        private Identifier identifier;
        private NbtMap states;

    }
}
