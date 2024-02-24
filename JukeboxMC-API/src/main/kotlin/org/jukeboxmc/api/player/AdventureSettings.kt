package org.jukeboxmc.api.player

interface AdventureSettings {

    fun set(type: Type, value: Boolean): AdventureSettings

    fun get(type: Type): Boolean

    fun getFlySpeed(): Float

    fun setFlySpeed(flySpeed: Float)

    fun getWalkSpeed(): Float

    fun setWalkSpeed(walkSpeed: Float)

    fun update()

    enum class Type {
        WORLD_IMMUTABLE,
        NO_PVM,
        NO_MVP,
        SHOW_NAME_TAGS,
        AUTO_JUMP,
        ALLOW_FLIGHT,
        NO_CLIP,
        WORLD_BUILDER,
        FLYING,
        MUTED,
        MINE,
        DOORS_AND_SWITCHES,
        OPEN_CONTAINERS,
        ATTACK_PLAYERS,
        ATTACK_MOBS,
        OPERATOR,
        TELEPORT,
        BUILD,
        PRIVILEGED_BUILDER;
    }

}