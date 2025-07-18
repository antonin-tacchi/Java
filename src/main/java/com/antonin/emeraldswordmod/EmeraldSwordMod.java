package com.antonin.emeraldswordmod;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(EmeraldSwordMod.MODID)
public class EmeraldSwordMod {

    public static final String MODID = "emeraldswordmod";
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    // ================== CLASSE HELPER POUR LES TIERS ==================

    private static class CustomTier implements Tier {
        private final int uses, level, enchantmentValue;
        private final float speed, attackDamageBonus;
        private final Ingredient repairIngredient;

        public CustomTier(int uses, float speed, float attackDamageBonus, int level, int enchantmentValue, Item repairItem) {
            this.uses = uses;
            this.speed = speed;
            this.attackDamageBonus = attackDamageBonus;
            this.level = level;
            this.enchantmentValue = enchantmentValue;
            this.repairIngredient = Ingredient.of(repairItem);
        }

        @Override public int getUses() { return uses; }
        @Override public float getSpeed() { return speed; }
        @Override public float getAttackDamageBonus() { return attackDamageBonus; }
        @Override public int getLevel() { return level; }
        @Override public int getEnchantmentValue() { return enchantmentValue; }
        @Override public Ingredient getRepairIngredient() { return repairIngredient; }
    }

    // ================== TIERS ÉQUILIBRÉS ==================

    // Emerald
    public static final Tier EMERALD_TIER = new CustomTier(2000, 8.0f, 4.0f, 3, 15, net.minecraft.world.item.Items.EMERALD);

    // Redstone
    public static final Tier REDSTONE_TIER = new CustomTier(600, 10.0f, 2.5f, 2, 22, net.minecraft.world.item.Items.REDSTONE);

    // Lapis
    public static final Tier LAPIS_TIER = new CustomTier(1200, 7.0f, 3.0f, 2, 25, net.minecraft.world.item.Items.LAPIS_LAZULI);

    // Quartz
    public static final Tier QUARTZ_TIER = new CustomTier(1000, 8.0f, 3.5f, 2, 18, net.minecraft.world.item.Items.QUARTZ);

    // Apple
    public static final Tier APPLE_TIER = new CustomTier(800, 6.0f, 2.0f, 1, 12, net.minecraft.world.item.Items.APPLE);

    // Stick
    public static final Tier STICK_TIER = new CustomTier(50, 8.0f, 1.0f, 1, 30, net.minecraft.world.item.Items.STICK);

    // Netherrack
    public static final Tier NETHERRACK_TIER = new CustomTier(900, 7.0f, 3.0f, 2, 16, net.minecraft.world.item.Items.NETHERRACK);

    // Amethyst
    public static final Tier AMETHYST_TIER = new CustomTier(1800, 9.0f, 4.5f, 3, 20, net.minecraft.world.item.Items.AMETHYST_SHARD);

    // Glowstone
    public static final Tier GLOWSTONE_TIER = new CustomTier(700, 8.0f, 2.5f, 2, 20, net.minecraft.world.item.Items.GLOWSTONE_DUST);

    // ================== ÉPÉES AVEC EFFETS SPÉCIAUX ==================

    // Épée d'Émeraude
    public static final RegistryObject<SwordItem> EMERALD_SWORD = ITEMS.register("emerald_sword",
            () -> new SwordItem(EMERALD_TIER, 2, -2.4f, new Item.Properties()) {
                @Override
                public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    if (attacker instanceof Player player) {
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 0));
                    }
                    return super.hurtEnemy(stack, target, attacker);
                }
            });

    // Épée de Redstone
    public static final RegistryObject<SwordItem> REDSTONE_SWORD = ITEMS.register("redstone_sword",
            () -> new SwordItem(REDSTONE_TIER, 1, -2.0f, new Item.Properties()) {
                @Override
                public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    if (attacker instanceof Player player) {
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 1));
                        // Particules électriques
                        Level level = player.level();
                        if (level instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                                    target.getX(), target.getY() + 1, target.getZ(),
                                    5, 0.5, 0.5, 0.5, 0.1);
                        }
                    }
                    return super.hurtEnemy(stack, target, attacker);
                }
            });

    // Épée de Lapis
    public static final RegistryObject<SwordItem> LAPIS_SWORD = ITEMS.register("lapis_sword",
            () -> new SwordItem(LAPIS_TIER, 2, -2.2f, new Item.Properties()) {
                @Override
                public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    if (Math.random() < 0.3) {
                        target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 0));
                    }
                    if (attacker instanceof Player player && Math.random() < 0.2) {
                        player.addEffect(new MobEffectInstance(MobEffects.LUCK, 200, 0));
                    }
                    return super.hurtEnemy(stack, target, attacker);
                }
            });

    // Épée de Quartz
    public static final RegistryObject<SwordItem> QUARTZ_SWORD = ITEMS.register("quartz_sword",
            () -> new SwordItem(QUARTZ_TIER, 2, -2.3f, new Item.Properties()) {
                @Override
                public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    // Knockback renforcé
                    target.knockback(1.5, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
                    return super.hurtEnemy(stack, target, attacker);
                }
            });

    // Épée de Pomme
    public static final RegistryObject<SwordItem> APPLE_SWORD = ITEMS.register("apple_sword",
            () -> new SwordItem(APPLE_TIER, 3, -2.8f, new Item.Properties()) {
                @Override
                public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    if (attacker instanceof Player player) {
                        player.heal(1.0f);
                        player.getFoodData().eat(1, 0.2f);
                    }
                    return super.hurtEnemy(stack, target, attacker);
                }
            });

    // Épée de Bâton
    public static final RegistryObject<SwordItem> STICK_SWORD = ITEMS.register("stick_sword",
            () -> new SwordItem(STICK_TIER, 2, -1.5f, new Item.Properties()));

    // Épée de Netherrack
    public static final RegistryObject<SwordItem> NETHERRACK_SWORD = ITEMS.register("netherrack_sword",
            () -> new SwordItem(NETHERRACK_TIER, 2, -2.4f, new Item.Properties()) {
                @Override
                public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    target.setSecondsOnFire(5);
                    Level level = attacker.level();
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.FLAME,
                                target.getX(), target.getY() + 1, target.getZ(),
                                8, 0.5, 0.5, 0.5, 0.1);
                    }
                    return super.hurtEnemy(stack, target, attacker);
                }
            });

    // Épée d'Améthyste
    public static final RegistryObject<SwordItem> AMETHYST_SWORD = ITEMS.register("amethyst_sword",
            () -> new SwordItem(AMETHYST_TIER, 1, -2.3f, new Item.Properties()) {
                @Override
                public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    if (attacker instanceof Player player) {
                        player.level().playSound(null, player.blockPosition(),
                                SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0f, 1.0f);
                        // Effet de résonance : dégâts de zone
                        player.level().getNearbyEntities(LivingEntity.class,
                                        net.minecraft.world.entity.ai.targeting.TargetingConditions.DEFAULT,
                                        player, player.getBoundingBox().inflate(3.0))
                                .forEach(entity -> {
                                    if (entity != player && entity != target) {
                                        entity.hurt(player.damageSources().playerAttack(player), 2.0f);
                                    }
                                });
                    }
                    return super.hurtEnemy(stack, target, attacker);
                }
            });

    // Épée de Glowstone
    public static final RegistryObject<SwordItem> GLOWSTONE_SWORD = ITEMS.register("glowstone_sword",
            () -> new SwordItem(GLOWSTONE_TIER, 2, -2.1f, new Item.Properties()) {
                @Override
                public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    if (attacker instanceof Player player) {
                        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 0));
                        Level level = player.level();
                        if (level instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(ParticleTypes.END_ROD,
                                    target.getX(), target.getY() + 1, target.getZ(),
                                    6, 0.5, 0.5, 0.5, 0.1);
                        }
                    }
                    return super.hurtEnemy(stack, target, attacker);
                }
            });

    // ================== TABLEAU DES ÉPÉES ==================

    private static final RegistryObject<SwordItem>[] SWORDS = new RegistryObject[]{
            EMERALD_SWORD, REDSTONE_SWORD, LAPIS_SWORD, QUARTZ_SWORD, APPLE_SWORD,
            STICK_SWORD, NETHERRACK_SWORD, AMETHYST_SWORD, GLOWSTONE_SWORD
    };

    // ================== CONSTRUCTEUR ET ÉVÉNEMENTS ==================

    public EmeraldSwordMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        modEventBus.addListener(this::addCreative);
        System.out.println("Emerald Sword Mod loaded for 1.20.1!");
    }

    @SubscribeEvent
    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            for (RegistryObject<SwordItem> sword : SWORDS) {
                event.accept(sword);
            }
        }
    }
}