package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.SeaGrassType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSeagrass extends Block {

    public BlockSeagrass( Identifier identifier ) {
        super( identifier );
    }

    public BlockSeagrass( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    public void setSeaGrassType( SeaGrassType seaGrassType ) {
        this.setState( "sea_grass_type",seaGrassType.name().toLowerCase() );
    }

    public SeaGrassType getSeaGrassType() {
        return this.stateExists( "sea_grass_type" ) ? SeaGrassType.valueOf( this.getStringState( "sea_grass_type" ) ) : SeaGrassType.DEFAULT;
    }
}
