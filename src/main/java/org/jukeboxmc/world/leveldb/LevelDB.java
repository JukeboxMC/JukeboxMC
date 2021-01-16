package org.jukeboxmc.world.leveldb;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.PooledByteBufAllocator;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.jukeboxmc.Server;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.NBTInputStream;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtUtils;
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

    private DB db;

    private World world;

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

            ByteBuf allocate = this.allocate( data );
            try {
                NBTInputStream networkReader = NbtUtils.createReaderLE( new ByteBufInputStream( allocate ) );
                NbtMap nbt = (NbtMap) networkReader.readTag();
               // System.out.println( nbt.toString() );

                this.world.setSpawnLocation( new Vector( nbt.getInt( "SpawnX", 0 ), nbt.getInt( "SpawnY", 4 ) + 1.62f, nbt.getInt( "SpawnZ", 0 ) ) );
                this.world.setDifficulty( Difficulty.getDifficulty( nbt.getInt( "Difficulty", 2 ) ) );
            } catch ( IOException e ) {
                e.printStackTrace();
            }

        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public void open() {
        try {
            this.db = Iq80DBFactory.factory.open( this.worldFolder, new Options().createIfMissing( true ) );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public void loadChunk( int x, int z, boolean generate ) {

    }

    public ByteBuf getKey(int chunkX, int chunkZ, byte dataType) {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer(9);
        return buf.writeIntLE(chunkX).writeIntLE(chunkZ).writeByte(dataType);
    }


    public ByteBuf allocate( byte[] data ) {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer( data.length );
        buf.writeBytes( data );
        return buf;
    }

}
