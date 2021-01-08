package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAnvil;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAnvil extends Item {

    public ItemAnvil() {
        super( "minecraft:anvil", 145 );
    }

    @Override
    public BlockAnvil getBlock() {
        return new BlockAnvil();
    }

    public void setAnvilType( AnvilType anvilType ) {
        switch ( anvilType ) {
            case SLIGHTLY_ANVIL:
                this.setMeta( 4 );
                break;
            case VERY_DAMAGED_ANVIL:
                this.setMeta( 8 );
                break;
            default:
                this.setMeta( 0 );
                break;
        }
    }

    public AnvilType getAnvilType() {
        switch ( this.getMeta() ) {
            case 4:
                return AnvilType.SLIGHTLY_ANVIL;
            case 8:
                return AnvilType.VERY_DAMAGED_ANVIL;
            default:
                return AnvilType.ANVIL;
        }
    }

    public enum AnvilType {
        ANVIL,
        SLIGHTLY_ANVIL,
        VERY_DAMAGED_ANVIL
    }

}
