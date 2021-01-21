package org.jukeboxmc.block;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.jukeboxmc.JukeboxMC;
import org.jukeboxmc.nbt.NBTInputStream;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtType;
import org.jukeboxmc.nbt.NbtUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPalette {

    private static final AtomicInteger RUNTIME_COUNTER = new AtomicInteger( 0 );

    public static final Map<Integer, NbtMap> BLOCK_PALETTE = new LinkedHashMap<>();
    public static final Map<Integer, Block> RUNTIME_TO_BLOCK = new LinkedHashMap<>();
    public static final List<BlockData> BLOCK_DATA = new LinkedList<>();

    @SneakyThrows
    public static void init() {
        try ( NBTInputStream nbtReader = NbtUtils.createReader( JukeboxMC.class.getClassLoader().getResourceAsStream( "blockpalette.nbt" ) ) ) {
            NbtMap nbtMap = (NbtMap) nbtReader.readTag();
            for ( NbtMap blockMap : nbtMap.getList( "blocks", NbtType.COMPOUND ) ) {
                NbtMap block = blockMap.getCompound( "block" );
                int runtimeId = RUNTIME_COUNTER.getAndIncrement();
                BLOCK_PALETTE.put( runtimeId, block );
                BLOCK_DATA.add( new BlockData( block.getString( "name" ), runtimeId, block.getCompound( "states" ) ) );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public static NbtMap getBlockNBT( int runtimeId ) {
        return BLOCK_PALETTE.get( runtimeId );
    }

    public static int getRuntimeId( String identifier, NbtMap compound ) {
        for ( BlockData blockData : BLOCK_DATA ) {
            if ( blockData.getIdentifier().equals( identifier ) && blockData.getStates().equals( compound ) ) {
                return blockData.getRuntimeId();
            }
        }
        return 134;
    }

    public static Integer getRuntimeId( NbtMap blockMap ) {
        for ( Integer runtimeId : BLOCK_PALETTE.keySet() ) {
            if ( BLOCK_PALETTE.get( runtimeId ).equals( blockMap ) ) {
                return runtimeId;
            }
        }
        throw new NullPointerException( "Block was not found" );
    }

    public static NbtMap getBlockMap( Integer runtimeId ) {
        return Objects.requireNonNull( BLOCK_PALETTE.get( runtimeId ), "Block was not found" );
    }

    public static NbtMap searchBlock( Predicate<NbtMap> predicate ) {
        for ( NbtMap nbtMap : BLOCK_PALETTE.values() ) {
            if ( predicate.test( nbtMap ) ) {
                return nbtMap;
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
        private int runtimeId;
        private NbtMap states;

    }

}
