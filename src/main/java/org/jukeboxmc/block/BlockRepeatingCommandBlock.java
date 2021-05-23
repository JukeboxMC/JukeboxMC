package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.ItemRepeatingCommandBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRepeatingCommandBlock extends Block {

    public BlockRepeatingCommandBlock() {
        super( "minecraft:repeating_command_block" );
    }

    @Override
    public ItemRepeatingCommandBlock toItem() {
        return new ItemRepeatingCommandBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.REPEATING_COMMAND_BLOCK;
    }

    public void setConditional( boolean value ) {
        this.setState( "conditional_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isConditional() {
        return this.stateExists( "conditional_bit" ) && this.getByteState( "conditional_bit" ) == 1;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
