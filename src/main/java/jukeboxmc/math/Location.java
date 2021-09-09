package jukeboxmc.math;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Location extends Vector {

    private World world;
    private float yaw;
    private float pitch;

    public Location( World world, float x, float y, float z, float yaw, float pitch, Dimension dimension ) {
        super( x, y, z );
        this.world = world;
        this.yaw = yaw;
        this.pitch = pitch;
        this.dimension = dimension;
    }

    public Location( World world, float x, float y, float z, float yaw, float pitch ) {
        this( world, x, y, z, yaw, pitch, Dimension.OVERWORLD );
    }

    public Location( World world, float x, float y, float z, Dimension dimension ) {
        this( world, x, y, z, 0, 0, dimension );
    }

    public Location( World world, float x, float y, float z ) {
        this( world, x, y, z, 0, 0, Dimension.OVERWORLD );
    }

    public Location( World world, int x, int y, int z, Dimension dimension ) {
        this( world, x, y, z, 0, 0, dimension );
    }

    public Location( World world, int x, int y, int z ) {
        this( world, x, y, z, 0, 0, Dimension.OVERWORLD );
    }

    public Location( World world, Vector vector, float yaw, float pitch, Dimension dimension ) {
        this( world, vector.getX(), vector.getY(), vector.getZ(), yaw, pitch, dimension );
    }

    public Location( World world, Vector vector, float yaw, float pitch ) {
        this( world, vector.getX(), vector.getY(), vector.getZ(), yaw, pitch, Dimension.OVERWORLD );
    }

    public Location( World world, Vector vector ) {
        this( world, vector.getX(), vector.getY(), vector.getZ(), 0, 0, Dimension.OVERWORLD );
    }

    public World getWorld() {
        return this.world;
    }

    public void setWorld( World world ) {
        this.world = world;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw( float yaw ) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch( float pitch ) {
        this.pitch = pitch;
    }

    public Block getBlock() {
        return this.world.getBlock( this );
    }

    public Chunk getChunk() {
        return this.world.getChunk( this.getBlockX() >> 4, this.getBlockZ() >> 4, this.dimension );
    }

    public Vector getDirection() {
        double pitch = ( ( this.pitch + 90 ) * Math.PI ) / 180;
        double yaw = ( ( this.yaw + 90 ) * Math.PI ) / 180;
        double x = Math.sin( pitch ) * Math.cos( yaw );
        double z = Math.sin( pitch ) * Math.sin( yaw );
        double y = Math.cos( pitch );
        return new Vector( (float) x, (float) y, (float) z ).normalize();
    }
}
