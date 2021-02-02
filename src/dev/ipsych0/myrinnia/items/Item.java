package dev.ipsych0.myrinnia.items;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.CripplingImpactAbility;
import dev.ipsych0.myrinnia.abilities.DebilitatingStrikeAbility;
import dev.ipsych0.myrinnia.abilities.RockyConstrictAbility;
import dev.ipsych0.myrinnia.abilities.data.AbilityManager;
import dev.ipsych0.myrinnia.chatwindow.Filter;
import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.Resistance;
import dev.ipsych0.myrinnia.entities.buffs.AttributeBuff;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.entities.statics.ShamrockRockslide;
import dev.ipsych0.myrinnia.equipment.EquipSlot;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.utils.Utils;
import dev.ipsych0.myrinnia.worlds.Zone;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Item implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5417348314768685085L;
    // ItemList

    public static final int ITEMWIDTH = 32, ITEMHEIGHT = 32;
    public static Item[] items = new Item[1024];

    /*
     * Items
     */
    public static Item palmWood = Utils.loadItem("0_palm_wood.json", Assets.palmWood);
    public static Item azuriteOre = Utils.loadItem("1_azurite_ore.json", Assets.azuriteOre);
    public static Item magicSword = Utils.loadItem("2_magic_sword.json", Assets.undiscovered);
    public static Item simpleSword = Utils.loadItem("3_simple_sword.json", Assets.beginnersSword);
    public static Item coins = Utils.loadItem("4_coins.json", Assets.coins[0]);
    public static Item mackerelFish = Utils.loadItem("5_mackerel.json", Assets.mackerelFish);
    public static Item ryansAxe = Utils.loadItem("6_ryans_axe.json", Assets.ryansAxe);
    public static Item simplePickaxe = Utils.loadItem("7_simple_pickaxe.json", Assets.simplePickaxe);
    public static Item simpleStaff = Utils.loadItem("8_simple_staff.json", Assets.beginnersStaff);
    public static Item bountyContract = Utils.loadItem("9_bounty_contract.json", Assets.bountyContract);
    public static Item simpleAxe = Utils.loadItem("10_simple_axe.json", Assets.simpleAxe);
    public static Item simpleBow = Utils.loadItem("11_simple_bow.json", Assets.beginnersBow);
    public static Item simpleFishingRod = Utils.loadItem("12_simple_fishing_rod.json", Assets.simpleFishingRod);
    public static Item simpleSpellBook = Utils.loadItem("13_simple_spellbook.json", Assets.simpleSpellBook);
    public static Item simpleShield = Utils.loadItem("14_simple_shield.json", Assets.simpleShield);
    public static Item simpleQuiver = Utils.loadItem("15_simple_quiver.json", Assets.simpleQuiver);
    public static Item simpleSandals = Utils.loadItem("16_simple_sandals.json", Assets.simpleSandals);
    public static Item copperPickaxe = Utils.loadItem("17_copper_pickaxe.json", Assets.copperPickaxe);
    public static Item copperAxe = Utils.loadItem("18_copper_axe.json", Assets.copperAxe);
    public static Item copperOre = Utils.loadItem("19_copper_ore.json", Assets.copperOre);
    public static Item miningEquipment = Utils.loadItem("20_mining_equipment.json", Assets.miningEquipment);
    public static Item malachite = Utils.loadItem("21_malachite.json", Assets.malachite);
    public static Item dustyScroll = Utils.loadItem("22_dusty_scroll.json", Assets.abilityScroll, 0, (Use & Serializable) (i) -> {
        Handler.get().sendMsg("TODO: You unlocked [Earth Ability].");
        Handler.get().removeItem(Item.dustyScroll, 1);
    });
    public static Item azuriteNecklace = Utils.loadItem("23_azurite_necklace.json", Assets.azuriteNecklace);
    public static Item weakAntidote, antidote, strongAntidote, weakPotionOfPrecision, potionOfPrecision, strongPotionOfPrecision,
            weakPotionOfMight, potionOfMight, strongPotionOfMight, weakPotionOfWisdom, potionOfWisdom, strongPotionOfWisdom,
            weakPotionOfFortitude, potionOfFortitude, strongPotionOfFortitude, weakPotionOfVigor, potionOfVigor, strongPotionOfVigor;
    public static Item azureBatWing = Utils.loadItem("42_azure_bat_wing.json", Assets.azureBatWing);
    public static Item crablingClaw = Utils.loadItem("43_crabling_claw.json", Assets.crablingClaw);
    public static Item simpleGloves = Utils.loadItem("44_simple_gloves.json", Assets.simpleGloves);
    public static Item simpleBandana = Utils.loadItem("45_simple_bandana.json", Assets.simpleBandana);

    public static Item chitin = Utils.loadItem("46_chitin.json", Assets.chitin);
    public static Item scorpionTail = Utils.loadItem("47_scorpion_tail.json", Assets.scorpionTail);
    public static Item owlFeather = Utils.loadItem("48_owl_feather.json", Assets.owlFeather);
    public static Item dynamite = Utils.loadItem("49_dynamite.json", Assets.dynamite);
    public static Item detonator = Utils.loadItem("50_detonator.json", Assets.detonator, 0, (Use & Serializable) (i) -> {
        detonatorLogic();
    });
    public static Item vineRoot = Utils.loadItem("51_vine_root.json", Assets.vineRoot);
    public static Item simpleVest = Utils.loadItem("52_simple_vest.json", Assets.simpleVest);
    public static Item simpleTrousers = Utils.loadItem("53_simple_trousers.json", Assets.simpleTrousers);
    public static Item pileOfSand = Utils.loadItem("54_pile_of_sand.json", Assets.pileOfSand);
    public static Item pileOfAshes = Utils.loadItem("55_pile_of_ashes.json", Assets.pileOfAshes);
    public static Item glass = Utils.loadItem("56_glass.json", Assets.glass);
    public static Item lightWoodPlank = Utils.loadItem("57_lightwood_plank.json", Assets.lightWoodPlank);
    public static Item hardWood = Utils.loadItem("58_hardwood.json", Assets.hardWood);
    public static Item hardWoodPlank = Utils.loadItem("59_hardwood_plank.json", Assets.hardWoodPlank);
    public static Item ironOre = Utils.loadItem("60_iron_ore.json", Assets.ironOre);
    public static Item trout = Utils.loadItem("61_trout.json", Assets.trout);
    public static Item boneMeal = Utils.loadItem("62_bonemeal.json", Assets.boneMeal);
    public static Item rockyShell = Utils.loadItem("63_rocky_shell.json", Assets.rockyShell);
    public static Item tomatoSeeds = Utils.loadItem("64_tomato_seeds.json", Assets.tomatoSeeds);
    public static Item cabbageSeeds = Utils.loadItem("65_cabbage_seeds.json", Assets.cabbageSeeds);
    public static Item tomato = Utils.loadItem("66_tomato.json", Assets.tomato);
    public static Item cabbage = Utils.loadItem("67_cabbage.json", Assets.cabbage);
    public static Item wateringCan = Utils.loadItem("68_watering_can.json", Assets.wateringCan);
    public static Item rockyConstrictScroll = Utils.loadItem("69_ability_scroll.json", Assets.abilityScroll, 0, (Use & Serializable) (i) -> {
        AbilityManager.abilityMap.get(RockyConstrictAbility.class).setUnlocked(true);
        Handler.get().removeItem(Item.rockyConstrictScroll, 1);
    });
    public static Item cripplingImpactScroll = Utils.loadItem("70_ability_scroll.json", Assets.abilityScroll, 0, (Use & Serializable) (i) -> {
        AbilityManager.abilityMap.get(CripplingImpactAbility.class).setUnlocked(true);
        Handler.get().removeItem(Item.cripplingImpactScroll, 1);
    });
    public static Item debilitatingStrikeScroll = Utils.loadItem("71_ability_scroll.json", Assets.abilityScroll, 0, (Use & Serializable) (i) -> {
        AbilityManager.abilityMap.get(DebilitatingStrikeAbility.class).setUnlocked(true);
        Handler.get().removeItem(Item.debilitatingStrikeScroll, 1);
    });
    public static Item softLeather = Utils.loadItem("72_soft_leather.json", Assets.softLeather);
    public static Item stripOfWool = Utils.loadItem("73_strip_of_wool.json", Assets.stripOfWool);
    public static Item lapisLazuli = Utils.loadItem("74_lapis_lazuli.json", Assets.lapisLazuli);
    public static Item azuriteEarrings = Utils.loadItem("75_azurite_earrings.json", Assets.azuriteEarrings);
    public static Item azuriteRingL = Utils.loadItem("76_azurite_ring_(l).json", Assets.azuriteRingL);
    public static Item azuriteRingR = Utils.loadItem("77_azurite_ring_(r).json", Assets.azuriteRingR);
    public static Item copperFishingRod = Utils.loadItem("78_copper_fishing_rod.json", Assets.copperFishingRod);
    public static Item malachiteEarrings = Utils.loadItem("79_malachite_earrings.json", Assets.malachiteEarrings);
    public static Item malachiteRingL = Utils.loadItem("80_malachite_ring_(l).json", Assets.malachiteRingL);
    public static Item malachiteRingR = Utils.loadItem("81_malachite_ring_(r).json", Assets.malachiteRingR);
    public static Item malachiteAmulet = Utils.loadItem("82_malachite_amulet.json", Assets.malachiteAmulet);
    public static Item ironAxe = Utils.loadItem("83_iron_axe.json", Assets.ironAxe);
    public static Item ironPickaxe = Utils.loadItem("84_iron_pickaxe.json", Assets.ironPickaxe);
    public static Item ironFishingRod = Utils.loadItem("85_iron_fishing_rod.json", Assets.ironFishingRod);

    public static Item ironChainMail = Utils.loadItem("86_iron_chainmail.json", Assets.ironChainMail);
    public static Item studdedShield = Utils.loadItem("87_studded_shield.json", Assets.studdedShield);
    public static Item ironSword = Utils.loadItem("88_iron_sword.json", Assets.ironSword);
    public static Item ironLegs = Utils.loadItem("89_iron_legs.json", Assets.ironLegs);
    public static Item squiresCloak = Utils.loadItem("90_squire's_cloak.json", Assets.squiresCloak);
    public static Item ironHelm = Utils.loadItem("91_iron_helm.json", Assets.ironHelm);
    public static Item ironBoots = Utils.loadItem("92_iron_boots.json", Assets.ironBoots);
    public static Item ironGloves = Utils.loadItem("93_iron_gloves.json", Assets.ironGloves);

    public static Item softLeatherBody = Utils.loadItem("94_soft_leather_body.json", Assets.softLeatherBody);
    public static Item ironQuiver = Utils.loadItem("95_iron_quiver.json", Assets.ironQuiver);
    public static Item hardwoodBow = Utils.loadItem("96_hardwood_bow.json", Assets.hardwoodBow);
    public static Item softLeatherLeggings = Utils.loadItem("97_soft_leather_leggings.json", Assets.softLeatherLeggings);
    public static Item scoutsCloak = Utils.loadItem("98_scout's_cloak.json", Assets.scoutsCloak);
    public static Item softLeatherCowl = Utils.loadItem("99_soft_leather_cowl.json", Assets.softLeatherCowl);
    public static Item softLeatherBoots = Utils.loadItem("100_soft_leather_boots.json", Assets.softLeatherBoots);
    public static Item softLeatherGloves = Utils.loadItem("101_soft_leather_gloves.json", Assets.softLeatherGloves);

    public static Item woolenRobeTop = Utils.loadItem("102_woolen_robe_top.json", Assets.woolenRobeTop);
    public static Item leatherSpellbook = Utils.loadItem("103_leather_spellbook.json", Assets.leatherSpellbook);
    public static Item hardwoodStaff = Utils.loadItem("104_hardwood_staff.json", Assets.hardwoodStaff);
    public static Item woolenRobeBottom = Utils.loadItem("105_woolen_robe_bottom.json", Assets.woolenRobeBottom);
    public static Item apprenticesCloak = Utils.loadItem("106_apprentice's_cloak.json", Assets.apprenticesCloak);
    public static Item woolenHat = Utils.loadItem("107_woolen_hat.json", Assets.woolenHat);
    public static Item woolenBoots = Utils.loadItem("108_woolen_boots.json", Assets.woolenBoots);
    public static Item woolenGloves = Utils.loadItem("109_woolen_gloves.json", Assets.woolenGloves);
    public static Item rope = Utils.loadItem("110_rope.json", Assets.rope);
    public static Item snakehead = Utils.loadItem("111_snakehead.json", Assets.snakehead);
    public static Item clam = Utils.loadItem("112_clam.json", Assets.clam);
    public static Item rake = Utils.loadItem("113_rake.json", Assets.rake);
    public static Item tungstenOre = Utils.loadItem("114_tungsten_ore.json", Assets.tungstenOre);
    public static Item aspenwood = Utils.loadItem("115_aspenwood.json", Assets.aspenwood);
    public static Item lightwood = Utils.loadItem("116_lightwood.json", Assets.lightwood);

    public static Item strawberrySeeds = Utils.loadItem("117_strawberry_seeds.json", Assets.strawberrySeeds);
    public static Item raspberrySeeds = Utils.loadItem("118_raspberry_seeds.json", Assets.raspberrySeeds);
    public static Item blackberrySeeds = Utils.loadItem("119_blackberry_seeds.json", Assets.blackberrySeeds);
    public static Item blueberrySeeds = Utils.loadItem("120_blueberry_seeds.json", Assets.blueberrySeeds);

    public static Item appleTreeSeeds = Utils.loadItem("121_apple_tree_seeds.json", Assets.appleTreeSeeds);
    public static Item bananaTreeSeeds = Utils.loadItem("122_banana_tree_seeds.json", Assets.bananaTreeSeeds);
    public static Item orangeTreeSeeds = Utils.loadItem("123_orange_tree_seeds.json", Assets.orangeTreeSeeds);
    public static Item apricotTreeSeeds = Utils.loadItem("124_apricot_tree_seed.json", Assets.apricotTreeSeeds);
    public static Item peachTreeSeeds = Utils.loadItem("125_peach_tree_seeds.json", Assets.peachTreeSeeds);
    public static Item papayaTreeSeeds = Utils.loadItem("126_papaya_tree_seeds.json", Assets.papayaTreeSeeds);
    public static Item starfruitTreeSeeds = Utils.loadItem("127_starfruit_tree_seeds.json", Assets.starfruitTreeSeeds);
    public static Item dragonfruitTreeSeeds = Utils.loadItem("128_dragonfruit_tree_seeds.json", Assets.dragonfruitTreeSeeds);

    public static Item strawberry = Utils.loadItem("129_strawberry.json", Assets.strawberry);
    public static Item raspberry = Utils.loadItem("130_raspberry.json", Assets.raspberry);
    public static Item blackberry = Utils.loadItem("131_blackberry.json", Assets.blackberry);
    public static Item blueberry = Utils.loadItem("132_blueberry.json", Assets.blueberry);

    public static Item apple = Utils.loadItem("133_apple.json", Assets.apple);
    public static Item banana = Utils.loadItem("134_banana.json", Assets.banana);
    public static Item orange = Utils.loadItem("135_orange.json", Assets.orange);
    public static Item apricot = Utils.loadItem("136_apricot.json", Assets.apricot);
    public static Item peach = Utils.loadItem("137_peach.json", Assets.peach);
    public static Item papaya = Utils.loadItem("138_papaya.json", Assets.papaya);
    public static Item starfruit = Utils.loadItem("139_starfruit.json", Assets.starfruit);
    public static Item dragonfruit = Utils.loadItem("140_dragonfruit.json", Assets.dragonfruit);

    public static Item shears = Utils.loadItem("141_shears.json", Assets.shears);
    public static Item wool = Utils.loadItem("142_wool.json", Assets.wool, 0, (Use & Serializable) (i) -> {
        Handler.get().sendMsg("I can probably craft something useful out of this.");
    });
    public static Item bucket = Utils.loadItem("143_empty_bucket.json", Assets.bucket);
    public static Item pollutedBucket = Utils.loadItem("144_bucket_of_polluted_water.json", Assets.pollutedBucket);
    public static Item amanitaMushroom = Utils.loadItem("145_amanita_mushroom.json", Assets.amanitaMushroom);
    public static Item potionOfDecontamination = Utils.loadItem("146_potion_of_decontamination.json", Assets.potionOfDecontamination);

    public static Item litTorch = Utils.loadItem("147_lit_torch.json", Assets.litTorch, 0, (i) -> {
        Handler.get().removeItem(Item.litTorch, 1);
        Handler.get().giveItem(Item.unlitTorch, 1);
        Handler.get().sendMsg("You extinguish the torch.");
    });
    public static Item unlitTorch = Utils.loadItem("148_unlit_torch.json", Assets.unlitTorch, 0, (i) -> {
        if (Handler.get().playerHasItemType(ItemType.FIRE_SOURCE)) {
            Handler.get().removeItem(Item.unlitTorch, 1);
            Handler.get().giveItem(Item.litTorch, 1);
            Handler.get().sendMsg("You light the torch.");
        } else {
            Handler.get().sendMsg("You don't have anything to light the torch with.");
        }
    });
    public static Item unlitLantern = Utils.loadItem("149_unlit_lantern.json", Assets.unlitLantern, 0, (i) -> {
        if (Handler.get().playerHasItemType(ItemType.FIRE_SOURCE)) {
            Handler.get().removeItem(Item.unlitLantern, 1);
            Handler.get().giveItem(Item.litLantern, 1);
            Handler.get().sendMsg("You light the lantern.");
        } else {
            Handler.get().sendMsg("You don't have anything to light the lantern with.");
        }
    });
    public static Item litLantern = Utils.loadItem("150_lit_lantern.json", Assets.litLantern, 0, (i) -> {
        Handler.get().removeItem(Item.litLantern, 1);
        Handler.get().giveItem(Item.unlitLantern, 1);
        Handler.get().sendMsg("You extinguish the lantern.");
    });
    public static Item unlitCandle = Utils.loadItem("151_unlit_candle.json", Assets.unlitCandle, 0, (i) -> {
        if (Handler.get().playerHasItemType(ItemType.FIRE_SOURCE)) {
            Handler.get().removeItem(Item.unlitCandle, 1);
            Handler.get().giveItem(Item.litCandle, 1);
            Handler.get().sendMsg("You light the candle.");
        } else {
            Handler.get().sendMsg("You don't have anything to light the candle with.");
        }
    });
    public static Item litCandle = Utils.loadItem("152_lit_candle.json", Assets.litCandle, 0, (i) -> {
        Handler.get().removeItem(Item.litCandle, 1);
        Handler.get().giveItem(Item.unlitCandle, 1);
        Handler.get().sendMsg("You extinguish the candle.");
    });
    public static Item matchbox = Utils.loadItem("153_matchbox.json", Assets.matchbox);
    public static Item clay = Utils.loadItem("154_clay.json", Assets.clay);
    public static Item coalOre = Utils.loadItem("155_coal_ore.json", Assets.coalOre);
    public static Item hardLeather = Utils.loadItem("156_hard_leather.json", Assets.hardLeather);
    public static Item stripOfLinen = Utils.loadItem("157_strip_of_linen.json", Assets.stripOfLinen);
    public static Item flaxSeeds = Utils.loadItem("158_flax_seeds.json", Assets.flaxSeeds);
    public static Item flax = Utils.loadItem("159_flax.json", Assets.flax);
    public static Item topaz = Utils.loadItem("160_topaz.json", Assets.topaz);
    public static Item pearl = Utils.loadItem("161_pearl.json", Assets.pearl);
    public static Item amethyst = Utils.loadItem("162_amethyst.json", Assets.amethyst);
    public static Item diamond = Utils.loadItem("163_diamond.json", Assets.diamond);
    public static Item onyx = Utils.loadItem("164_onyx.json", Assets.onyx);
    public static Item silverOre = Utils.loadItem("165_silver_ore.json", Assets.silverOre);
    public static Item goldOre = Utils.loadItem("166_gold_ore.json", Assets.goldOre);
    public static Item palladiumOre = Utils.loadItem("167_palladium_ore.json", Assets.palladiumOre);
    public static Item cobaltOre = Utils.loadItem("168_cobalt_ore.json", Assets.cobaltOre);
    public static Item platinumOre = Utils.loadItem("169_platinum_ore.json", Assets.platinumOre);
    public static Item titaniumOre = Utils.loadItem("170_titanium_ore.json", Assets.titaniumOre);
    public static Item obsidian = Utils.loadItem("171_obsidian.json", Assets.obsidian);
    public static Item obsidianShard = Utils.loadItem("172_obsidian_shard.json", Assets.obsidianShard);
    public static Item titaniumPlating = Utils.loadItem("173_titanium_plating.json", Assets.titaniumPlating);
    public static Item steelPlating = Utils.loadItem("174_steel_plating.json", Assets.steelPlating);
    public static Item yakHair = Utils.loadItem("175_yak_hair.json", Assets.yakHair);
    public static Item highQualityYakFibre = Utils.loadItem("176_high_quality_yak_fibre.json", Assets.highQualityYakFibre);
    public static Item primordialCrystal = Utils.loadItem("177_primordial_crystal.json", Assets.primordialCrystal);
    public static Item celenorianThread = Utils.loadItem("178_celenorian_thread.json", Assets.celenorianThread);
    public static Item arcaneThread = Utils.loadItem("179_arcane_thread.json", Assets.arcaneThread);

    public static Item topazEarrings = Utils.loadItem("180_topaz_earrings.json", Assets.topazEarrings);
    public static Item topazRingL = Utils.loadItem("181_topaz_ring_(l).json", Assets.topazRingL);
    public static Item topazRingR = Utils.loadItem("182_topaz_ring_(r).json", Assets.topazRingR);
    public static Item topazAmulet = Utils.loadItem("183_topaz_amulet.json", Assets.topazAmulet);

    public static Item pearlEarrings = Utils.loadItem("184_pearl_earrings.json", Assets.pearlEarrings);
    public static Item pearlRingL = Utils.loadItem("185_pearl_ring_(l).json", Assets.pearlRingL);
    public static Item pearlRingR = Utils.loadItem("186_pearl_ring_(r).json", Assets.pearlRingR);
    public static Item pearlAmulet = Utils.loadItem("187_pearl_amulet.json", Assets.pearlAmulet);

    public static Item amethystEarrings = Utils.loadItem("188_amethyst_earrings.json", Assets.amethystEarrings);
    public static Item amethystRingL = Utils.loadItem("189_amethyst_ring_(l).json", Assets.amethystRingL);
    public static Item amethystRingR = Utils.loadItem("190_amethyst_ring_(r).json", Assets.amethystRingR);
    public static Item amethystAmulet = Utils.loadItem("191_amethyst_amulet.json", Assets.amethystAmulet);

    public static Item diamondEarrings = Utils.loadItem("192_diamond_earrings.json", Assets.diamondEarrings);
    public static Item diamondRingL = Utils.loadItem("193_diamond_ring_(l).json", Assets.diamondRingL);
    public static Item diamondRingR = Utils.loadItem("194_diamond_ring_(r).json", Assets.diamondRingR);
    public static Item diamondAmulet = Utils.loadItem("195_diamond_amulet.json", Assets.diamondAmulet);

    public static Item onyxEarrings = Utils.loadItem("196_onyx_earrings.json", Assets.onyxEarrings);
    public static Item onyxRingL = Utils.loadItem("197_onyx_ring_(l).json", Assets.onyxRingL);
    public static Item onyxRingR = Utils.loadItem("198_onyx_ring_(r).json", Assets.onyxRingR);
    public static Item onyxAmulet = Utils.loadItem("199_onyx_amulet.json", Assets.onyxAmulet);

    public static Item steelAxe = Utils.loadItem("200_steel_axe.json", Assets.steelAxe);
    public static Item steelPickaxe = Utils.loadItem("201_steel_pickaxe.json", Assets.steelPickaxe);
    public static Item steelFishingRod = Utils.loadItem("202_steel_fishing_rod.json", Assets.steelFishingRod);

    public static Item platinumAxe = Utils.loadItem("203_platinum_axe.json", Assets.platinumAxe);
    public static Item platinumPickaxe = Utils.loadItem("204_platinum_pickaxe.json", Assets.platinumPickaxe);
    public static Item platinumFishingRod = Utils.loadItem("205_platinum_fishing_rod.json", Assets.platinumFishingRod);

    public static Item titaniumAxe = Utils.loadItem("206_titanium_axe.json", Assets.titaniumAxe);
    public static Item titaniumPickaxe = Utils.loadItem("207_titanium_pickaxe.json", Assets.titaniumPickaxe);
    public static Item titaniumFishingRod = Utils.loadItem("208_titanium_fishing_rod.json", Assets.titaniumFishingRod);

    public static Item obsidianAxe = Utils.loadItem("209_obsidian_axe.json", Assets.obsidianAxe);
    public static Item obsidianPickaxe = Utils.loadItem("210_obsidian_pickaxe.json", Assets.obsidianPickaxe);
    public static Item obsidianFishingRod = Utils.loadItem("211_obsidian_fishing_rod.json", Assets.obsidianFishingRod);

    public static Item primordialAxe = Utils.loadItem("212_primordial_axe.json", Assets.primordialAxe);
    public static Item primordialPickaxe = Utils.loadItem("213_primordial_pickaxe.json", Assets.primordialPickaxe);
    public static Item primordialFishingRod = Utils.loadItem("214_primordial_fishing_rod.json", Assets.primordialFishingRod);

    public static Item teakWood = Utils.loadItem("215_teak_wood.json", Assets.teakWood);
    public static Item mahoganyWood = Utils.loadItem("216_mahogany_wood.json", Assets.mahoganyWood);
    public static Item elderWood = Utils.loadItem("217_elder_wood.json", Assets.elderWood);
    public static Item ancientWood = Utils.loadItem("218_ancient_wood.json", Assets.ancientWood);
    public static Item reinforcedLeather = Utils.loadItem("219_reinforced_leather.json", Assets.reinforcedLeather);
    public static Item studdedLeather = Utils.loadItem("220_studded_leather.json", Assets.studdedLeather);
    public static Item armoredLeather = Utils.loadItem("221_armored_leather.json", Assets.armoredLeather);
    public static Item celenorianLeather = Utils.loadItem("222_celenorian_leather.json", Assets.celenorianLeather);
    public static Item stripOfSilk = Utils.loadItem("223_strip_of_silk.json", Assets.stripOfSilk);
    public static Item stripOfDamask = Utils.loadItem("224_strip_of_damask.json", Assets.stripOfDamask);
    public static Item stripOfIntricateCloth = Utils.loadItem("225_strip_of_intricate_cloth.json", Assets.stripOfIntricateCloth);
    public static Item stripOfFarnorCloth = Utils.loadItem("226_strip_of_farnor_cloth.json", Assets.stripOfFarnorCloth);
    public static Item primordialIngot = Utils.loadItem("227_primordial_ingot.json", Assets.primordialIngot);

    public static Item steelChainmail = Utils.loadItem("228_steel_chainmail.json", Assets.steelChainmail);
    public static Item spikedShield = Utils.loadItem("229_spiked_shield.json", Assets.spikedShield);
    public static Item steelSword = Utils.loadItem("230_steel_sword.json", Assets.steelSword);
    public static Item steelLegs = Utils.loadItem("231_steel_legs.json", Assets.steelLegs);
    public static Item knightsCloak = Utils.loadItem("232_knight's_cloak.json", Assets.knightsCloak);
    public static Item steelHelm = Utils.loadItem("233_steel_helm.json", Assets.steelHelm);
    public static Item steelBoots = Utils.loadItem("234_steel_boots.json", Assets.steelBoots);
    public static Item steelGloves = Utils.loadItem("235_steel_gloves.json", Assets.steelGloves);
    public static Item hardLeatherBody = Utils.loadItem("236_hard_leather_body.json", Assets.hardLeatherBody);
    public static Item steelQuiver = Utils.loadItem("237_steel_quiver.json", Assets.steelQuiver);
    public static Item aspenwoodBow = Utils.loadItem("238_aspenwood_bow.json", Assets.aspenwoodBow);
    public static Item hardLeatherLeggings = Utils.loadItem("239_hard_leather_leggings.json", Assets.hardLeatherLeggings);
    public static Item wardensCloak = Utils.loadItem("240_warden's_cloak.json", Assets.wardensCloak);
    public static Item hardLeatherCowl = Utils.loadItem("241_hard_leather_cowl.json", Assets.hardLeatherCowl);
    public static Item hardLeatherBoots = Utils.loadItem("242_hard_leather_boots.json", Assets.hardLeatherBoots);
    public static Item hardLeatherGloves = Utils.loadItem("243_hard_leather_gloves.json", Assets.hardLeatherGloves);
    public static Item linenRobeTop = Utils.loadItem("244_linen_robe_top.json", Assets.linenRobeTop);
    public static Item hardLeatherSpellbook = Utils.loadItem("245_hard_leather_spellbook.json", Assets.hardLeatherSpellbook);
    public static Item aspenwoodStaff = Utils.loadItem("246_aspenwood_staff.json", Assets.aspenwoodStaff);
    public static Item linenRobeBottom = Utils.loadItem("247_linen_robe_bottom.json", Assets.linenRobeBottom);
    public static Item wizardsCloak = Utils.loadItem("248_wizard's_cloak.json", Assets.wizardsCloak);
    public static Item linenHat = Utils.loadItem("249_linen_hat.json", Assets.linenHat);
    public static Item linenBoots = Utils.loadItem("250_linen_boots.json", Assets.linenBoots);
    public static Item linenGloves = Utils.loadItem("251_linen_gloves.json", Assets.linenGloves);
    public static Item platinumPlatemail = Utils.loadItem("252_platinum_platemail.json", Assets.platinumPlatemail);
    public static Item reinforcedShield = Utils.loadItem("253_reinforced_shield.json", Assets.reinforcedShield);
    public static Item platinumSword = Utils.loadItem("254_platinum_sword.json", Assets.platinumSword);
    public static Item platinumLegs = Utils.loadItem("255_platinum_legs.json", Assets.platinumLegs);
    public static Item championsCloak = Utils.loadItem("256_champion's_cloak.json", Assets.championsCloak);
    public static Item platinumHelm = Utils.loadItem("257_platinum_helm.json", Assets.platinumHelm);
    public static Item platinumBoots = Utils.loadItem("258_platinum_boots.json", Assets.platinumBoots);
    public static Item platinumGloves = Utils.loadItem("259_platinum_gloves.json", Assets.platinumGloves);
    public static Item reinforcedBody = Utils.loadItem("260_reinforced_body.json", Assets.reinforcedBody);
    public static Item platinumQuiver = Utils.loadItem("261_platinum_quiver.json", Assets.platinumQuiver);
    public static Item teakBow = Utils.loadItem("262_teak_bow.json", Assets.teakBow);
    public static Item reinforcedLeggings = Utils.loadItem("263_reinforced_leggings.json", Assets.reinforcedLeggings);
    public static Item markmansCloak = Utils.loadItem("264_markman's_cloak.json", Assets.markmansCloak);
    public static Item reinforcedCowl = Utils.loadItem("265_reinforced_cowl.json", Assets.reinforcedCowl);
    public static Item reinforcedBoots = Utils.loadItem("266_reinforced_boots.json", Assets.reinforcedBoots);
    public static Item reinforcedGloves = Utils.loadItem("267_reinforced_gloves.json", Assets.reinforcedGloves);
    public static Item silkRobeTop = Utils.loadItem("268_silk_robe_top.json", Assets.silkRobeTop);
    public static Item sorcerersSpellbook = Utils.loadItem("269_sorcerer's_spellbook.json", Assets.sorcerersSpellbook);
    public static Item teakStaff = Utils.loadItem("270_teak_staff.json", Assets.teakStaff);
    public static Item silkRobeBottom = Utils.loadItem("271_silk_robe_bottom.json", Assets.silkRobeBottom);
    public static Item sorcerersCloak = Utils.loadItem("272_sorcerer's_cloak.json", Assets.sorcerersCloak);
    public static Item silkHat = Utils.loadItem("273_silk_hat.json", Assets.silkHat);
    public static Item silkBoots = Utils.loadItem("274_silk_boots.json", Assets.silkBoots);
    public static Item silkGloves = Utils.loadItem("275_silk_gloves.json", Assets.silkGloves);
    public static Item titaniumPlatemail = Utils.loadItem("276_titanium_platemail.json", Assets.titaniumPlatemail);
    public static Item towerShield = Utils.loadItem("277_tower_shield.json", Assets.towerShield);
    public static Item titaniumSword = Utils.loadItem("278_titanium_sword.json", Assets.titaniumSword);
    public static Item titaniumLegs = Utils.loadItem("279_titanium_legs.json", Assets.titaniumLegs);
    public static Item warchiefsCloak = Utils.loadItem("280_warchief's_cloak.json", Assets.warchiefsCloak);
    public static Item titaniumHelm = Utils.loadItem("281_titanium_helm.json", Assets.titaniumHelm);
    public static Item titaniumBoots = Utils.loadItem("282_titanium_boots.json", Assets.titaniumBoots);
    public static Item titaniumGloves = Utils.loadItem("283_titanium_gloves.json", Assets.titaniumGloves);
    public static Item studdedLeatherBody = Utils.loadItem("284_studded_leather_body.json", Assets.studdedLeatherBody);
    public static Item titaniumQuiver = Utils.loadItem("285_titanium_quiver.json", Assets.titaniumQuiver);
    public static Item mahoganyBow = Utils.loadItem("286_mahogany_bow.json", Assets.mahoganyBow);
    public static Item studdedLeatherLeggings = Utils.loadItem("287_studded_leather_leggings.json", Assets.studdedLeatherLeggings);
    public static Item sharpshootersCloak = Utils.loadItem("288_sharpshooter's_cloak.json", Assets.sharpshootersCloak);
    public static Item studdedLeatherCowl = Utils.loadItem("289_studded_leather_cowl.json", Assets.studdedLeatherCowl);
    public static Item studdedLeatherBoots = Utils.loadItem("290_studded_leather_boots.json", Assets.studdedLeatherBoots);
    public static Item studdedLeatherGloves = Utils.loadItem("291_studded_leather_gloves.json", Assets.studdedLeatherGloves);
    public static Item damaskRobeTop = Utils.loadItem("292_damask_robe_top.json", Assets.damaskRobeTop);
    public static Item warlocksSpellbook = Utils.loadItem("293_warlock's_spellbook.json", Assets.warlocksSpellbook);
    public static Item mahoganyStaff = Utils.loadItem("294_mahogany_staff.json", Assets.mahoganyStaff);
    public static Item damaskRobeBottom = Utils.loadItem("295_damask_robe_bottom.json", Assets.damaskRobeBottom);
    public static Item warlocksCloak = Utils.loadItem("296_warlock's_cloak.json", Assets.warlocksCloak);
    public static Item damaskHat = Utils.loadItem("297_damask_hat.json", Assets.damaskHat);
    public static Item damaskBoots = Utils.loadItem("298_damask_boots.json", Assets.damaskBoots);
    public static Item damaskGloves = Utils.loadItem("299_damask_gloves.json", Assets.damaskGloves);
    public static Item obsidianCuirass = Utils.loadItem("300_obsidian_cuirass.json", Assets.obsidianCuirass);
    public static Item obsidianBuckler = Utils.loadItem("301_obsidian_buckler.json", Assets.obsidianBuckler);
    public static Item obsidianBlade = Utils.loadItem("302_obsidian_blade.json", Assets.obsidianBlade);
    public static Item obsidianLegs = Utils.loadItem("303_obsidian_legs.json", Assets.obsidianLegs);
    public static Item gladiatorsCloak = Utils.loadItem("304_gladiator's_cloak.json", Assets.gladiatorsCloak);
    public static Item obsidianHelm = Utils.loadItem("305_obsidian_helm.json", Assets.obsidianHelm);
    public static Item obsidianGreaves = Utils.loadItem("306_obsidian_greaves.json", Assets.obsidianGreaves);
    public static Item obsidianGauntlets = Utils.loadItem("307_obsidian_gauntlets.json", Assets.obsidianGauntlets);
    public static Item deadeyesTorso = Utils.loadItem("308_deadeye's_torso.json", Assets.deadeyesTorso);
    public static Item obsidianQuiver = Utils.loadItem("309_obsidian_quiver.json", Assets.obsidianQuiver);
    public static Item deadeyesFlatbow = Utils.loadItem("310_deadeye's_flatbow.json", Assets.deadeyesFlatbow);
    public static Item deadeyesLeggings = Utils.loadItem("311_deadeye's_leggings.json", Assets.deadeyesLeggings);
    public static Item deadeyesCloak = Utils.loadItem("312_deadeye's_cloak.json", Assets.deadeyesCloak);
    public static Item deadeyesCowl = Utils.loadItem("313_deadeye's_cowl.json", Assets.deadeyesCowl);
    public static Item deadeyesBoots = Utils.loadItem("314_deadeye's_boots.json", Assets.deadeyesBoots);
    public static Item deadeyesGloves = Utils.loadItem("315_deadeye's_gloves.json", Assets.deadeyesGloves);
    public static Item seersGarb = Utils.loadItem("316_seer's_garb.json", Assets.seersGarb);
    public static Item seersBook = Utils.loadItem("317_seer's_book.json", Assets.seersBook);
    public static Item seersSpire = Utils.loadItem("318_seer's_spire.json", Assets.seersSpire);
    public static Item seersGown = Utils.loadItem("319_seer's_gown.json", Assets.seersGown);
    public static Item seersCloak = Utils.loadItem("320_seer's_cloak.json", Assets.seersCloak);
    public static Item seersHat = Utils.loadItem("321_seer's_hat.json", Assets.seersHat);
    public static Item seersBoots = Utils.loadItem("322_seer's_boots.json", Assets.seersBoots);
    public static Item seersGloves = Utils.loadItem("323_seer's_gloves.json", Assets.seersGloves);
    public static Item primordialCuirass = Utils.loadItem("324_primordial_cuirass.json", Assets.primordialCuirass);
    public static Item primordialKiteshield = Utils.loadItem("325_primordial_kiteshield.json", Assets.primordialKiteshield);
    public static Item primordialBlade = Utils.loadItem("326_primordial_blade.json", Assets.primordialBlade);
    public static Item primordialPlatelegs = Utils.loadItem("327_primordial_platelegs.json", Assets.primordialPlatelegs);
    public static Item primordialCloak = Utils.loadItem("328_primordial_cloak.json", Assets.primordialCloak);
    public static Item primordialGreathelm = Utils.loadItem("329_primordial_greathelm.json", Assets.primordialGreathelm);
    public static Item primordialGreaves = Utils.loadItem("330_primordial_greaves.json", Assets.primordialGreaves);
    public static Item primordialGauntlets = Utils.loadItem("331_primordial_gauntlets.json", Assets.primordialGauntlets);
    public static Item primevalTorso = Utils.loadItem("332_primeval_torso.json", Assets.primevalTorso);
    public static Item primevalQuiver = Utils.loadItem("333_primeval_quiver.json", Assets.primevalQuiver);
    public static Item primevalRecurveBow = Utils.loadItem("334_primeval_recurve_bow.json", Assets.primevalRecurveBow);
    public static Item primevalLeggings = Utils.loadItem("335_primeval_leggings.json", Assets.primevalLeggings);
    public static Item primevalCloak = Utils.loadItem("336_primeval_cloak.json", Assets.primevalCloak);
    public static Item primevalCowl = Utils.loadItem("337_primeval_cowl.json", Assets.primevalCowl);
    public static Item primevalBoots = Utils.loadItem("338_primeval_boots.json", Assets.primevalBoots);
    public static Item primevalGloves = Utils.loadItem("339_primeval_gloves.json", Assets.primevalGloves);
    public static Item primalGownTop = Utils.loadItem("340_primal_gown_top.json", Assets.primalGownTop);
    public static Item bookOfPrimalMagics = Utils.loadItem("341_book_of_primal_magics.json", Assets.bookOfPrimalMagics);
    public static Item primalSceptre = Utils.loadItem("342_primal_sceptre.json", Assets.primalSceptre);
    public static Item primalGownBottoms = Utils.loadItem("343_primal_gown_bottoms.json", Assets.primalGownBottoms);
    public static Item primalCloak = Utils.loadItem("344_primal_cloak.json", Assets.primalCloak);
    public static Item primalHood = Utils.loadItem("345_primal_hood.json", Assets.primalHood);
    public static Item primalFootgear = Utils.loadItem("346_primal_footgear.json", Assets.primalFootgear);
    public static Item primalGloves = Utils.loadItem("347_primal_gloves.json", Assets.primalGloves);
    public static Item spiderSilk = Utils.loadItem("348_spider_silk.json", Assets.spiderSilk);
    public static Item eel = Utils.loadItem("349_eel.json", Assets.eel);
    public static Item venomSac = Utils.loadItem("350_venom_sac.json", Assets.venomSac);
    public static Item sharpTooth = Utils.loadItem("351_sharp_tooth.json", Assets.sharpTooth);
    public static Item pointySpine = Utils.loadItem("352_pointy_spine.json", Assets.pointySpine);
    public static Item hauntedRag = Utils.loadItem("353_haunted_rag.json", Assets.hauntedRag);
    public static Item potentLeaves = Utils.loadItem("354_potent_leaves.json", Assets.potentLeaves);
    public static Item graniteChunk = Utils.loadItem("355_granite_chunk.json", Assets.graniteChunk);

    // Food items
    public static Item onionSeeds = Utils.loadItem("356_onion_seeds.json", Assets.onionSeeds);
    public static Item onion = Utils.loadItem("357_onion.json", Assets.onion);
    public static Item carrotSeeds = Utils.loadItem("358_carrot_seeds.json", Assets.carrotSeeds);
    public static Item carrot = Utils.loadItem("359_carrot.json", Assets.carrot);
    public static Item potatoSeeds = Utils.loadItem("360_potato_seeds.json", Assets.potatoSeeds);
    public static Item potato = Utils.loadItem("361_potato.json", Assets.potato);
    public static Item cauliflowerSeeds = Utils.loadItem("362_cauliflower_seeds.json", Assets.cauliflowerSeeds);
    public static Item cauliflower = Utils.loadItem("363_cauliflower.json", Assets.cauliflower);
    public static Item kaleSeeds = Utils.loadItem("364_kale_seeds.json", Assets.kaleSeeds);
    public static Item kale = Utils.loadItem("365_kale.json", Assets.kale);
    public static Item broccoliSeeds = Utils.loadItem("366_broccoli_seeds.json", Assets.broccoliSeeds);
    public static Item broccoli = Utils.loadItem("367_broccoli.json", Assets.broccoli);
    public static Item spinachSeeds = Utils.loadItem("368_spinach_seeds.json", Assets.spinachSeeds);
    public static Item spinach = Utils.loadItem("369_spinach.json", Assets.spinach);
    public static Item sweetPotatoSeeds = Utils.loadItem("370_sweet_potato_seeds.json", Assets.sweetPotatoSeeds);
    public static Item sweetPotato = Utils.loadItem("371_sweet_potato.json", Assets.sweetPotato);
    public static Item pumpkinSeeds = Utils.loadItem("372_pumpkin_seeds.json", Assets.pumpkinSeeds);
    public static Item pumpkin = Utils.loadItem("373_pumpkin.json", Assets.pumpkin);
    public static Item egg = Utils.loadItem("374_egg.json", Assets.egg);
    public static Item jugOfWater = Utils.loadItem("375_jug_of_water.json", Assets.jugOfWater);
    public static Item potOfFlour = Utils.loadItem("376_pot_of_flour.json", Assets.potOfFlour);
    public static Item dough = Utils.loadItem("377_dough.json", Assets.dough);
    public static Item bowl = Utils.loadItem("378_bowl.json", Assets.bowl);
    public static Item cakeTin = Utils.loadItem("379_cake_tin.json", Assets.cakeTin);
    public static Item emptyPlate = Utils.loadItem("380_empty_plate.json", Assets.emptyPlate);
    public static Item bucketOfMilk = Utils.loadItem("381_bucket_of_milk.json", Assets.bucketOfMilk);
    public static Item bucketOfWater = Utils.loadItem("382_bucket_of_water.json", Assets.bucketOfWater);
    public static Item hammer = Utils.loadItem("383_hammer.json", Assets.hammer);
    public static Item shovel = Utils.loadItem("384_shovel.json", Assets.shovel);
    public static Item lesserLumberjacksSoul = Utils.loadItem("385_lesser_lumberjack's_soul.json", Assets.lesserLumberjacksSoul);
    public static Item lesserMineworkersSoul = Utils.loadItem("386_lesser_mineworker's_soul.json", Assets.lesserMineworkersSoul);
    public static Item lesserGardenersSoul = Utils.loadItem("387_lesser_gardener's_soul.json", Assets.lesserGardenersSoul);
    public static Item lesserFishermansSoul = Utils.loadItem("388_lesser_fisherman's_soul.json", Assets.lesserFishermansSoul);


    static {
        initPotions();
    }

    /*
     * Class variables
     */

    private ItemType[] itemTypes;
    private ItemRarity itemRarity;
    private ItemRequirement[] requirements;
    private transient BufferedImage texture;
    private String name;
    private final int id;
    private EquipSlot equipSlot;
    private int strength;
    private int dexterity;
    private int intelligence;
    private int defence;
    private int vitality;
    private double attackSpeed;
    private double movementSpeed;
    private int x;
    private int y;
    private Rectangle bounds;
    private Rectangle position;
    private int count;
    private boolean pickedUp = false;
    public static boolean pickUpKeyPressed = false;
    private int price;
    private boolean stackable;
    private long respawnTime = 180L;
    private long timeDropped;
    private boolean equippable;
    private boolean hovering;
    private Use use;
    private int useCooldown;
    private boolean used;
    private int usedTimer;

    public Item(BufferedImage texture, String name, int id, ItemRarity itemRarity, int price, boolean stackable, ItemType... itemTypes) {
        this.texture = texture;
        this.name = name;
        this.id = id;
        this.itemTypes = itemTypes;
        this.itemRarity = itemRarity;
        this.price = price;
        this.stackable = stackable;
        this.equipSlot = EquipSlot.None;
        this.equippable = false;
        this.strength = 0;
        this.dexterity = 0;
        this.intelligence = 0;
        this.defence = 0;
        this.vitality = 0;
        this.attackSpeed = 0;
        this.movementSpeed = 0;

        // Prevent duplicate IDs when creating items
        try {
            if (items[id] != null && !name.equals(items[id].name)) {
                throw new DuplicateIDException(name, id);
            } else {
                // If the item already exists, don't create a new reference
                if (items[id] == null) {
                    items[id] = this;
                }
            }
        } catch (DuplicateIDException exc) {
            exc.printStackTrace();
            System.exit(1);
        }

        bounds = new Rectangle(0, 0, 32, 32);
        position = new Rectangle(0, 0, 32, 32);
    }

    public Item(BufferedImage texture, String name, int id, ItemRarity itemRarity,
                EquipSlot equipSlot, int strength, int dexterity, int intelligence, int defence, int vitality, double attackSpeed, double movementSpeed,
                int price, boolean stackable, ItemType[] itemTypes, ItemRequirement... requirements) {
        this(texture, name, id, itemRarity, price, stackable, itemTypes);
        this.equipSlot = equipSlot;
        this.equippable = true;
        this.requirements = requirements;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.defence = defence;
        this.vitality = vitality;
        this.attackSpeed = attackSpeed;
        this.movementSpeed = movementSpeed;
    }

    public void tick() {

    }

    public void render(Graphics2D g) {
        render(g, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()));
    }

    private void render(Graphics2D g, int x, int y) {
        g.drawImage(texture, x, y, ITEMWIDTH, ITEMHEIGHT, null);
    }

    /*
     * Adds a new item equippable item to the world
     * @params: x,y pause and amount
     */
    public Item createItem(int x, int y, int amount) {
        Item i;
        if (this.isEquippable()) {
            i = new Item(texture, name, id, itemRarity, equipSlot, strength, dexterity, intelligence, defence, vitality, attackSpeed, movementSpeed, price, stackable, itemTypes, requirements);
        } else {
            i = new Item(texture, name, id, itemRarity, price, stackable, itemTypes);
        }

        i.setPosition(x, y);

        // If the item is stackable, set the amount
        if (i.stackable)
            i.setAmount(amount);
            // If the item is unstackable, the count is always 1.
        else
            i.setAmount(1);
        return i;
    }

    private void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /*
     * Returns the pause of the item
     */
    public Rectangle itemPosition(double xOffset, double yOffset) {
        position.setBounds((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), 32, 32);
        return position;
    }

    /*
     * Item pickup function
     */
    public boolean pickUpItem(Item item) {
        int inventoryIndex = Handler.get().getInventory().findFreeSlot(item);
        if (inventoryIndex >= 0) {
            // If we have space
            if (id == item.getId()) {
                if (Handler.get().getInventory().getItemSlots().get(inventoryIndex).addItem(item, item.getCount())) {
                    Handler.get().sendMsg("Picked up " + item.getCount() + "x " + item.getName() + ".", Filter.LOOT);
                    pickedUp = true;
                    return true;
                }
            }
            System.out.println("Something went wrong picking up this item.");
            return false;
        }
        return false;
    }


    // Getters & Setters

    public int getEquipSlot() {
        return equipSlot.getSlotId();
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getDefence() {
        return defence;
    }

    public int getVitality() {
        return vitality;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public double getMovementSpeed() {
        return movementSpeed;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCount() {
        return count;
    }

    private void setAmount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public boolean isType(ItemType type) {
        if (itemTypes == null) {
            return false;
        }
        for (ItemType it : itemTypes) {
            if (it == type) {
                return true;
            }
        }
        return false;
    }

    public ItemType[] getItemTypes() {
        return itemTypes;
    }

    public void setItemType(ItemType[] itemTypes) {
        this.itemTypes = itemTypes;
    }

    public ItemRarity getItemRarity() {
        return itemRarity;
    }

    public void setItemRarity(ItemRarity itemRarity) {
        this.itemRarity = itemRarity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isStackable() {
        return stackable;
    }

    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }

    public ItemRequirement[] getRequirements() {
        return requirements;
    }

    public void setRequirements(ItemRequirement[] requirements) {
        this.requirements = requirements;
    }

    public long getRespawnTime() {
        return respawnTime;
    }

    public void setRespawnTimer(long respawnTime) {
        this.respawnTime = respawnTime;
    }

    public long getTimeDropped() {
        return timeDropped;
    }

    public void setTimeDropped(long timeDropped) {
        this.timeDropped = timeDropped;
    }

    public Rectangle getPosition() {
        return position;
    }

    public void setPosition(Rectangle position) {
        this.position = position;
    }

    private boolean isEquippable() {
        return equippable;
    }

    public void setEquippable(boolean equippable) {
        this.equippable = equippable;
    }

    public boolean isHovering() {
        return hovering;
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }

    public Use getUse() {
        return use;
    }

    public void setUse(Use use) {
        this.use = use;
    }

    public int getUseCooldown() {
        return useCooldown;
    }

    public void setUseCooldown(int useCooldown) {
        this.useCooldown = useCooldown;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getUsedTimer() {
        return usedTimer;
    }

    public void setUsedTimer(int usedTimer) {
        this.usedTimer = usedTimer;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ImageIO.write(this.texture, "png", buffer);

        out.writeInt(buffer.size()); // Prepend image with byte count
        buffer.writeTo(out);         // Write image
        buffer.close();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        int size = in.readInt(); // Read byte count
        byte[] buffer = new byte[size];
        in.readFully(buffer); // Make sure you read all bytes of the image

        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        this.texture = ImageIO.read(is);
        is.close();
    }

    /*
     * Items that have use cases
     */

    // Potions
    private static void initPotions() {
        Player player = Handler.get().getPlayer();

        weakAntidote = Utils.loadItem("24_weak_antidote.json", Assets.weakAntidote, 0, (Use & Serializable) (i) -> {
            Handler.get().getPlayer().addResistance(new Resistance(Condition.Type.POISON, 60 * 60 * 5, 0.2));
            Handler.get().removeItem(Item.weakAntidote, 1);
        });
        antidote = Utils.loadItem("25_antidote.json", Assets.antidote, 0, (Use & Serializable) (i) -> {
            Handler.get().getPlayer().addResistance(new Resistance(Condition.Type.POISON, 60 * 60 * 10, 0.4));
            Handler.get().removeItem(Item.antidote, 1);
        });
        strongAntidote = Utils.loadItem("26_strong_antidote.json", Assets.strongAntidote, 0, (Use & Serializable) (i) -> {
            Handler.get().getPlayer().addResistance(new Resistance(Condition.Type.POISON, 60 * 60 * 15, 0.6));
            Handler.get().removeItem(Item.strongAntidote, 1);
        });

        weakPotionOfMight = Utils.loadItem("27_weak_potion_of_might.json", Assets.weakPotionOfMight, 0, (Use & Serializable) (i) -> {
            player.addBuff(player, new AttributeBuff(AttributeBuff.Attribute.STR, player, 60.0, 6.0));
            Handler.get().removeItem(Item.weakPotionOfMight, 1);
        });
        potionOfMight = Utils.loadItem("28_potion_of_might.json", Assets.potionOfMight, 0, (Use & Serializable) (i) -> {
            player.addBuff(player, new AttributeBuff(AttributeBuff.Attribute.STR, player, 120.0, 30.0));
            Handler.get().removeItem(Item.potionOfMight, 1);
        });
        strongPotionOfMight = Utils.loadItem("29_strong_potion_of_might.json", Assets.strongPotionOfMight, 0, (Use & Serializable) (i) -> {
            player.addBuff(player, new AttributeBuff(AttributeBuff.Attribute.STR, player, 180.0, 60.0));
            Handler.get().removeItem(Item.strongPotionOfMight, 1);
        });

        weakPotionOfPrecision = Utils.loadItem("30_weak_potion_of_precision.json", Assets.weakPotionOfPrecision, 0, (Use & Serializable) (i) -> {
            player.addBuff(player, new AttributeBuff(AttributeBuff.Attribute.DEX, player, 60.0, 6.0));
            Handler.get().removeItem(Item.weakPotionOfPrecision, 1);
        });
        potionOfPrecision = Utils.loadItem("31_potion_of_precision.json", Assets.potionOfPrecision, 0, (Use & Serializable) (i) -> {
            player.addBuff(player, new AttributeBuff(AttributeBuff.Attribute.DEX, player, 120.0, 30.0));
            Handler.get().removeItem(Item.potionOfPrecision, 1);
        });
        strongPotionOfPrecision = Utils.loadItem("32_strong_potion_of_precision.json", Assets.strongPotionOfPrecision, 0, (Use & Serializable) (i) -> {
            player.addBuff(player, new AttributeBuff(AttributeBuff.Attribute.DEX, player, 180.0, 60.0));
            Handler.get().removeItem(Item.strongPotionOfPrecision, 1);
        });

        weakPotionOfWisdom = Utils.loadItem("33_weak_potion_of_wisdom.json", Assets.weakPotionOfWisdom, 0, (Use & Serializable) (i) -> {
            player.addBuff(player, new AttributeBuff(AttributeBuff.Attribute.INT, player, 60.0, 6.0));
            Handler.get().removeItem(Item.weakPotionOfWisdom, 1);
        });
        potionOfWisdom = Utils.loadItem("34_potion_of_wisdom.json", Assets.potionOfWisdom, 0, (Use & Serializable) (i) -> {
            player.addBuff(player, new AttributeBuff(AttributeBuff.Attribute.INT, player, 120.0, 30.0));
            Handler.get().removeItem(Item.potionOfWisdom, 1);
        });
        strongPotionOfWisdom = Utils.loadItem("35_strong_potion_of_wisdom.json", Assets.strongPotionOfWisdom, 0, (Use & Serializable) (i) -> {
            player.addBuff(player, new AttributeBuff(AttributeBuff.Attribute.INT, player, 180.0, 60.0));
            Handler.get().removeItem(Item.strongPotionOfWisdom, 1);
        });

        weakPotionOfFortitude = Utils.loadItem("36_weak_potion_of_fortitude.json", Assets.weakPotionOfFortitude, 0, (Use & Serializable) (i) -> {
            player.addBuff(player, new AttributeBuff(AttributeBuff.Attribute.DEF, player, 60.0, 10.0));
            Handler.get().removeItem(Item.weakPotionOfFortitude, 1);
        });
        potionOfFortitude = Utils.loadItem("37_potion_of_fortitude.json", Assets.potionOfFortitude, 0, (Use & Serializable) (i) -> {
            player.addBuff(player, new AttributeBuff(AttributeBuff.Attribute.DEF, player, 120.0, 30.0));
            Handler.get().removeItem(Item.potionOfFortitude, 1);
        });
        strongPotionOfFortitude = Utils.loadItem("38_strong_potion_of_fortitude.json", Assets.strongPotionOfFortitude, 0, (Use & Serializable) (i) -> {
            player.addBuff(player, new AttributeBuff(AttributeBuff.Attribute.DEF, player, 180.0, 80.0));
            Handler.get().removeItem(Item.strongPotionOfFortitude, 1);
        });

        weakPotionOfVigor = Utils.loadItem("39_weak_potion_of_vigor.json", Assets.weakPotionOfVigor, 0, (Use & Serializable) (i) -> {
            player.addBuff(player, new AttributeBuff(AttributeBuff.Attribute.VIT, player, 60.0, 5.0));
            Handler.get().removeItem(Item.weakPotionOfVigor, 1);
        });
        potionOfVigor = Utils.loadItem("40_potion_of_vigor.json", Assets.potionOfVigor, 0, (Use & Serializable) (i) -> {
            player.addBuff(player, new AttributeBuff(AttributeBuff.Attribute.VIT, player, 120.0, 25.0));
            Handler.get().removeItem(Item.potionOfVigor, 1);
        });
        strongPotionOfVigor = Utils.loadItem("41_strong_potion_of_vigor.json", Assets.strongPotionOfVigor, 0, (Use & Serializable) (i) -> {
            player.addBuff(player, new AttributeBuff(AttributeBuff.Attribute.VIT, player, 180.0, 100.0));
            Handler.get().removeItem(Item.strongPotionOfVigor, 1);
        });
    }

    private static void detonatorLogic() {
        if (!ShamrockRockslide.hasDetonated) {
            if (Handler.get().getPlayer().getZone() != Zone.ShamrockMines3) {
                Handler.get().sendMsg("You must use this detonator in Shamrock Mines B3");
                return;
            }
            if ((Integer) Handler.get().getQuest(QuestList.WeDelvedTooDeep).getCheckValueWithDefault("dynamitePlaced", 0) < 3) {
                Handler.get().sendMsg("You should place a dynamite stick north, east and west of the rock slide before using the detonator.");
            } else {
                Handler.get().sendMsg("You press the button and the dynamite explodes.");
                ShamrockRockslide.hasDetonated = true;
            }
        } else {
            Handler.get().sendMsg("There's no use for this anymore.");
        }
    }
}
