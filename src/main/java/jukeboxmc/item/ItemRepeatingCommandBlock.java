package jukeboxmc.item;

import org.jukeboxmc.block.BlockRepeatingCommandBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRepeatingCommandBlock extends Item {

    public ItemRepeatingCommandBlock() {
        super ( "minecraft:repeating_command_block" );
    }

    @Override
    public BlockRepeatingCommandBlock getBlock() {
        return new BlockRepeatingCommandBlock();
    }
}
