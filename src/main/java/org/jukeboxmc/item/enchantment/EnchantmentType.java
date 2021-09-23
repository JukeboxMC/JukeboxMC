package org.jukeboxmc.item.enchantment;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum EnchantmentType {

    AQUA_AFFINITY( new EnchantmentAquaAffinity() ),
    BANE_OF_ARTHROPODS( new EnchantmentBaneOfArthropods() ),
    BLAST_PROTECTION( new EnchantmentBlastProtection() ),
    CHANNELING( new EnchantmentChanneling() ),
    CURSE_OF_BINDING( new EnchantmentCurseOfBinding() ),
    CURSE_OF_VANISHING( new EnchantmentCurseOfVanishing() ),
    DEPTH_STRIDER( new EnchantmentDepthStrider() ),
    EFFICIENCY( new EnchantmentEfficiency() ),
    FEATHER_FALLING( new EnchantmentFeatherFalling() ),
    FIRE_ASPECT( new EnchantmentFireAspect() ),
    FIRE_PROTECTION( new EnchantmentFireProtection() ),
    FLAME( new EnchantmentFlame() ),
    FORTUNE( new EnchantmentFortune() ),
    IMPALING( new EnchantmentImpaling() ),
    INFINITY( new EnchantmentInfinity() ),
    KNOCKBACK( new EnchantmentKnockback() ),
    LOOTING( new EnchantmentLooting() ),
    LOYALTY( new EnchantmentLoyalty() ),
    LUCK_OF_THE_SEA( new EnchantmentLuckOfTheSea() ),
    LURE( new EnchantmentLure() ),
    MENDING( new EnchantmentMending() ),
    MULTISHOT( new EnchantmentMultishot() ),
    PIERCING( new EnchantmentPiercing() ),
    POWER( new EnchantmentPower() ),
    PROJECTILE_PROTECTION( new EnchantmentProjectileProtection() ),
    PROTECTION( new EnchantmentProtection() ),
    PUNCH( new EnchantmentPunch() ),
    QUICK_CHARGE( new EnchantmentQuickCharge() ),
    RESPIRATION( new EnchantmentRespiration() ),
    RIPTIDE( new EnchantmentRiptide() ),
    SHARPNESS( new EnchantmentSharpness() ),
    SILK_TOUCH( new EnchantmentSilkTouch() ),
    SMITE( new EnchantmentSmite() ),
    SOUL_SPEED( new EnchantmentSoulSpeed() ),
    THORNS( new EnchantmentThorns() ),
    UNBREAKING( new EnchantmentUnbreaking() );

    private final Enchantment enchantment;

    EnchantmentType( Enchantment enchantment ) {
        this.enchantment = enchantment;
    }

    public Enchantment getEnchantment() {
        return this.enchantment;
    }
}
