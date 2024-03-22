package org.jukeboxmc.server.entity.passive

import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.protocol.bedrock.data.GameType
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag
import org.cloudburstmc.protocol.bedrock.packet.AddPlayerPacket
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket
import org.cloudburstmc.protocol.bedrock.packet.EmotePacket
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.api.entity.passive.EntityHuman
import org.jukeboxmc.api.event.entity.EntityDamageEvent
import org.jukeboxmc.api.event.entity.EntityHealEvent
import org.jukeboxmc.api.event.player.PlayerFoodLevelChangeEvent
import org.jukeboxmc.api.player.Emote
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.api.player.info.Device
import org.jukeboxmc.api.player.info.DeviceInfo
import org.jukeboxmc.api.player.info.UIProfile
import org.jukeboxmc.api.player.skin.Skin
import org.jukeboxmc.api.world.Difficulty
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.entity.Attribute
import org.jukeboxmc.server.entity.JukeboxAttribute
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.extensions.toJukeboxPlayer
import org.jukeboxmc.server.extensions.toVector3f
import org.jukeboxmc.server.inventory.JukeboxArmorInventory
import org.jukeboxmc.server.inventory.JukeboxOffHandInventory
import org.jukeboxmc.server.inventory.JukeboxPlayerInventory
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.util.Utils
import java.util.*

open class JukeboxEntityHuman : JukeboxEntityLiving(), EntityHuman {

    private var skin: Skin? = null
    private val uuid: UUID = UUID.randomUUID()
    private var foodTicks: Long = 0
    private var actionStart: Long = -1
    private var gameMode: GameMode = JukeboxServer.getInstance().getDefaultGameMode()
    private val deviceInfo: DeviceInfo = DeviceInfo("", "", Random().nextLong(), Device.UNKNOWN, UIProfile.CLASSIC)

    private lateinit var playerInventory: JukeboxPlayerInventory
    private lateinit var armorInventory: JukeboxArmorInventory
    private lateinit var offHandInventory: JukeboxOffHandInventory

    init {
        this.initInventory()
        this.getMetadata().setFlag(EntityFlag.CAN_SHOW_NAME, true)
        this.getMetadata().setByte(EntityDataTypes.NAMETAG_ALWAYS_SHOW, 1)
        this.setOnGround(true)

        this.addAttribute(Attribute.PLAYER_HUNGER)
        this.addAttribute(Attribute.PLAYER_SATURATION)
        this.addAttribute(Attribute.PLAYER_EXHAUSTION)
        this.addAttribute(Attribute.PLAYER_EXPERIENCE)
        this.addAttribute(Attribute.PLAYER_LEVEL)
    }

    override fun tick(currentTick: Long) {
        super.tick(currentTick)

        if (!this.isDead()) {
            val hungerAttribute = this.getAttribute(Attribute.PLAYER_HUNGER)
            val hunger: Float = hungerAttribute.getCurrentValue()
            var health = -1f
            val difficulty = this.getWorld().getDifficulty()
            if (difficulty == Difficulty.PEACEFUL && this.getFoodTicks() % 10 == 0L) {
                if (hunger < hungerAttribute.getMaxValue()) {
                    this.addHunger(1)
                }
                if (this.getFoodTicks() % 20 == 0L) {
                    health = this.getHealth()
                    if (health < this.getAttribute(Attribute.HEALTH).getMaxValue()) {
                        this.heal(1, EntityHealEvent.Cause.SATURATION)
                    }
                }
            }
            if (this.getFoodTicks() == 0L) {
                if (hunger >= 18) {
                    if (health == -1f) {
                        health = this.getHealth()
                    }
                    if (health < 20) {
                        this.heal(1, EntityHealEvent.Cause.SATURATION)
                        if (this.getGameMode() == GameMode.SURVIVAL) {
                            this.exhaust(3F)
                        }
                    }
                } else if (hunger <= 0) {
                    if (health == -1f) {
                        health = this.getHealth()
                    }
                    if (difficulty == Difficulty.NORMAL && health > 2 || difficulty == Difficulty.HARD && health > 1) {
                        this.damage(EntityDamageEvent(this, 1F, EntityDamageEvent.DamageSource.STARVE))
                    }
                }
            }
            this.addFoodTicks()
            if (this.getFoodTicks() >= 80) {
                this.setFoodTicks(0)
            }

            if (this.hasEffect(EffectType.HUNGER)) {
                this.exhaust(0.1f * (this.getEffect(EffectType.HUNGER)!!.getAmplifier() + 1))
            }
        }

        val breathing = !this.isInWater() || this.hasEffect(EffectType.WATER_BREATHING)
        if (this.getMetadata().getFlag(EntityFlag.BREATHING) != breathing && this.getGameMode() == GameMode.SURVIVAL) {
            this.updateMetadata(this.getMetadata().setFlag(EntityFlag.BREATHING, breathing))
        }

        var air = this.getMetadata().getShort(EntityDataTypes.AIR_SUPPLY)
        val maxAir = this.getMetadata().getShort(EntityDataTypes.AIR_SUPPLY_MAX)

        if (this.getGameMode() == GameMode.SURVIVAL) {
            if (!breathing) {
                if (--air < 0) {
                    if (currentTick % 20 == 0L) {
                        this.damage(EntityDamageEvent(this, 2F, EntityDamageEvent.DamageSource.DROWNING))
                    }
                } else {
                    this.updateMetadata(this.getMetadata().setShort(EntityDataTypes.AIR_SUPPLY, air))
                }
            } else {
                if (air != maxAir) {
                    this.updateMetadata(this.getMetadata().setShort(EntityDataTypes.AIR_SUPPLY, maxAir))
                }
            }
        }
    }

    fun initInventory() {
        this.playerInventory = JukeboxPlayerInventory(this)
        this.armorInventory = JukeboxArmorInventory(this)
        this.offHandInventory = JukeboxOffHandInventory(this)
    }

    override fun createSpawnPacket(): BedrockPacket {
        val addPlayerPacket = AddPlayerPacket()
        addPlayerPacket.runtimeEntityId = this.getEntityId()
        addPlayerPacket.uniqueEntityId = this.getEntityId()
        addPlayerPacket.uuid = this.getUUID()
        addPlayerPacket.username = this.getName()
        addPlayerPacket.platformChatId = this.deviceInfo.getDeviceId()
        addPlayerPacket.position = this.getLocation().toVector3f()
        addPlayerPacket.motion = this.getVelocity().toVector3f()
        addPlayerPacket.rotation =
            Vector3f.from(this.getLocation().getPitch(), this.getLocation().getYaw(), this.getLocation().getYaw())
        addPlayerPacket.gameType = GameType.CREATIVE
        addPlayerPacket.metadata.putAll(this.getMetadata().getEntityDataMap())
        addPlayerPacket.deviceId = this.deviceInfo.getDeviceId()
        addPlayerPacket.hand = this.playerInventory.getItemInHand().toJukeboxItem().toItemData()
        return addPlayerPacket
    }

    override fun spawn(player: Player): Boolean {
        val playerVal = player.toJukeboxPlayer()
        if (this != playerVal && this.skin != null) {
            if (this !is Player) {
                JukeboxServer.getInstance()
                    .addToTabList(this.getUUID(), this.getEntityId(), getName(), this.getDeviceInfo(), "", this.skin!!)
            }
            val spawned = super.spawn(player)
            this.armorInventory.sendContents(playerVal)
            if (this !is Player) {
                JukeboxServer.getInstance().removeFromTabList(this.uuid, player)
            }
            return spawned
        }
        return false
    }

    override fun spawn() {
        for (player in getWorld().getPlayers()) {
            if (getDimension() == player.getDimension()) {
                this.spawn(player)
            }
        }
    }

    override fun despawn(player: Player): Boolean {
        if (this !== player) {
            return super.despawn(player)
        }
        return false
    }

    override fun despawn() {
        for (player in getWorld().getPlayers()) {
            if (getDimension() == player.getDimension()) {
                this.despawn(player)
            }
        }
    }

    override fun getName(): String {
        return this.getNameTag()
    }

    override fun getUUID(): UUID {
        return this.uuid
    }

    override fun getDeviceInfo(): DeviceInfo {
        return this.deviceInfo
    }

    override fun getSkin(): Skin? {
        return this.skin
    }

    override fun setSkin(skin: Skin) {
        this.skin = skin
    }

    fun setSkinInternal(skin: Skin) {
        this.skin = skin
    }

    override fun getEyeHeight(): Float {
        return 1.62F
    }

    override fun getWidth(): Float {
        return 0.6F
    }

    override fun getHeight(): Float {
        return 1.8F
    }

    override fun getEntityType(): EntityType {
        return EntityType.HUMAN
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:player")
    }

    override fun isSneaking(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.SNEAKING)
    }

    override fun setSneaking(value: Boolean) {
        if (value != this.isSneaking()) {
            this.updateMetadata(this.getMetadata().setFlag(EntityFlag.SNEAKING, value))
        }
    }

    override fun isSprinting(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.SPRINTING)
    }

    override fun setSprinting(value: Boolean) {
        if (value != this.isSprinting()) {
            this.updateMetadata(this.getMetadata().setFlag(EntityFlag.SPRINTING, value))

            this.setMovement(if (value) this.getMovement() * 1.3f else this.getMovement() / 1.3f)
            if (this.hasEffect(EffectType.SPEED)) {
                val movement = getMovement()
                this.setMovement(if (value) movement * 1.3f else movement)
            }
        }
    }

    override fun isSwimming(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.SWIMMING)
    }

    override fun setSwimming(value: Boolean) {
        if (value != this.isSwimming()) {
            this.updateMetadata(this.getMetadata().setFlag(EntityFlag.SWIMMING, value))
        }
    }

    override fun isGliding(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.GLIDING)
    }

    override fun setGliding(value: Boolean) {
        if (value != this.isGliding()) {
            this.updateMetadata(this.getMetadata().setFlag(EntityFlag.GLIDING, value))
        }
    }

    override fun hasAction(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.USING_ITEM)
    }

    override fun setAction(value: Boolean) {
        if (value != this.hasAction()) {
            this.updateMetadata(this.getMetadata().setFlag(EntityFlag.USING_ITEM, value))
            this.actionStart = JukeboxServer.getInstance().getCurrentTick()
        } else {
            this.actionStart = -1
        }
    }

    fun getActionStart(): Long {
        return this.actionStart
    }

    fun getFoodTicks(): Long {
        return this.foodTicks
    }

    fun setFoodTicks(foodTicks: Long) {
        this.foodTicks = foodTicks
    }

    fun addFoodTicks() {
        this.foodTicks++
    }

    override fun isHungry(): Boolean {
        val attribute: JukeboxAttribute = this.getAttribute(Attribute.PLAYER_HUNGER)
        return attribute.getCurrentValue() < attribute.getMaxValue()
    }

    override fun getHunger(): Int {
        return getAttributeValue(Attribute.PLAYER_HUNGER).toInt()
    }

    override fun setHunger(value: Int) {
        val attribute: JukeboxAttribute = getAttribute(Attribute.PLAYER_HUNGER)
        val old: Float = attribute.getCurrentValue()
        this.setAttributes(
            Attribute.PLAYER_HUNGER,
            Utils.clamp(value, attribute.getMinValue().toInt(), attribute.getMaxValue().toInt()).toFloat()
        )
        if (old < 17 && value >= 17 || old < 6 && value >= 6 || old > 0 && value == 0) {
            this.foodTicks = 0
        }
    }

    override fun addHunger(value: Int) {
        this.setHunger(getHunger() + value)
    }

    override fun getSaturation(): Float {
        return this.getAttributeValue(Attribute.PLAYER_SATURATION)
    }

    override fun setSaturation(value: Float) {
        val attribute = getAttribute(Attribute.PLAYER_SATURATION)
        this.setAttributes(Attribute.PLAYER_SATURATION, Utils.clamp(value.toInt(), attribute.getMinValue().toInt(), attribute.getMaxValue().toInt()).toFloat())
    }

    override fun getExhaustion(): Float {
        return this.getAttributeValue(Attribute.PLAYER_EXHAUSTION)
    }

    override fun setExhaustion(value: Float) {
        this.setAttributes(Attribute.PLAYER_EXHAUSTION, value)
    }

    open fun exhaust(value: Float) {
        if (this.hasEffect(EffectType.SATURATION)) return
        var exhaustion = this.getExhaustion() + value
        while (exhaustion >= 4) {
            exhaustion -= 4f
            var saturation = this.getSaturation()
            if (saturation > 0) {
                saturation = 0f.coerceAtLeast(saturation - 1)
                this.setSaturation(saturation)
            } else {
                val hunger = this.getHunger()
                if (hunger > 0) {
                    if (this is JukeboxPlayer) {
                        val playerFoodLevelChangeEvent = PlayerFoodLevelChangeEvent(this, hunger, saturation)
                        JukeboxServer.getInstance().getPluginManager().callEvent(playerFoodLevelChangeEvent)
                        if (playerFoodLevelChangeEvent.isCancelled()) {
                            this.updateAttributes()
                            return
                        }
                        this.setHunger(0.coerceAtLeast(playerFoodLevelChangeEvent.getFoodLevel() - 1))
                    } else {
                        this.setHunger(0.coerceAtLeast(hunger - 1))
                    }
                }
            }
        }
        this.setExhaustion(exhaustion)
    }

    override fun getExperience(): Int {
        return this.getAttributeValue(Attribute.PLAYER_EXPERIENCE).toInt()
    }

    override fun setExperience(value: Int) {
        this.setAttributes(Attribute.PLAYER_EXPERIENCE, value.toFloat())
    }

    override fun getLevel(): Int {
        return this.getAttributeValue(Attribute.PLAYER_LEVEL).toInt()
    }

    override fun setLevel(value: Int) {
        this.setAttributes(Attribute.PLAYER_LEVEL, value.toFloat())
    }

    override fun getInventory(): JukeboxPlayerInventory {
        return this.playerInventory
    }

    override fun getArmorInventory(): JukeboxArmorInventory {
        return this.armorInventory
    }

    override fun getOffHandInventory(): JukeboxOffHandInventory {
        return this.offHandInventory
    }

    override fun getGameMode(): GameMode {
        return this.gameMode
    }

    override fun setGameMode(gameMode: GameMode) {
        this.gameMode = gameMode
    }

    override fun playEmote(emote: Emote) {
        val emotePacket = EmotePacket()
        emotePacket.runtimeEntityId = this.getEntityId()
        emotePacket.xuid = ""
        emotePacket.platformId = ""
        emotePacket.emoteId = emote.getUUID().toString()
        this.getWorld().sendChunkPacket(this.getChunkX(), this.getChunkZ(), emotePacket)
    }
}