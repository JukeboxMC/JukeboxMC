package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Enchantment
import org.jukeboxmc.api.item.enchantment.EnchantmentType

class EnchantmentRegistry {

    companion object {

        private val enchantmentClassByType: MutableMap<EnchantmentType, Class<out Enchantment>> = mutableMapOf()
        private val enchantmentById: MutableMap<Int, EnchantmentType> = mutableMapOf()

        init {
            register(EnchantmentType.AQUA_AFFINITY, EnchantmentAquaAffinity::class.java)
            register(EnchantmentType.BANE_OF_ARTHROPODS, EnchantmentBaneOfArthropods::class.java)
            register(EnchantmentType.BLAST_PROTECTION, EnchantmentBlastProtection::class.java)
            register(EnchantmentType.CHANNELING, EnchantmentChanneling::class.java)
            register(EnchantmentType.CURSE_OF_BINDING, EnchantmentCurseOfBinding::class.java)
            register(EnchantmentType.CURSE_OF_VANISHING, EnchantmentCurseOfVanishing::class.java)
            register(EnchantmentType.DEPTH_STRIDER, EnchantmentDepthStrider::class.java)
            register(EnchantmentType.EFFICIENCY, EnchantmentEfficiency::class.java)
            register(EnchantmentType.FEATHER_FALLING, EnchantmentFeatherFalling::class.java)
            register(EnchantmentType.FIRE_ASPECT, EnchantmentFireAspect::class.java)
            register(EnchantmentType.FIRE_PROTECTION, EnchantmentFireProtection::class.java)
            register(EnchantmentType.FLAME, EnchantmentFlame::class.java)
            register(EnchantmentType.FORTUNE, EnchantmentFortune::class.java)
            register(EnchantmentType.IMPALING, EnchantmentImpaling::class.java)
            register(EnchantmentType.INFINITY, EnchantmentInfinity::class.java)
            register(EnchantmentType.KNOCKBACK, EnchantmentKnockback::class.java)
            register(EnchantmentType.LOOTING, EnchantmentLooting::class.java)
            register(EnchantmentType.LOYALTY, EnchantmentLoyalty::class.java)
            register(EnchantmentType.LUCK_OF_THE_SEA, EnchantmentLuckOfTheSea::class.java)
            register(EnchantmentType.LURE, EnchantmentLure::class.java)
            register(EnchantmentType.MENDING, EnchantmentMending::class.java)
            register(EnchantmentType.MULTISHOT, EnchantmentMultiShot::class.java)
            register(EnchantmentType.PIERCING, EnchantmentPiercing::class.java)
            register(EnchantmentType.POWER, EnchantmentPower::class.java)
            register(EnchantmentType.PROJECTILE_PROTECTION, EnchantmentProjectileProtection::class.java)
            register(EnchantmentType.PROTECTION, EnchantmentProtection::class.java)
            register(EnchantmentType.PUNCH, EnchantmentPunch::class.java)
            register(EnchantmentType.QUICK_CHARGE, EnchantmentQuickCharge::class.java)
            register(EnchantmentType.RESPIRATION, EnchantmentRespiration::class.java)
            register(EnchantmentType.RIPTIDE, EnchantmentRiptide::class.java)
            register(EnchantmentType.SHARPNESS, EnchantmentSharpness::class.java)
            register(EnchantmentType.SILK_TOUCH, EnchantmentSilkTouch::class.java)
            register(EnchantmentType.SMITE, EnchantmentSmite::class.java)
            register(EnchantmentType.SOUL_SPEED, EnchantmentSoulSpeed::class.java)
            register(EnchantmentType.THORNS, EnchantmentThorns::class.java)
            register(EnchantmentType.UNBREAKING, EnchantmentUnbreaking::class.java)
            register(EnchantmentType.SWIFT_SNEAK, EnchantmentSwiftSneak::class.java)
            register(EnchantmentType.FROST_WALKER, EnchantmentFrostWalker::class.java)
        }

        fun register(enchantmentType: EnchantmentType, enchantmentClass: Class<out Enchantment>) {
            enchantmentClassByType[enchantmentType] = enchantmentClass
            val enchantment = enchantmentClass.getConstructor().newInstance()
            enchantmentById[enchantment.getId()] = enchantmentType
        }

        fun getEnchantmentClass(enchantmentType: EnchantmentType): Class<out Enchantment> {
            return enchantmentClassByType[enchantmentType]!!
        }

        fun getEnchantmentTypeById(id: Int): EnchantmentType {
            return enchantmentById[id]!!
        }

    }

}