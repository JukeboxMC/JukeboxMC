package org.jukeboxmc.entity;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum EntityEventType {

    NONE( 0 ),
    JUMP( 1 ),
    HURT( 2 ),
    DEATH( 3 ),
    ATTACK_START( 4 ),
    ATTACK_STOP( 5 ),
    TAME_FAILED( 6 ),
    TAME_SUCCEEDED( 7 ),
    SHAKE_WETNESS( 8 ),
    USE_ITEM( 9 ),
    EAT_GRASS( 10 ),
    FISH_HOOK_BUBBLE( 11 ),
    FISH_HOOK_POSITION( 12 ),
    FISH_HOOK_TIME( 13 ),
    FISH_HOOK_TEASE( 14 ),
    SQUID_FLEEING( 15 ),
    ZOMBIE_VILLAGER_CURE( 16 ),
    PLAY_AMBIENT( 17 ),
    RESPAWN( 18 ),
    GOLEM_FLOWER_OFFER( 19 ),
    GOLEM_FLOWER_WITHDRAW( 20 ),
    LOVE_PARTICLES( 21 ),
    VILLAGER_ANGRY( 22 ),
    VILLAGER_HAPPY( 23 ),
    WITCH_HAT_MAGIC( 24 ),
    FIREWORK_EXPLODE( 25 ),
    IN_LOVE_HEARTS( 26 ),
    SILVERFISH_MERGE_WITH_STONE( 27 ),
    GUARDIAN_ATTACK_ANIMATION( 28 ),
    WITCH_DRINK_POTION( 29 ),
    WITCH_THROW_POTION( 30 ),
    PRIME_TNT_MINECART( 31 ),
    PRIME_CREEPER( 32 ),
    AIR_SUPPLY( 33 ),
    PLAYER_ADD_XP_LEVELS( 34 ),
    ELDER_GUARDIAN_CURSE( 35 ),
    AGENT_ARM_SWING( 36 ),
    ENDER_DRAGON_DEATH( 37 ),
    DUST_PARTICLES( 38 ),
    ARROW_SHAKE( 39 ),
    EATING_ITEM( 57 ),
    BABY_ANIMAL_FEED( 60 ),
    DEATH_SMOKE_CLOUD( 61 ),
    COMPLETE_TRADE( 62 ),
    REMOVE_LEASH( 63 ),
    CARAVAN( 64 ),
    CONSUME_TOTEM( 65 ),
    CHECK_TREASURE_HUNTER_ACHIEVEMENT( 66 ),
    ENTITY_SPAWN( 67 ),
    DRAGON_FLAMING( 68 ),
    UPDATE_ITEM_STACK_SIZE( 69 ),
    START_SWIMMING( 70 ),
    BALLOON_POP( 71 ),
    TREASURE_HUNT( 72 ),
    SUMMON_AGENT( 73 ),
    FINISHED_CHARGING_CROSSBOW( 74 ),
    LANDED_ON_GROUND( 75 );

    private final int eventId;

    EntityEventType( int eventId ) {
        this.eventId = eventId;
    }

    public int getEventId() {
        return this.eventId;
    }

    public static EntityEventType getEntityEventById( int eventId ) {
        for ( EntityEventType entityEventType : EntityEventType.values() ) {
            if ( entityEventType.getEventId() == eventId ) {
                return entityEventType;
            }
        }
        return NONE;
    }
}
