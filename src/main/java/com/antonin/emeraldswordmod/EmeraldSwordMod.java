package com.antonin.emeraldswordmod;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
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

    // Classe helper pour créer des tiers personnalisés
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

    // Définition des tiers avec leurs statistiques
    public static final Tier EMERALD_TIER = new CustomTier(3000, 9.0f, 3.5f, 4, 20, net.minecraft.world.item.Items.EMERALD);
    public static final Tier REDSTONE_TIER = new CustomTier(800, 12.0f, 2.0f, 2, 25, net.minecraft.world.item.Items.REDSTONE);
    public static final Tier LAPIS_TIER = new CustomTier(1800, 12.0f, 15.0f, 2, 25, net.minecraft.world.item.Items.LAPIS_LAZULI);
    public static final Tier QUARTZ_TIER = new CustomTier(1800, 10.0f, 12.0f, 2, 25, net.minecraft.world.item.Items.QUARTZ);
    public static final Tier APPLE_TIER = new CustomTier(1800, 10.0f, 12.0f, 2, 25, net.minecraft.world.item.Items.APPLE);
    public static final Tier STICK_TIER = new CustomTier(10, 10.0f, 3.0f, 2, 25, net.minecraft.world.item.Items.STICK);
    public static final Tier NETHERRACK_TIER = new CustomTier(2000, 10.0f, 30.0f, 2, 25, net.minecraft.world.item.Items.NETHERRACK);
    public static final Tier AMETHYST_TIER = new CustomTier(2800, 10.0f, 50.0f, 4, 25, net.minecraft.world.item.Items.AMETHYST_SHARD);
    public static final Tier GLOWSTONE_TIER = new CustomTier(1700, 10.0f, 11.0f, 2, 25, net.minecraft.world.item.Items.GLOWSTONE_DUST);

    // Enregistrement des épées
    public static final RegistryObject<SwordItem> EMERALD_SWORD = ITEMS.register("emerald_sword", () -> new SwordItem(EMERALD_TIER, 2, -2.4f, new Item.Properties()));
    public static final RegistryObject<SwordItem> REDSTONE_SWORD = ITEMS.register("redstone_sword", () -> new SwordItem(REDSTONE_TIER, 3, -2.0f, new Item.Properties()));
    public static final RegistryObject<SwordItem> LAPIS_SWORD = ITEMS.register("lapis_sword", () -> new SwordItem(LAPIS_TIER, 13, 2.0f, new Item.Properties()));
    public static final RegistryObject<SwordItem> QUARTZ_SWORD = ITEMS.register("quartz_sword", () -> new SwordItem(QUARTZ_TIER, 14, 5f, new Item.Properties()));
    public static final RegistryObject<SwordItem> APPLE_SWORD = ITEMS.register("apple_sword", () -> new SwordItem(APPLE_TIER, 1000, 500f, new Item.Properties()));
    public static final RegistryObject<SwordItem> STICK_SWORD = ITEMS.register("stick_sword", () -> new SwordItem(STICK_TIER, 1, 1f, new Item.Properties()));
    public static final RegistryObject<SwordItem> NETHERRACK_SWORD = ITEMS.register("netherrack_sword", () -> new SwordItem(NETHERRACK_TIER, 1, 1f, new Item.Properties()));
    public static final RegistryObject<SwordItem> AMETHYST_SWORD = ITEMS.register("amethyst_sword", () -> new SwordItem(AMETHYST_TIER, 1, 1f, new Item.Properties()));
    public static final RegistryObject<SwordItem> GLOWSTONE_SWORD = ITEMS.register("glowstone_sword", () -> new SwordItem(GLOWSTONE_TIER, 1, 1f, new Item.Properties()));

    // Tableau des épées pour faciliter l'ajout au mode créatif
    private static final RegistryObject<SwordItem>[] SWORDS = new RegistryObject[]{
            EMERALD_SWORD, REDSTONE_SWORD, LAPIS_SWORD, QUARTZ_SWORD, APPLE_SWORD, STICK_SWORD, NETHERRACK_SWORD,  AMETHYST_SWORD, GLOWSTONE_SWORD
    };

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