package com.antonin.emeraldswordmod;

import net.minecraft.world.item.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.*;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.Ingredient;

import net.minecraftforge.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

public class ArmorItems {

    public static final DeferredRegister<Item> ARMOR_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EmeraldSwordMod.MODID);

    // ================== CLASSE HELPER POUR LES ARMORMATERIAL ==================

    private static class CustomArmorMaterial implements ArmorMaterial {
        private final String name;
        private final int durabilityMultiplier;
        private final int[] protectionAmounts;
        private final int enchantmentValue;
        private final SoundEvent equipSound;
        private final float toughness;
        private final float knockbackResistance;
        private final Ingredient repairIngredient;

        public CustomArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts,
                                   int enchantmentValue, SoundEvent equipSound, float toughness,
                                   float knockbackResistance, Item repairItem) {
            this.name = name;
            this.durabilityMultiplier = durabilityMultiplier;
            this.protectionAmounts = protectionAmounts;
            this.enchantmentValue = enchantmentValue;
            this.equipSound = equipSound;
            this.toughness = toughness;
            this.knockbackResistance = knockbackResistance;
            this.repairIngredient = Ingredient.of(repairItem);
        }

        @Override
        public int getDurabilityForType(ArmorItem.Type type) {
            return switch (type) {
                case HELMET -> durabilityMultiplier * 11;
                case CHESTPLATE -> durabilityMultiplier * 16;
                case LEGGINGS -> durabilityMultiplier * 15;
                case BOOTS -> durabilityMultiplier * 13;
            };
        }

        @Override
        public int getDefenseForType(ArmorItem.Type type) {
            return switch (type) {
                case HELMET -> protectionAmounts[0];
                case CHESTPLATE -> protectionAmounts[1];
                case LEGGINGS -> protectionAmounts[2];
                case BOOTS -> protectionAmounts[3];
            };
        }

        @Override public int getEnchantmentValue() { return enchantmentValue; }
        @Override public SoundEvent getEquipSound() { return equipSound; }
        @Override public Ingredient getRepairIngredient() { return repairIngredient; }
        @Override public String getName() { return name; }
        @Override public float getToughness() { return toughness; }
        @Override public float getKnockbackResistance() { return knockbackResistance; }
    }

    // ================== MATÉRIAUX D'ARMURE CORRIGÉS ==================

    // Emerald Armor
    public static final ArmorMaterial EMERALD_ARMOR_MATERIAL = new CustomArmorMaterial(
            EmeraldSwordMod.MODID + ":emerald", 37, new int[]{3, 8, 6, 3}, 15,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0f, 0.0f, Items.EMERALD);

    // Redstone Armor
    public static final ArmorMaterial REDSTONE_ARMOR_MATERIAL = new CustomArmorMaterial(
            EmeraldSwordMod.MODID + ":redstone", 20, new int[]{2, 6, 5, 2}, 22,
            SoundEvents.ARMOR_EQUIP_IRON, 1.0f, 0.0f, Items.REDSTONE);

    // Lapis Armor
    public static final ArmorMaterial LAPIS_ARMOR_MATERIAL = new CustomArmorMaterial(
            EmeraldSwordMod.MODID + ":lapis", 25, new int[]{2, 7, 5, 2}, 25,
            SoundEvents.ARMOR_EQUIP_GOLD, 1.5f, 0.0f, Items.LAPIS_LAZULI);

    // Quartz Armor
    public static final ArmorMaterial QUARTZ_ARMOR_MATERIAL = new CustomArmorMaterial(
            EmeraldSwordMod.MODID + ":quartz", 30, new int[]{3, 7, 6, 2}, 18,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 1.8f, 0.1f, Items.QUARTZ);

    // Apple Armor
    public static final ArmorMaterial APPLE_ARMOR_MATERIAL = new CustomArmorMaterial(
            EmeraldSwordMod.MODID + ":apple", 15, new int[]{1, 4, 3, 1}, 12,
            SoundEvents.ARMOR_EQUIP_LEATHER, 0.5f, 0.0f, Items.APPLE);

    // Stick Armor
    public static final ArmorMaterial STICK_ARMOR_MATERIAL = new CustomArmorMaterial(
            EmeraldSwordMod.MODID + ":stick", 8, new int[]{1, 2, 2, 1}, 30,
            SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, Items.STICK);

    // Netherrack Armor
    public static final ArmorMaterial NETHERRACK_ARMOR_MATERIAL = new CustomArmorMaterial(
            EmeraldSwordMod.MODID + ":netherrack", 22, new int[]{2, 6, 5, 2}, 16,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.2f, 0.05f, Items.NETHERRACK);

    // Amethyst Armor
    public static final ArmorMaterial AMETHYST_ARMOR_MATERIAL = new CustomArmorMaterial(
            EmeraldSwordMod.MODID + ":amethyst", 35, new int[]{3, 8, 6, 3}, 20,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 2.2f, 0.0f, Items.AMETHYST_SHARD);

    // Glowstone Armor
    public static final ArmorMaterial GLOWSTONE_ARMOR_MATERIAL = new CustomArmorMaterial(
            EmeraldSwordMod.MODID + ":glowstone", 18, new int[]{2, 5, 4, 2}, 20,
            SoundEvents.ARMOR_EQUIP_GOLD, 0.8f, 0.0f, Items.GLOWSTONE_DUST);

    // ================== ARMURES AVEC EFFETS SPÉCIAUX ==================

    // EMERALD ARMOR SET
    public static final RegistryObject<ArmorItem> EMERALD_HELMET = ARMOR_ITEMS.register("emerald_helmet",
            () -> new ArmorItem(EMERALD_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties()) {
                @Override
                public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slotId, boolean isSelected) {
                    if (entity instanceof Player player && slotId == EquipmentSlot.HEAD.getIndex() && player.tickCount % 100 == 0) {
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 0));
                    }
                    super.inventoryTick(stack, level, entity, slotId, isSelected);
                }
            });

    public static final RegistryObject<ArmorItem> EMERALD_CHESTPLATE = ARMOR_ITEMS.register("emerald_chestplate",
            () -> new ArmorItem(EMERALD_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<ArmorItem> EMERALD_LEGGINGS = ARMOR_ITEMS.register("emerald_leggings",
            () -> new ArmorItem(EMERALD_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<ArmorItem> EMERALD_BOOTS = ARMOR_ITEMS.register("emerald_boots",
            () -> new ArmorItem(EMERALD_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties()));

    // REDSTONE ARMOR SET
    public static final RegistryObject<ArmorItem> REDSTONE_HELMET = ARMOR_ITEMS.register("redstone_helmet",
            () -> new ArmorItem(REDSTONE_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<ArmorItem> REDSTONE_CHESTPLATE = ARMOR_ITEMS.register("redstone_chestplate",
            () -> new ArmorItem(REDSTONE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<ArmorItem> REDSTONE_LEGGINGS = ARMOR_ITEMS.register("redstone_leggings",
            () -> new ArmorItem(REDSTONE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<ArmorItem> REDSTONE_BOOTS = ARMOR_ITEMS.register("redstone_boots",
            () -> new ArmorItem(REDSTONE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties()) {
                @Override
                public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slotId, boolean isSelected) {
                    if (entity instanceof Player player && slotId == EquipmentSlot.FEET.getIndex() && player.tickCount % 60 == 0) {
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 80, 0));
                    }
                    super.inventoryTick(stack, level, entity, slotId, isSelected);
                }
            });

    // LAPIS ARMOR SET
    public static final RegistryObject<ArmorItem> LAPIS_HELMET = ARMOR_ITEMS.register("lapis_helmet",
            () -> new ArmorItem(LAPIS_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties()) {
                @Override
                public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slotId, boolean isSelected) {
                    if (entity instanceof Player player && slotId == EquipmentSlot.HEAD.getIndex() && player.tickCount % 200 == 0) {
                        player.addEffect(new MobEffectInstance(MobEffects.LUCK, 400, 0));
                    }
                    super.inventoryTick(stack, level, entity, slotId, isSelected);
                }
            });

    public static final RegistryObject<ArmorItem> LAPIS_CHESTPLATE = ARMOR_ITEMS.register("lapis_chestplate",
            () -> new ArmorItem(LAPIS_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<ArmorItem> LAPIS_LEGGINGS = ARMOR_ITEMS.register("lapis_leggings",
            () -> new ArmorItem(LAPIS_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<ArmorItem> LAPIS_BOOTS = ARMOR_ITEMS.register("lapis_boots",
            () -> new ArmorItem(LAPIS_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties()));

    // QUARTZ ARMOR SET
    public static final RegistryObject<ArmorItem> QUARTZ_HELMET = ARMOR_ITEMS.register("quartz_helmet",
            () -> new ArmorItem(QUARTZ_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<ArmorItem> QUARTZ_CHESTPLATE = ARMOR_ITEMS.register("quartz_chestplate",
            () -> new ArmorItem(QUARTZ_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties()) {
                @Override
                public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slotId, boolean isSelected) {
                    if (entity instanceof Player player && slotId == EquipmentSlot.CHEST.getIndex() && player.tickCount % 80 == 0) {
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 0));
                    }
                    super.inventoryTick(stack, level, entity, slotId, isSelected);
                }
            });

    public static final RegistryObject<ArmorItem> QUARTZ_LEGGINGS = ARMOR_ITEMS.register("quartz_leggings",
            () -> new ArmorItem(QUARTZ_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<ArmorItem> QUARTZ_BOOTS = ARMOR_ITEMS.register("quartz_boots",
            () -> new ArmorItem(QUARTZ_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties()));

    // APPLE ARMOR SET
    public static final RegistryObject<ArmorItem> APPLE_HELMET = ARMOR_ITEMS.register("apple_helmet",
            () -> new ArmorItem(APPLE_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<ArmorItem> APPLE_CHESTPLATE = ARMOR_ITEMS.register("apple_chestplate",
            () -> new ArmorItem(APPLE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties()) {
                @Override
                public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slotId, boolean isSelected) {
                    if (entity instanceof Player player && slotId == EquipmentSlot.CHEST.getIndex() &&
                            player.tickCount % 100 == 0 && player.getFoodData().needsFood()) {
                        player.getFoodData().eat(1, 0.1f);
                    }
                    super.inventoryTick(stack, level, entity, slotId, isSelected);
                }
            });

    public static final RegistryObject<ArmorItem> APPLE_LEGGINGS = ARMOR_ITEMS.register("apple_leggings",
            () -> new ArmorItem(APPLE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<ArmorItem> APPLE_BOOTS = ARMOR_ITEMS.register("apple_boots",
            () -> new ArmorItem(APPLE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties()));

    // STICK ARMOR SET (simple, pas d'effets)
    public static final RegistryObject<ArmorItem> STICK_HELMET = ARMOR_ITEMS.register("stick_helmet",
            () -> new ArmorItem(STICK_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<ArmorItem> STICK_CHESTPLATE = ARMOR_ITEMS.register("stick_chestplate",
            () -> new ArmorItem(STICK_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<ArmorItem> STICK_LEGGINGS = ARMOR_ITEMS.register("stick_leggings",
            () -> new ArmorItem(STICK_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<ArmorItem> STICK_BOOTS = ARMOR_ITEMS.register("stick_boots",
            () -> new ArmorItem(STICK_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties()));

    // NETHERRACK ARMOR SET
    public static final RegistryObject<ArmorItem> NETHERRACK_HELMET = ARMOR_ITEMS.register("netherrack_helmet",
            () -> new ArmorItem(NETHERRACK_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<ArmorItem> NETHERRACK_CHESTPLATE = ARMOR_ITEMS.register("netherrack_chestplate",
            () -> new ArmorItem(NETHERRACK_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties()) {
                @Override
                public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slotId, boolean isSelected) {
                    if (entity instanceof Player player && slotId == EquipmentSlot.CHEST.getIndex() && player.tickCount % 120 == 0) {
                        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0));
                    }
                    super.inventoryTick(stack, level, entity, slotId, isSelected);
                }
            });

    public static final RegistryObject<ArmorItem> NETHERRACK_LEGGINGS = ARMOR_ITEMS.register("netherrack_leggings",
            () -> new ArmorItem(NETHERRACK_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<ArmorItem> NETHERRACK_BOOTS = ARMOR_ITEMS.register("netherrack_boots",
            () -> new ArmorItem(NETHERRACK_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties()));

    // AMETHYST ARMOR SET
    public static final RegistryObject<ArmorItem> AMETHYST_HELMET = ARMOR_ITEMS.register("amethyst_helmet",
            () -> new ArmorItem(AMETHYST_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties()) {
                @Override
                public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slotId, boolean isSelected) {
                    if (entity instanceof Player player && slotId == EquipmentSlot.HEAD.getIndex() &&
                            player.tickCount % 40 == 0 && !level.isClientSide) {
                        // Son d'améthyste périodique
                        level.playSound(null, player.blockPosition(),
                                SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.3f, 2.0f);
                    }
                    super.inventoryTick(stack, level, entity, slotId, isSelected);
                }
            });

    public static final RegistryObject<ArmorItem> AMETHYST_CHESTPLATE = ARMOR_ITEMS.register("amethyst_chestplate",
            () -> new ArmorItem(AMETHYST_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<ArmorItem> AMETHYST_LEGGINGS = ARMOR_ITEMS.register("amethyst_leggings",
            () -> new ArmorItem(AMETHYST_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<ArmorItem> AMETHYST_BOOTS = ARMOR_ITEMS.register("amethyst_boots",
            () -> new ArmorItem(AMETHYST_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties()));

    // GLOWSTONE ARMOR SET
    public static final RegistryObject<ArmorItem> GLOWSTONE_HELMET = ARMOR_ITEMS.register("glowstone_helmet",
            () -> new ArmorItem(GLOWSTONE_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties()) {
                @Override
                public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slotId, boolean isSelected) {
                    if (entity instanceof Player player && slotId == EquipmentSlot.HEAD.getIndex() && player.tickCount % 100 == 0) {
                        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0));
                    }
                    super.inventoryTick(stack, level, entity, slotId, isSelected);
                }
            });

    public static final RegistryObject<ArmorItem> GLOWSTONE_CHESTPLATE = ARMOR_ITEMS.register("glowstone_chestplate",
            () -> new ArmorItem(GLOWSTONE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<ArmorItem> GLOWSTONE_LEGGINGS = ARMOR_ITEMS.register("glowstone_leggings",
            () -> new ArmorItem(GLOWSTONE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<ArmorItem> GLOWSTONE_BOOTS = ARMOR_ITEMS.register("glowstone_boots",
            () -> new ArmorItem(GLOWSTONE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties()));

    // ================== TABLEAU DES ARMURES ==================

    public static final RegistryObject<ArmorItem>[] ALL_ARMOR_ITEMS = new RegistryObject[]{
            // Emerald
            EMERALD_HELMET, EMERALD_CHESTPLATE, EMERALD_LEGGINGS, EMERALD_BOOTS,
            // Redstone
            REDSTONE_HELMET, REDSTONE_CHESTPLATE, REDSTONE_LEGGINGS, REDSTONE_BOOTS,
            // Lapis
            LAPIS_HELMET, LAPIS_CHESTPLATE, LAPIS_LEGGINGS, LAPIS_BOOTS,
            // Quartz
            QUARTZ_HELMET, QUARTZ_CHESTPLATE, QUARTZ_LEGGINGS, QUARTZ_BOOTS,
            // Apple
            APPLE_HELMET, APPLE_CHESTPLATE, APPLE_LEGGINGS, APPLE_BOOTS,
            // Stick
            STICK_HELMET, STICK_CHESTPLATE, STICK_LEGGINGS, STICK_BOOTS,
            // Netherrack
            NETHERRACK_HELMET, NETHERRACK_CHESTPLATE, NETHERRACK_LEGGINGS, NETHERRACK_BOOTS,
            // Amethyst
            AMETHYST_HELMET, AMETHYST_CHESTPLATE, AMETHYST_LEGGINGS, AMETHYST_BOOTS,
            // Glowstone
            GLOWSTONE_HELMET, GLOWSTONE_CHESTPLATE, GLOWSTONE_LEGGINGS, GLOWSTONE_BOOTS
    };
}