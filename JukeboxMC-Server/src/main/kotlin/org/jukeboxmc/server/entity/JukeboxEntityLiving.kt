package org.jukeboxmc.server.entity

import org.apache.commons.math3.util.FastMath
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes
import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType
import org.cloudburstmc.protocol.bedrock.packet.EntityEventPacket
import org.cloudburstmc.protocol.bedrock.packet.MobEffectPacket
import org.cloudburstmc.protocol.bedrock.packet.UpdateAttributesPacket
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.effect.Effect
import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.entity.EntityLiving
import org.jukeboxmc.api.event.entity.*
import org.jukeboxmc.api.event.player.PlayerInteractEvent
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.effect.JukeboxEffect
import org.jukeboxmc.server.player.JukeboxPlayer
import kotlin.math.max

open class JukeboxEntityLiving : JukeboxEntity(), EntityLiving {

    private val attributes: MutableMap<Attribute, JukeboxAttribute> = mutableMapOf()
    private val effects: MutableMap<EffectType, JukeboxEffect> = mutableMapOf()

    private var highestPosition = 0F
    private var fallDistance = 0F
    private var inAirTicks = 0L

    private var deadTimer = 0
    private var lastDamage = 0f
    private var attackCoolDown = 0
    private var lastDamageEntity: JukeboxEntity? = null
    private var lastDamageSource: EntityDamageEvent.DamageSource = EntityDamageEvent.DamageSource.COMMAND

    init {
        this.addAttribute(Attribute.HEALTH)
        this.addAttribute(Attribute.ABSORPTION)
        this.addAttribute(Attribute.ATTACK_DAMAGE)
        this.addAttribute(Attribute.FOLLOW_RANGE)
        this.addAttribute(Attribute.MOVEMENT)
        this.addAttribute(Attribute.KNOCKBACK_RESISTANCE)
    }

    override fun tick(currentTick: Long) {
        super.tick(currentTick)
        if (!this.isOnGround() && !this.isOnLadder()) {
            ++this.inAirTicks
            if (this.inAirTicks > 5) {
                if (this.getLocation().getY() > this.highestPosition) {
                    this.highestPosition = this.getLocation().getY()
                }
            }
        } else {
            if (this.inAirTicks > 0) {
                this.inAirTicks = 0
            }
            this.fallDistance = (this.highestPosition - this.getLocation().getY())
            if (this.fallDistance > 0) {
                this.handleFall()
                this.highestPosition = this.getLocation().getY()
                this.fallDistance = 0F
            }
        }

        if (this.lastDamageEntity != null && this.lastDamageEntity!!.isDead()) {
            this.lastDamageEntity = null
        }
        if (this.getHealth() < 1) {
            if (this.deadTimer > 0 && this.deadTimer-- > 1) {
                this.despawn()
                this.setDead(true)
                this.deadTimer = 0
            }
        } else {
            this.deadTimer = 0
        }
        if (this.isDead() || this.getHealth() <= 0) {
            return
        }
        if (this.attackCoolDown > 0) {
            this.attackCoolDown--
        }

        if (this.isOnLadder()) {
            this.fallDistance = 0F
        }

        if (this.getFireTicks() > 0) {
            if (this.getFireTicks() % 20 == 0L) {
                if (!this.hasEffect(EffectType.FIRE_RESISTANCE)) {
                    this.damage(EntityDamageEvent(this, 1f, EntityDamageEvent.DamageSource.ON_FIRE))
                }
            }
            this.setFireTick(this.getFireTicks() - 1)
            if (this.getFireTicks() == 0L) {
                this.setOnFire(false)
            }
        }

        if (this.effects.isNotEmpty()) {
            for (effect in this.effects.values) {
                effect.update(currentTick)
                if (effect.canExecute()) {
                    effect.apply(this)
                }
                effect.setDuration(effect.getDuration() - 1)
                if (effect.getDuration() <= 0) {
                    this.removeEffect(effect.getEffectType())
                }
            }
        }
    }

    fun addAttribute(attribute: Attribute) {
        this.attributes[attribute] = attribute.getJukeboxAttribute()
    }

    fun getAttribute(attribute: Attribute): JukeboxAttribute {
        return this.attributes[attribute]!!
    }

    fun setAttributes(attribute: Attribute, value: Float) {
        this.attributes[attribute]?.setCurrentValue(value)
    }

    fun sendAttribute(attribute: Attribute) {
        val updateAttributesPacket = UpdateAttributesPacket()
        updateAttributesPacket.runtimeEntityId = this.getEntityId()
        updateAttributesPacket.attributes.add(this.attributes[attribute]!!.toNetwork())
        updateAttributesPacket.tick = JukeboxServer.getInstance().getCurrentTick()
        JukeboxServer.getInstance().broadcastPacket(updateAttributesPacket)
    }

    fun getAttributeValue(attribute: Attribute): Float {
        return this.getAttribute(attribute).getCurrentValue()
    }

    fun getAttributes(): Collection<JukeboxAttribute> {
        return this.attributes.values
    }

    override fun getHealth(): Float {
        return this.getAttributeValue(Attribute.HEALTH)
    }

    override fun setHealth(value: Float) {
        var health = value
        if (health < 1) {
            health = 0F
            this.handleKill()
        }
        this.setAttributes(Attribute.HEALTH, health)
    }

    override fun getMaxHealth(): Float {
        return this.getAttribute(Attribute.HEALTH).getMaxValue()
    }

    override fun setMaxHealth(value: Float) {
        this.getAttribute(Attribute.HEALTH).setMaxValue(value)
    }

    override fun getAbsorption(): Float {
        return this.getAttributeValue(Attribute.ABSORPTION)
    }

    override fun setAbsorption(value: Float) {
        this.setAttributes(Attribute.ABSORPTION, value)
    }

    override fun getAttackDamage(): Float {
        return this.getAttributeValue(Attribute.ATTACK_DAMAGE)
    }

    override fun setAttackDamage(value: Float) {
        this.setAttributes(Attribute.ATTACK_DAMAGE, value)
    }

    override fun getMovement(): Float {
        return this.getAttributeValue(Attribute.MOVEMENT)
    }

    override fun setMovement(value: Float) {
        this.setAttributes(Attribute.MOVEMENT, value)
    }

    override fun heal(value: Float) {
        this.setHealth(this.getMaxHealth().coerceAtMost(0f.coerceAtLeast(value)))
    }

    override fun getInAirTicks(): Long {
        return this.inAirTicks
    }

    override fun setInAirTicks(value: Long) {
        this.inAirTicks = value
    }

    override fun getFallingDistance(): Float {
        return this.fallDistance
    }

    override fun setFallingDistance(value: Float) {
        this.fallDistance = value
    }

    override fun getHighestPosition(): Float {
        return this.highestPosition
    }

    override fun setHighestPosition(value: Float) {
        this.highestPosition = value
    }

    override fun addEffect(effect: Effect) {
        val addEffectEvent = EntityAddEffectEvent(this, effect)
        JukeboxServer.getInstance().getPluginManager().callEvent(addEffectEvent)
        if (addEffectEvent.isCancelled()) return
        val oldEffect = this.getEffect(addEffectEvent.getEffect().getEffectType())

        (addEffectEvent.getEffect() as JukeboxEffect).apply(this)

        if (this is JukeboxPlayer) {
            val mobEffectPacket = MobEffectPacket()
            mobEffectPacket.runtimeEntityId = this.getEntityId()
            mobEffectPacket.effectId = addEffectEvent.getEffect().getId()
            mobEffectPacket.amplifier = addEffectEvent.getEffect().getAmplifier()
            mobEffectPacket.isParticles = addEffectEvent.getEffect().isVisible()
            mobEffectPacket.duration = addEffectEvent.getEffect().getDuration()
            if (oldEffect != null) {
                mobEffectPacket.event = MobEffectPacket.Event.MODIFY
            } else {
                mobEffectPacket.event = MobEffectPacket.Event.ADD
            }
            this.sendPacket(mobEffectPacket)
        }
        this.effects[addEffectEvent.getEffect().getEffectType()] = (addEffectEvent.getEffect() as JukeboxEffect)
        this.calculateEffectColor()
    }

    override fun removeEffect(effectType: EffectType) {
        if (this.effects.containsKey(effectType)) {
            val effect: Effect? = effects[effectType]
            val removeEffectEvent = EntityRemoveEffectEvent(this, effect!!)
            JukeboxServer.getInstance().getPluginManager().callEvent(removeEffectEvent)
            if (removeEffectEvent.isCancelled()) return
            this.effects.remove(effectType)
            (effect as JukeboxEffect).remove(this)
            if (this is JukeboxPlayer) {
                val mobEffectPacket = MobEffectPacket()
                mobEffectPacket.runtimeEntityId = this.getEntityId()
                mobEffectPacket.event = MobEffectPacket.Event.REMOVE
                mobEffectPacket.effectId = effect.getId()
                this.sendPacket(mobEffectPacket)
            }
            this.calculateEffectColor()
        }
    }

    override fun removeAllEffects() {
        for (effectType in this.effects.keys) {
            this.removeEffect(effectType)
        }
    }

    override fun getEffect(effectType: EffectType): Effect? {
        return this.effects[effectType]
    }

    override fun hasEffect(effectType: EffectType): Boolean {
        return this.effects.containsKey(effectType)
    }

    fun getDeadTimer(): Int {
        return this.deadTimer
    }

    fun setDeadTimer(deadTimer: Int) {
        this.deadTimer = deadTimer
    }

    fun getLastDamage(): Float {
        return this.lastDamage
    }

    fun setLastDamage(lastDamage: Float) {
        this.lastDamage = lastDamage
    }

    fun getAttackCooldown(): Int {
        return this.attackCoolDown
    }

    fun setAttackCooldown(attackCoolDown: Int) {
        this.attackCoolDown = attackCoolDown
    }

    fun getLastDamageSource(): EntityDamageEvent.DamageSource {
        return this.lastDamageSource
    }

    fun setLastDamageSource(damageSource: EntityDamageEvent.DamageSource) {
        this.lastDamageSource = damageSource
    }

    fun getLastDamageEntity(): Entity? {
        return this.lastDamageEntity
    }

    fun setLastDamageEntity(entity: JukeboxEntity?) {
        this.lastDamageEntity = entity
    }

    override fun kill() {
        this.damage(EntityDamageEvent(this, this.getMaxHealth(), EntityDamageEvent.DamageSource.COMMAND))
    }

    private fun calculateEffectColor() {
        val color = IntArray(3)
        var count = 0
        for (effect in this.effects.values) {
            if (effect.isVisible()) {
                val c: IntArray = effect.getColor()
                color[0] += c[0] * (effect.getAmplifier() + 1)
                color[1] += c[1] * (effect.getAmplifier() + 1)
                color[2] += c[2] * (effect.getAmplifier() + 1)
                count += effect.getAmplifier() + 1
            }
        }
        if (count > 0) {
            val r = color[0] / count and 0xff
            val g = color[1] / count and 0xff
            val b = color[2] / count and 0xff
            this.updateMetadata(this.getMetadata().setInt(EntityDataTypes.EFFECT_COLOR, (r shl 16) + (g shl 8) + b))
            this.updateMetadata(this.getMetadata().setByte(EntityDataTypes.EFFECT_AMBIENCE, 0.toByte()))
        } else {
            this.updateMetadata(this.getMetadata().setInt(EntityDataTypes.EFFECT_COLOR, 0))
            this.updateMetadata(this.getMetadata().setByte(EntityDataTypes.EFFECT_AMBIENCE, 0.toByte()))
        }
    }

    override fun heal(value: Int, cause: EntityHealEvent.Cause) {
        val entityHealEvent = EntityHealEvent(this, this.getHealth().toInt() + value, cause)
        JukeboxServer.getInstance().getPluginManager().callEvent(entityHealEvent)
        if (entityHealEvent.isCancelled()) return
        this.heal(20.coerceAtMost(0.coerceAtLeast(entityHealEvent.getHeal())).toFloat())
    }

    override fun damage(event: EntityDamageEvent): Boolean {
        if (this.getHealth() <= 0) {
            return false
        }

        if (this.hasEffect(EffectType.FIRE_RESISTANCE) &&
            (event.getDamageSource() == EntityDamageEvent.DamageSource.FIRE ||
                    event.getDamageSource() == EntityDamageEvent.DamageSource.ON_FIRE ||
                    event.getDamageSource() == EntityDamageEvent.DamageSource.LAVA)
        ) {
            return false
        }

        var damage = this.applyArmorReduction(event, false)
        damage = this.applyFeatherFallingReduction(event, damage)
        damage = this.applyProtectionReduction(event, damage)
        damage = this.applyProjectileProtectionReduction(event, damage)
        damage = this.applyFireProtectionReduction(event, damage)
        damage = this.applyResistanceEffectReduction(event, damage)
        if (damage < 0) damage = 0F

        var absorption = this.getAbsorption()
        if (absorption > 0) {
            damage = max(damage - absorption, 0f)
        }

        if (this.attackCoolDown > 0 && damage <= this.lastDamage) {
            return false
        }
        event.setFinalDamage(damage)
        if (!super.damage(event)) {
            return false
        }

        var damageToBeDealt: Float
        if (damage != event.getFinalDamage()) {
            damageToBeDealt = event.getFinalDamage()
        } else {
            damageToBeDealt = this.applyArmorReduction(event, true)
            damageToBeDealt = this.applyFeatherFallingReduction(event, damageToBeDealt)
            damageToBeDealt = this.applyProtectionReduction(event, damageToBeDealt)
            damageToBeDealt = this.applyProjectileProtectionReduction(event, damageToBeDealt)
            damageToBeDealt = this.applyFireProtectionReduction(event, damageToBeDealt)
            damageToBeDealt = this.applyResistanceEffectReduction(event, damageToBeDealt)
            absorption = this.getAbsorption()
            if (absorption > 0) {
                val oldDamage = damageToBeDealt
                damageToBeDealt = max(damage - absorption, 0f)
                this.setAbsorption(absorption - (oldDamage - damageToBeDealt))
            }
        }
      //  if (damageToBeDealt < 0) damageToBeDealt = 0F

        val health = this.getHealth() - damageToBeDealt
        if (health > 0) {
            val entityEventPacket = EntityEventPacket()
            entityEventPacket.runtimeEntityId = this.getEntityId()
            entityEventPacket.type = EntityEventType.HURT
            JukeboxServer.getInstance().broadcastPacket(entityEventPacket)
        }

        this.lastDamage = damage
        this.lastDamageSource = event.getDamageSource()
        this.lastDamageEntity = if (event is EntityDamageByEntityEvent) event.getDamager() as JukeboxEntity else null
        this.attackCoolDown = 10
        this.setHealth(if (health <= 0F) 0F else health)
        return true
    }

    open fun applyArmorReduction(event: EntityDamageEvent, damageArmor: Boolean): Float {
        return event.getDamage()
    }

    open fun applyFeatherFallingReduction(event: EntityDamageEvent, damage: Float): Float {
        return 0F
    }

    open fun applyProtectionReduction(event: EntityDamageEvent, damage: Float): Float {
        return 0F
    }

    open fun applyProjectileProtectionReduction(event: EntityDamageEvent, damage: Float): Float {
        return 0F
    }

    open fun applyFireProtectionReduction(event: EntityDamageEvent, damage: Float): Float {
        return 0F
    }

    open fun applyResistanceEffectReduction(event: EntityDamageEvent, damage: Float): Float {
        val entity = event.getEntity()
        if (entity is EntityLiving) {
            if (entity.hasEffect(EffectType.RESISTANCE)) {
                val amplifier= entity.getEffect(EffectType.RESISTANCE)!!.getAmplifier()
                return -(damage * 0.20f * amplifier + 1)
            }
        }
        return damage
    }

    open fun handleKill() {
        this.deadTimer = 20

        val entityEventPacket = EntityEventPacket()
        entityEventPacket.runtimeEntityId = this.getEntityId()
        entityEventPacket.type = EntityEventType.DEATH
        this.getWorld().sendChunkPacket(this.getChunkX(), this.getChunkZ(), entityEventPacket)

        this.removeAllEffects()

        this.setFireTicks(0L)
        this.setOnFire(false)
    }

    open fun handleFall() {
        var distanceReduce = 0.0f
        if (this.hasEffect(EffectType.JUMP_BOOST)) {
            val jumpAmplifier = this.getEffect(EffectType.JUMP_BOOST)!!.getAmplifier()
            if (jumpAmplifier != -1) {
                distanceReduce = (jumpAmplifier + 1).toFloat()
            }
        }
        val damage = FastMath.floor((fallDistance - 3f - distanceReduce).toDouble()).toFloat()
        if (damage > 0) {
            this.damage(EntityDamageEvent(this, damage, EntityDamageEvent.DamageSource.FALL))
        }

        if (fallDistance > 0.75) {
            val blockDown = this.getLocation().subtract(0F, 0.1F, 0F).getBlock()
            if (blockDown.getType() == BlockType.FARMLAND) {
                if (this is JukeboxPlayer) {
                    val playerInteractEvent = PlayerInteractEvent(
                        this,
                        PlayerInteractEvent.Action.PHYSICAL,
                        this.getInventory().getItemInHand(),
                        blockDown
                    )
                    JukeboxServer.getInstance().getPluginManager().callEvent(playerInteractEvent)
                    if (!playerInteractEvent.isCancelled()) {
                        this.getLocation().getWorld().setBlock(blockDown.getLocation(), Block.create(BlockType.DIRT))
                    }
                } else {
                    this.getLocation().getWorld().setBlock(blockDown.getLocation(), Block.create(BlockType.DIRT))
                }
            }
        }
    }

    fun resetFallDistance() {
        this.fallDistance = 0F
    }

}