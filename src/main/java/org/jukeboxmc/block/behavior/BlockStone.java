package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.StoneType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemStone;
import org.jukeboxmc.util.Identifier;

import java.util.Collections;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStone extends Block {

    public BlockStone( Identifier identifier ) {
        super( identifier );
    }

    public BlockStone( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public Item toItem() {
        return Item.<ItemStone>create( ItemType.STONE ).setStoneType( this.getStoneType() );
    }

    @Override
    public @NotNull List<Item> getDrops(@Nullable Item item ) {
        if ( item != null && this.isCorrectToolType( item ) && this.isCorrectTierType( item ) ) {
            return Collections.singletonList( Item.create( ItemType.COBBLESTONE ) );
        }
        return Collections.emptyList();
    }

    public BlockStone setStoneType(@NotNull StoneType stoneType ) {
        return this.setState( "stone_type", stoneType.name().toLowerCase() );
    }

    public @NotNull StoneType getStoneType() {
        return this.stateExists( "stone_type" ) ? StoneType.valueOf( this.getStringState( "stone_type" ) ) : StoneType.STONE;
    }
}
