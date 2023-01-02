package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockCoralBlock;
import org.jukeboxmc.block.data.CoralColor;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoralBlock extends Item {

    private final BlockCoralBlock block;

    public ItemCoralBlock( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.CORAL_BLOCK );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemCoralBlock( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.CORAL_BLOCK );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemCoralBlock setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public ItemCoralBlock setCoralColor( CoralColor coralColor ) {
        this.blockRuntimeId = this.block.setCoralColor( coralColor ).getRuntimeId();
        return this;
    }

    public CoralColor getCoralColor() {
        return this.block.getCoralColor();
    }

    public ItemCoralBlock setDead( boolean value ) {
        this.blockRuntimeId = this.block.setDead( value ).getRuntimeId();
        return this;
    }

    public boolean isDead() {
        return this.block.isDead();
    }

}
