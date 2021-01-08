package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCoralBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoralBlock extends Item {

    public ItemCoralBlock() {
        super( "minecraft:coral_block", -132 );
    }

    @Override
    public BlockCoralBlock getBlock() {
        return new BlockCoralBlock();
    }

    public void setCoralType( CoralType coralType ) {
        switch ( coralType ) {
            case TUBE:
                this.setMeta( 0 );
                break;
            case BRAIN:
                this.setMeta( 1 );
                break;
            case BUBBLE:
                this.setMeta( 2 );
                break;
            case FIRE:
                this.setMeta( 3 );
                break;
            case HORN:
                this.setMeta( 4 );
                break;
            case DEAD_TUBE:
                this.setMeta( 8 );
                break;
            case DEAD_BRAIN:
                this.setMeta( 9 );
                break;
            case DEAD_BUBBLE:
                this.setMeta( 10 );
                break;
            case DEAD_FIRE:
                this.setMeta( 11 );
                break;
            default:
                this.setMeta( 12 );
                break;
        }
    }

    public CoralType getCoralType() {
        switch ( this.getMeta() ) {
            case 0:
                return CoralType.TUBE;
            case 1:
                return CoralType.BRAIN;
            case 2:
                return CoralType.BUBBLE;
            case 3:
                return CoralType.FIRE;
            case 4:
                return CoralType.HORN;
            case 8:
                return CoralType.DEAD_TUBE;
            case 9:
                return CoralType.DEAD_BRAIN;
            case 10:
                return CoralType.DEAD_BUBBLE;
            case 11:
                return CoralType.DEAD_FIRE;
            default:
                return CoralType.DEAD_HORN;
        }
    }

    public enum CoralType {
        TUBE,
        BRAIN,
        BUBBLE,
        FIRE,
        HORN,
        DEAD_TUBE,
        DEAD_BRAIN,
        DEAD_BUBBLE,
        DEAD_FIRE,
        DEAD_HORN
    }
}
