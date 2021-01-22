package org.jukeboxmc.world.leveldb;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.DbImpl;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.jukeboxmc.Server;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.NBTInputStream;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtUtils;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.Difficulty;
import org.jukeboxmc.world.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class LevelDB {

    private File worldFolder;
    private File worldFile;

    protected DbImpl db;

    private World world;
    protected Vector spawnLocation;
    protected Difficulty difficulty;

    public LevelDB() {
        this.world = Server.getInstance().getDefaultWorld();
        this.worldFolder = new File( "./worlds/world/" );
        this.worldFile = new File( this.worldFolder, "level.dat" );

        if ( !this.worldFolder.exists() ) {
            if ( this.worldFolder.mkdirs() ) {
                System.out.println( "Worlds folder was created successfully" );
            }
        }

        if ( !this.worldFile.exists() || !this.worldFile.isFile() ) {
            System.out.println( "level.dat is missing" );
        }
    }

    public void loadLevelFile() {
        try ( FileInputStream stream = new FileInputStream( this.worldFile ) ) {
            stream.skip( 8 );
            byte[] data = new byte[stream.available()];
            stream.read( data );

            ByteBuf allocate = Utils.allocate( data );
            try {
                NBTInputStream networkReader = NbtUtils.createReaderLE( new ByteBufInputStream( allocate ) );
                NbtMap nbt = (NbtMap) networkReader.readTag();
                this.spawnLocation = new Vector( nbt.getInt( "SpawnX", 0 ), 63 + 1.62f, nbt.getInt( "SpawnZ", 0 ) );
                this.difficulty = Difficulty.getDifficulty( nbt.getInt( "Difficulty", 2 ) );
            } catch ( IOException e ) {
                e.printStackTrace();
            }

        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public void open() {
        try {
            this.db = (DbImpl) Iq80DBFactory.factory.open( new File( this.worldFolder, "db/" ), new Options().createIfMissing( true ) );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public byte[] getKey( int chunkX, int chunkZ, byte key ) {
        return new byte[]{
                (byte) ( chunkX & 0xff ),
                (byte) ( ( chunkX >>> 8 ) & 0xff ),
                (byte) ( ( chunkX >>> 16 ) & 0xff ),
                (byte) ( ( chunkX >>> 24 ) & 0xff ),
                (byte) ( chunkZ & 0xff ),
                (byte) ( ( chunkZ >>> 8 ) & 0xff ),
                (byte) ( ( chunkZ >>> 16 ) & 0xff ),
                (byte) ( ( chunkZ >>> 24 ) & 0xff ),
                key
        };
    }

    public byte[] getSubChunkKey( int chunkX, int chunkZ, byte key, byte subChunk ) {
        return new byte[]{
                (byte) ( chunkX & 0xff ),
                (byte) ( ( chunkX >>> 8 ) & 0xff ),
                (byte) ( ( chunkX >>> 16 ) & 0xff ),
                (byte) ( ( chunkX >>> 24 ) & 0xff ),
                (byte) ( chunkZ & 0xff ),
                (byte) ( ( chunkZ >>> 8 ) & 0xff ),
                (byte) ( ( chunkZ >>> 16 ) & 0xff ),
                (byte) ( ( chunkZ >>> 24 ) & 0xff ),
                key,
                subChunk
        };
    }

}