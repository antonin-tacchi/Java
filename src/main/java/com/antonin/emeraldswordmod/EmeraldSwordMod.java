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

    // Registre pour les items
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    // Matériau émeraude
    public static final Tier EMERALD_TIER = new Tier() {
        @Override
        public int getUses() {
            return 3000; // DURABILITÉ (bois=59, pierre=131, fer=250, diamant=1561)
        }

        @Override
        public float getSpeed() {
            return 9.0f; // VITESSE DE MINAGE (bois=2, pierre=4, fer=6, diamant=8)
        }

        @Override
        public float getAttackDamageBonus() {
            return 3.5f; // DÉGÂTS BONUS réduits (entre diamant=3 et ce qu'on avait=5)
        }

        @Override
        public int getLevel() {
            return 4; // NIVEAU DE MINAGE (0=bois/or, 1=pierre, 2=fer, 3=diamant, 4=netherite)
        }

        @Override
        public int getEnchantmentValue() {
            return 20; // FACILITÉ D'ENCHANTEMENT (bois=15, pierre=5, fer=14, diamant=10, or=22)
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(net.minecraft.world.item.Items.EMERALD);
        }
    };

    // Matériau redstone
    public static final Tier REDSTONE_TIER = new Tier() {
        @Override
        public int getUses() {
            return 800; // DURABILITÉ moyenne (entre pierre=131 et fer=250)
        }

        @Override
        public float getSpeed() {
            return 12.0f; // VITESSE DE MINAGE très rapide (thème électrique)
        }

        @Override
        public float getAttackDamageBonus() {
            return 2.0f; // DÉGÂTS BONUS modérés
        }

        @Override
        public int getLevel() {
            return 2; // NIVEAU DE MINAGE (comme le fer)
        }

        @Override
        public int getEnchantmentValue() {
            return 25; // FACILITÉ D'ENCHANTEMENT très élevée (thème magique)
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(net.minecraft.world.item.Items.REDSTONE);
        }
    };

    // Matériau lapis-lazulis
    public static final Tier LAPIS_TIER = new Tier() {
        @Override
        public int getUses() {
            return 1800; // DURABILITÉ moyenne (entre pierre=131 et fer=250)
        }

        @Override
        public float getSpeed() {
            return 12.0f; // VITESSE DE MINAGE très rapide (thème électrique)
        }

        @Override
        public float getAttackDamageBonus() {
            return 15.0f; // DÉGÂTS BONUS modérés
        }

        @Override
        public int getLevel() {
            return 2; // NIVEAU DE MINAGE (comme le fer)
        }

        @Override
        public int getEnchantmentValue() {
            return 25; // FACILITÉ D'ENCHANTEMENT très élevée (thème magique)
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(net.minecraft.world.item.Items.LAPIS_LAZULI);
        }
    };

    // Épée d'émeraude
    public static final RegistryObject<SwordItem> EMERALD_SWORD = ITEMS.register("emerald_sword",
            () -> new SwordItem(EMERALD_TIER,
                    2,      // DÉGÂTS DE BASE plus raisonnables (total = 5.0 + 2 + 1 = 8 dégâts)
                    -2.4f,  // VITESSE D'ATTAQUE normale d'épée
                    new Item.Properties()));

    // Épée de redstone
    public static final RegistryObject<SwordItem> REDSTONE_SWORD = ITEMS.register("redstone_sword",
            () -> new SwordItem(REDSTONE_TIER,
                    3,      // DÉGÂTS DE BASE (total = 2.0 + 3 + 1 = 6 dégâts)
                    -2.0f,  // VITESSE D'ATTAQUE rapide (thème électrique)
                    new Item.Properties()));

    // Épée de redstone
    public static final RegistryObject<SwordItem> LAPIS_SWORD = ITEMS.register("lapis_sword",
            () -> new SwordItem(LAPIS_TIER,
                    13,      // DÉGÂTS DE BASE (total = 2.0 + 3 + 1 = 6 dégâts)
                    2.0f,  // VITESSE D'ATTAQUE rapide (thème électrique)
                    new Item.Properties()));

    public EmeraldSwordMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Enregistre les items
        ITEMS.register(modEventBus);

        // Enregistre les événements
        modEventBus.addListener(this::addCreative);

        System.out.println("Emerald Sword Mod loaded for 1.20.1!");
    }

    @SubscribeEvent
    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        // Ajoute les épées dans l'onglet Combat du mode créatif
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(EMERALD_SWORD);
            event.accept(REDSTONE_SWORD);
            event.accept(LAPIS_SWORD);
        }
    }
}