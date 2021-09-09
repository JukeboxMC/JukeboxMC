package jukeboxmc.item;

import org.jukeboxmc.block.BlockBuddingAmethyst;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBuddingAmethyst extends Item{

    public ItemBuddingAmethyst() {
        super( "minecraft:budding_amethyst" );
    }

    @Override
    public BlockBuddingAmethyst getBlock() {
        return new BlockBuddingAmethyst();
    }
}
