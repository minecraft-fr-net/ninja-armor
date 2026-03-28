package net.minecraftfr.ninjaarmor.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraftfr.ninjaarmor.NinjaArmor;

public class ModItems {
	public static final Item NINJA_HELMET = register(
		new Item(baseSettings("ninja_helmet").armor(ModArmorMaterials.NINJA, EquipmentType.HELMET).maxDamage(EquipmentType.HELMET.getMaxDamage(5))),
		"ninja_helmet"
	);
	public static final Item NINJA_CHESTPLATE = register(
		new Item(baseSettings("ninja_chestplate").armor(ModArmorMaterials.NINJA, EquipmentType.CHESTPLATE).maxDamage(EquipmentType.CHESTPLATE.getMaxDamage(5))),
		"ninja_chestplate"
	);
	public static final Item NINJA_LEGGINGS = register(
		new Item(baseSettings("ninja_leggings").armor(ModArmorMaterials.NINJA, EquipmentType.LEGGINGS).maxDamage(EquipmentType.LEGGINGS.getMaxDamage(5))),
		"ninja_leggings"
	);
	public static final Item NINJA_BOOTS = register(
		new Item(baseSettings("ninja_boots").armor(ModArmorMaterials.NINJA, EquipmentType.BOOTS).maxDamage(EquipmentType.BOOTS.getMaxDamage(5))),
		"ninja_boots"
	);

	public static final Item WOODEN_KATANA = register(new Item(baseSettings("wooden_katana").sword(ToolMaterial.WOOD, 3.0F, -2.4F)), "wooden_katana");
	public static final Item STONE_KATANA = register(new Item(baseSettings("stone_katana").sword(ToolMaterial.STONE, 3.0F, -2.4F)), "stone_katana");
	public static final Item GOLDEN_KATANA = register(new Item(baseSettings("golden_katana").sword(ToolMaterial.GOLD, 3.0F, -2.4F)), "golden_katana");
	public static final Item IRON_KATANA = register(new Item(baseSettings("iron_katana").sword(ToolMaterial.IRON, 3.0F, -2.4F)), "iron_katana");
	public static final Item DIAMOND_KATANA = register(new Item(baseSettings("diamond_katana").sword(ToolMaterial.DIAMOND, 3.0F, -2.4F)), "diamond_katana");
	public static final Item NETHERITE_KATANA = register(new Item(baseSettings("netherite_katana").sword(ToolMaterial.NETHERITE, 3.0F, -2.4F)), "netherite_katana");

	public static void initialize() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(itemGroup -> {
			itemGroup.add(ModItems.NINJA_HELMET);
			itemGroup.add(ModItems.NINJA_CHESTPLATE);
			itemGroup.add(ModItems.NINJA_LEGGINGS);
			itemGroup.add(ModItems.NINJA_BOOTS);

			itemGroup.add(ModItems.WOODEN_KATANA);
			itemGroup.add(ModItems.STONE_KATANA);
			itemGroup.add(ModItems.GOLDEN_KATANA);
			itemGroup.add(ModItems.IRON_KATANA);
			itemGroup.add(ModItems.DIAMOND_KATANA);
			itemGroup.add(ModItems.NETHERITE_KATANA);
		});
	}

	private static Item.Settings baseSettings(String id) {
		Identifier itemId = Identifier.of(NinjaArmor.MOD_ID, id);
		RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, itemId);
		return new Item.Settings().registryKey(key);
	}

	public static Item register(Item item, String id) {
		Identifier itemID = Identifier.of(NinjaArmor.MOD_ID, id);
		return Registry.register(Registries.ITEM, itemID, item);
	}
}
