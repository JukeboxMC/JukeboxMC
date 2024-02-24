package org.jukeboxmc.server.player

import org.cloudburstmc.protocol.bedrock.data.Ability
import org.cloudburstmc.protocol.bedrock.data.AbilityLayer
import org.cloudburstmc.protocol.bedrock.data.PlayerPermission
import org.cloudburstmc.protocol.bedrock.data.command.CommandPermission
import org.cloudburstmc.protocol.bedrock.packet.UpdateAbilitiesPacket
import org.cloudburstmc.protocol.bedrock.packet.UpdateAdventureSettingsPacket
import org.jukeboxmc.api.player.AdventureSettings
import org.jukeboxmc.api.player.GameMode

class JukeboxAdventureSettings(
    private val player: JukeboxPlayer
) : AdventureSettings {

    private var walkSpeed: Float = 0.1f
    private var flySpeed: Float = 0.05f

    private val values: MutableMap<AdventureSettings.Type, Boolean> = mutableMapOf()
    private val ability: MutableMap<AdventureSettings.Type, TypeData> = mutableMapOf()

    init {
        ability[AdventureSettings.Type.WORLD_IMMUTABLE] = TypeData(defaultValue = false)
        ability[AdventureSettings.Type.NO_PVM] = TypeData(defaultValue = false)
        ability[AdventureSettings.Type.SHOW_NAME_TAGS] = TypeData(defaultValue = true)
        ability[AdventureSettings.Type.AUTO_JUMP] = TypeData(defaultValue = true)
        ability[AdventureSettings.Type.NO_MVP] = TypeData(Ability.INVULNERABLE, false)
        ability[AdventureSettings.Type.ALLOW_FLIGHT] = TypeData(Ability.MAY_FLY, false)
        ability[AdventureSettings.Type.NO_CLIP] = TypeData(Ability.NO_CLIP, false)
        ability[AdventureSettings.Type.WORLD_BUILDER] = TypeData(Ability.WORLD_BUILDER, true)
        ability[AdventureSettings.Type.FLYING] = TypeData(Ability.FLYING, false)
        ability[AdventureSettings.Type.MUTED] = TypeData(Ability.MUTED, false)
        ability[AdventureSettings.Type.MINE] = TypeData(Ability.MINE, true)
        ability[AdventureSettings.Type.DOORS_AND_SWITCHES] = TypeData(Ability.DOORS_AND_SWITCHES, true)
        ability[AdventureSettings.Type.OPEN_CONTAINERS] = TypeData(Ability.OPEN_CONTAINERS, true)
        ability[AdventureSettings.Type.ATTACK_PLAYERS] = TypeData(Ability.ATTACK_PLAYERS, true)
        ability[AdventureSettings.Type.ATTACK_MOBS] = TypeData(Ability.ATTACK_MOBS, true)
        ability[AdventureSettings.Type.OPERATOR] = TypeData(Ability.OPERATOR_COMMANDS, false)
        ability[AdventureSettings.Type.TELEPORT] = TypeData(Ability.TELEPORT, false)
        ability[AdventureSettings.Type.BUILD] = TypeData(Ability.BUILD, true)
        ability[AdventureSettings.Type.PRIVILEGED_BUILDER] = TypeData(Ability.PRIVILEGED_BUILDER, false)
    }

    override fun set(type: AdventureSettings.Type, value: Boolean): AdventureSettings {
        this.values[type] = value
        return this
    }

    override fun get(type: AdventureSettings.Type): Boolean {
        return this.values[type]?: this.ability[type]!!.defaultValue
    }

    override fun getFlySpeed(): Float {
        return this.walkSpeed
    }

    override fun setFlySpeed(flySpeed: Float) {
        this.flySpeed = flySpeed
    }

    override fun getWalkSpeed(): Float {
        return this.walkSpeed
    }

    override fun setWalkSpeed(walkSpeed: Float) {
        this.walkSpeed = walkSpeed
    }

    override fun update() {
        val updateAbilitiesPacket = UpdateAbilitiesPacket()
        updateAbilitiesPacket.uniqueEntityId = this.player.getEntityId()
        updateAbilitiesPacket.commandPermission = if (this.player.isOperator()) CommandPermission.ADMIN else CommandPermission.ANY
        updateAbilitiesPacket.playerPermission = if (this.player.isOperator() && this.player.getGameMode() != GameMode.SPECTATOR
        ) PlayerPermission.OPERATOR else PlayerPermission.MEMBER

        val abilityLayer = AbilityLayer()
        abilityLayer.layerType = AbilityLayer.Type.BASE
        abilityLayer.abilitiesSet.addAll(listOf(*Ability.entries.toTypedArray()))

        for (type in AdventureSettings.Type.entries) {
            this.ability[type]?.let {
                if (it.ability != null && this.get(type)) {
                    abilityLayer.abilityValues.add(it.ability)
                }
            }
        }

        abilityLayer.abilityValues.add(Ability.WALK_SPEED)
        abilityLayer.abilityValues.add(Ability.FLY_SPEED)

        if (this.player.getGameMode() == GameMode.CREATIVE) {
            abilityLayer.abilityValues.add(Ability.INSTABUILD)
        }

        if (this.player.isOperator()) {
            abilityLayer.abilityValues.add(Ability.OPERATOR_COMMANDS)
        }

        abilityLayer.walkSpeed = walkSpeed
        abilityLayer.flySpeed = flySpeed
        updateAbilitiesPacket.abilityLayers.add(abilityLayer)
        this.player.sendPacket(updateAbilitiesPacket)

        val updateAdventureSettingsPacket = UpdateAdventureSettingsPacket()
        updateAdventureSettingsPacket.isAutoJump = this.get(AdventureSettings.Type.AUTO_JUMP)
        updateAdventureSettingsPacket.isImmutableWorld = this.get(AdventureSettings.Type.WORLD_IMMUTABLE)
        updateAdventureSettingsPacket.isNoMvP = this.get(AdventureSettings.Type.NO_MVP)
        updateAdventureSettingsPacket.isNoPvM = this.get(AdventureSettings.Type.NO_PVM)
        updateAdventureSettingsPacket.isShowNameTags = this.get(AdventureSettings.Type.SHOW_NAME_TAGS)
        this.player.sendPacket(updateAdventureSettingsPacket)
    }

    class TypeData(val ability: Ability? = null, var defaultValue: Boolean)
}