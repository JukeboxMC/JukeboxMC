package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.data.WallType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemWall;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCobblestoneWall extends BlockWall {

    public BlockCobblestoneWall( Identifier identifier ) {
        super( identifier );
    }

    public BlockCobblestoneWall( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public Item toItem() {
        return Item.<ItemWall>create( ItemType.COBBLESTONE_WALL ).setWallType( this.getWallBlockType() );
    }

    public BlockCobblestoneWall setWallBlockType(@NotNull WallType wallType ) {
        return this.setState( "wall_block_type", wallType.name().toLowerCase() );
    }

    public @NotNull WallType getWallBlockType() {
        return this.stateExists( "wall_block_type" ) ? WallType.valueOf( this.getStringState( "wall_block_type" ) ) : WallType.COBBLESTONE;
    }
}
