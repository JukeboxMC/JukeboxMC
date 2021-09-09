package jukeboxmc.entity.adventure;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class AdventureType {

    public enum FirstFlag {
        WORLD_IMMUTABLE( 0x01 ),
        NO_PVP( 0x02 ),
        AUTO_JUMP( 0x20 ),
        CAN_FLY( 0x40 ),
        NO_CLIP( 0x80 ),
        WORLD_BUILDER( 0x100 ),
        FLYING( 0x200 ),
        MUTED( 0x400 );

        private int id;

        FirstFlag( int id ) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }
    }

    public enum SecondFlag {

        BUILD_AND_MINE( 0x01 ),
        USE_DOORS_AND_SWITCHES( 0x02 ),
        OPEN_CONTAINERS( 0x04 ),
        ATTACK_PLAYERS( 0x08 ),
        ATTACK_MOBS( 0x10 ),
        OPERATOR( 0x20 ),
        TELEPORT( 0x80 );

        private int id;

        SecondFlag( int id ) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }
    }

}
