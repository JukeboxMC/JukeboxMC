package org.jukeboxmc.item.enchantment;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EnchantmentRegistry {

    private final static Map<EnchantmentType, Class<? extends Enchantment>> ENCHANTMENTS = new HashMap<>();
    private final static Map<Short, EnchantmentType> ENCHANTMENT_BY_ID = new HashMap<>();

    public static void init() {
        register( EnchantmentType.AQUA_AFFINITY, EnchantmentAquaAffinity.class );
        register( EnchantmentType.BANE_OF_ARTHROPODS, EnchantmentBaneOfArthropods.class );
        register( EnchantmentType.BLAST_PROTECTION, EnchantmentBlastProtection.class );
        register( EnchantmentType.CHANNELING, EnchantmentChanneling.class );
        register( EnchantmentType.CURSE_OF_BINDING, EnchantmentCurseOfBinding.class );
        register( EnchantmentType.CURSE_OF_VANISHING, EnchantmentCurseOfVanishing.class );
        register( EnchantmentType.DEPTH_STRIDER, EnchantmentDepthStrider.class );
        register( EnchantmentType.EFFICIENCY, EnchantmentEfficiency.class );
        register( EnchantmentType.FEATHER_FALLING, EnchantmentFeatherFalling.class );
        register( EnchantmentType.FIRE_ASPECT, EnchantmentFireAspect.class );
        register( EnchantmentType.FIRE_PROTECTION, EnchantmentFireProtection.class );
        register( EnchantmentType.FLAME, EnchantmentFlame.class );
        register( EnchantmentType.FORTUNE, EnchantmentFortune.class );
        register( EnchantmentType.IMPALING, EnchantmentImpaling.class );
        register( EnchantmentType.INFINITY, EnchantmentInfinity.class );
        register( EnchantmentType.KNOCKBACK, EnchantmentKnockback.class );
        register( EnchantmentType.LOOTING, EnchantmentLooting.class );
        register( EnchantmentType.LOYALTY, EnchantmentLoyalty.class );
        register( EnchantmentType.LUCK_OF_THE_SEA, EnchantmentLuckOfTheSea.class );
        register( EnchantmentType.LURE, EnchantmentLure.class );
        register( EnchantmentType.MENDING, EnchantmentMending.class );
        register( EnchantmentType.MULTISHOT, EnchantmentMultishot.class );
        register( EnchantmentType.PIERCING, EnchantmentPiercing.class );
        register( EnchantmentType.POWER, EnchantmentPower.class );
        register( EnchantmentType.PROJECTILE_PROTECTION, EnchantmentProjectileProtection.class );
        register( EnchantmentType.PROTECTION, EnchantmentProtection.class );
        register( EnchantmentType.PUNCH, EnchantmentPunch.class );
        register( EnchantmentType.QUICK_CHARGE, EnchantmentQuickCharge.class );
        register( EnchantmentType.RESPIRATION, EnchantmentRespiration.class );
        register( EnchantmentType.RIPTIDE, EnchantmentRiptide.class );
        register( EnchantmentType.SHARPNESS, EnchantmentSharpness.class );
        register( EnchantmentType.SILK_TOUCH, EnchantmentSilkTouch.class );
        register( EnchantmentType.SMITE, EnchantmentSmite.class );
        register( EnchantmentType.SOUL_SPEED, EnchantmentSoulSpeed.class );
        register( EnchantmentType.THORNS, EnchantmentThorns.class );
        register( EnchantmentType.UNBREAKING, EnchantmentUnbreaking.class );
        register( EnchantmentType.SWIFT_SNEAK, EnchantmentSwiftSneak.class );
    }

    private static void register( EnchantmentType enchantmentType, Class<? extends Enchantment> enchantmentClass ) {
        ENCHANTMENTS.put( enchantmentType, enchantmentClass );
        try {
            Enchantment enchantment = enchantmentClass.getConstructor().newInstance();
            ENCHANTMENT_BY_ID.put( enchantment.getId(), enchantmentType );
        } catch ( InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e ) {
            throw new RuntimeException( e );
        }
    }

    public static EnchantmentType getEnchantmentType( short id ) {
        return ENCHANTMENT_BY_ID.get( id );
    }

    public static Class<? extends Enchantment> getEnchantmentClass( EnchantmentType enchantmentType ) {
        return ENCHANTMENTS.get( enchantmentType );
    }

}
