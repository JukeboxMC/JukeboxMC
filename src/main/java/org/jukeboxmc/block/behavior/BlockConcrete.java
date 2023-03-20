package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.BlockColor;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemConcrete;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockConcrete extends Block {

    public BlockConcrete( Identifier identifier ) {
        super( identifier );
    }

    public BlockConcrete( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public Item toItem() {
        return Item.<ItemConcrete>create( ItemType.CONCRETE ).setColor( this.getColor() );
    }

    public BlockConcrete setColor(@NotNull BlockColor color ) {
        return this.setState( "color", color.name().toLowerCase() );
    }

    public @NotNull BlockColor getColor() {
        return this.stateExists( "color" ) ? BlockColor.valueOf( this.getStringState( "color" ) ) : BlockColor.WHITE;
    }
}
