package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.BlockColor;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemStainedGlassPane;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStainedGlassPane extends Block {

    public BlockStainedGlassPane( Identifier identifier ) {
        super( identifier );
    }

    public BlockStainedGlassPane( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public Item toItem() {
        return Item.<ItemStainedGlassPane>create( ItemType.STAINED_GLASS_PANE ).setColor( this.getColor() );
    }

    public @NotNull BlockStainedGlassPane setColor(@NotNull BlockColor color ) {
        this.setState( "color", color.name().toLowerCase() );
        return this;
    }

    public @NotNull BlockColor getColor() {
        return this.stateExists( "color" ) ? BlockColor.valueOf( this.getStringState( "color" ) ) : BlockColor.WHITE;
    }
}
