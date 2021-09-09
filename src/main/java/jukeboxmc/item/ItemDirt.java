package jukeboxmc.item;

import org.jukeboxmc.block.BlockDirt;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDirt extends Item {

    public ItemDirt() {
        super ( "minecraft:dirt" );
    }

    @Override
    public BlockDirt getBlock() {
        return new BlockDirt();
    }

    public void setDirtType( DirtType dirtType ) {
        this.setMeta( dirtType.ordinal() );
    }

    public DirtType getDirtType() {
        return DirtType.values()[this.getMeta()];
    }

    public enum DirtType {
        DIRT,
        COARSA
    }
}
