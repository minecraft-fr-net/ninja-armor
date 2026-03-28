package net.minecraftfr.ninjaarmor.item;

import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraftfr.ninjaarmor.NinjaArmor;

public class ModArmorMaterials {
	public static final TagKey<Item> NINJA_REPAIR = TagKey.of(RegistryKeys.ITEM, Identifier.of(NinjaArmor.MOD_ID, "ninja_armor_repair"));

	/** {@code assets/ninja-armor/equipment/ninja.json} — joueur uniquement : {@code textures/entity/equipment/humanoid/ninja.png}, {@code humanoid_leggings/ninja.png} */
	public static final RegistryKey<EquipmentAsset> NINJA_ASSET = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(NinjaArmor.MOD_ID, "ninja"));

	public static final ArmorMaterial NINJA = new ArmorMaterial(
		5,
		Map.of(
			EquipmentType.HELMET, 1,
			EquipmentType.CHESTPLATE, 3,
			EquipmentType.LEGGINGS, 2,
			EquipmentType.BOOTS, 1
		),
		15,
		SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
		0.0F,
		0.0F,
		NINJA_REPAIR,
		NINJA_ASSET
	);

	public static void initialize() {
	}
}
