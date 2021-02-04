package org.jukeboxmc.world.leveldb;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.NBTInputStream;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtUtils;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.Difficulty;

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

    protected DB db;

    protected Vector spawnLocation;
    protected Difficulty difficulty;

    public LevelDB( String worldName ) {
        this.worldFolder = new File( "./worlds/" + worldName );
        this.worldFile = new File( this.worldFolder, "level.dat" );

        if ( !this.worldFolder.exists() ) {
            this.worldFolder.mkdirs();
        }
    }

    public boolean loadLevelFile() {
        try ( FileInputStream stream = new FileInputStream( this.worldFile ) ) {
            stream.skip( 8 );
            byte[] data = new byte[stream.available()];
            stream.read( data );

            ByteBuf allocate = Utils.allocate( data );
            try {
                NBTInputStream networkReader = NbtUtils.createReaderLE( new ByteBufInputStream( allocate ) );
                NbtMap nbt = (NbtMap) networkReader.readTag();
                this.spawnLocation = new Vector( nbt.getInt( "SpawnX", 0 ), nbt.getInt( "SpawnY", 4 ) + 1.62f, nbt.getInt( "SpawnZ", 0 ) );
                this.difficulty = Difficulty.getDifficulty( nbt.getInt( "Difficulty", 2 ) );
                return true;
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean open() {
        try {
            this.db = Iq80DBFactory.factory.open( new File( this.worldFolder, "db/" ), new Options().createIfMissing( true ) );
            return true;
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return false;
    }

}