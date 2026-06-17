package net.minecraftfr.ninjaarmor.item;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraftfr.ninjaarmor.NinjaArmor;

public class ModItems {
	public static final Item NINJA_HELMET = register(
		new Item(baseSettings("ninja_helmet").humanoidArmor(ModArmorMaterials.NINJA, ArmorType.HELMET)),
		"ninja_helmet"
	);
	public static final Item NINJA_CHESTPLATE = register(
		new Item(baseSettings("ninja_chestplate").humanoidArmor(ModArmorMaterials.NINJA, ArmorType.CHESTPLATE)),
		"ninja_chestplate"
	);
	public static final Item NINJA_LEGGINGS = register(
		new Item(baseSettings("ninja_leggings").humanoidArmor(ModArmorMaterials.NINJA, ArmorType.LEGGINGS)),
		"ninja_leggings"
	);
	public static final Item NINJA_BOOTS = register(
		new Item(baseSettings("ninja_boots").humanoidArmor(ModArmorMaterials.NINJA, ArmorType.BOOTS)),
		"ninja_boots"
	);

	public static final Item WOODEN_KATANA = register(new Item(baseSettings("wooden_katana").sword(ToolMaterial.WOOD, 3.0F, -2.4F)), "wooden_katana");
	public static final Item STONE_KATANA = register(new Item(baseSettings("stone_katana").sword(ToolMaterial.STONE, 3.0F, -2.4F)), "stone_katana");
	public static final Item GOLDEN_KATANA = register(new Item(baseSettings("golden_katana").sword(ToolMaterial.GOLD, 3.0F, -2.4F)), "golden_katana");
	public static final Item IRON_KATANA = register(new Item(baseSettings("iron_katana").sword(ToolMaterial.IRON, 3.0F, -2.4F)), "iron_katana");
	public static final Item DIAMOND_KATANA = register(new Item(baseSettings("diamond_katana").sword(ToolMaterial.DIAMOND, 3.0F, -2.4F)), "diamond_katana");
	public static final Item NETHERITE_KATANA = register(new Item(baseSettings("netherite_katana").sword(ToolMaterial.NETHERITE, 3.0F, -2.4F)), "netherite_katana");

	public static void initialize() {
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(entries -> {
			entries.accept(ModItems.NINJA_HELMET);
			entries.accept(ModItems.NINJA_CHESTPLATE);
			entries.accept(ModItems.NINJA_LEGGINGS);
			entries.accept(ModItems.NINJA_BOOTS);

			entries.accept(ModItems.WOODEN_KATANA);
			entries.accept(ModItems.STONE_KATANA);
			entries.accept(ModItems.GOLDEN_KATANA);
			entries.accept(ModItems.IRON_KATANA);
			entries.accept(ModItems.DIAMOND_KATANA);
			entries.accept(ModItems.NETHERITE_KATANA);
		});
	}

	private static Item.Properties baseSettings(String id) {
		Identifier itemId = Identifier.fromNamespaceAndPath(NinjaArmor.MOD_ID, id);
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, itemId);
		return new Item.Properties().setId(key);
	}

	public static Item register(Item item, String id) {
		Identifier itemID = Identifier.fromNamespaceAndPath(NinjaArmor.MOD_ID, id);
		return Registry.register(BuiltInRegistries.ITEM, itemID, item);
	}
}
