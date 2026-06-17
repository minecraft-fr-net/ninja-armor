package net.minecraftfr.ninjaarmor.item;

import java.util.Map;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraftfr.ninjaarmor.NinjaArmor;
import net.minecraft.core.registries.Registries;

public class ModArmorMaterials {
	public static final TagKey<Item> NINJA_REPAIR = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(NinjaArmor.MOD_ID, "ninja_armor_repair"));

	/** {@code assets/ninja-armor/equipment/ninja.json} — joueur uniquement : {@code textures/entity/equipment/humanoid/ninja.png}, {@code humanoid_leggings/ninja.png} */
	public static final ResourceKey<EquipmentAsset> NINJA_ASSET = ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(NinjaArmor.MOD_ID, "ninja"));

	public static final ArmorMaterial NINJA = new ArmorMaterial(
		5,
		Map.of(
			ArmorType.HELMET, 1,
			ArmorType.CHESTPLATE, 3,
			ArmorType.LEGGINGS, 2,
			ArmorType.BOOTS, 1
		),
		15,
		SoundEvents.ARMOR_EQUIP_LEATHER,
		0.0F,
		0.0F,
		NINJA_REPAIR,
		NINJA_ASSET
	);

	public static void initialize() {
	}
}
