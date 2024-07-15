package com.cprodhomme.ninjaarmormod;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
  public static final Item NINJA_HELMET = register(new ArmorItem(ModArmorMaterials.NINJA, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(5))), "ninja_helmet");
  public static final Item NINJA_CHESTPLATE = register(new ArmorItem(ModArmorMaterials.NINJA, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(5))), "ninja_chestplate");
  public static final Item NINJA_LEGGINGS = register(new ArmorItem(ModArmorMaterials.NINJA, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(5))), "ninja_leggings");
  public static final Item NINJA_BOOTS = register(new ArmorItem(ModArmorMaterials.NINJA, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(5))), "ninja_boots");

  public static final Item WOODEN_KATANA = register(new SwordItem(ToolMaterials.WOOD, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.WOOD, 3, -2.4F))), "wooden_katana");
  public static final Item STONE_KATANA = register(new SwordItem(ToolMaterials.STONE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.STONE, 3, -2.4F))), "stone_katana");
  public static final Item GOLDEN_KATANA = register(new SwordItem(ToolMaterials.GOLD, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.GOLD, 3, -2.4F))), "golden_katana");
  public static final Item IRON_KATANA = register(new SwordItem(ToolMaterials.IRON, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.IRON, 3, -2.4F))), "iron_katana");
  public static final Item DIAMOND_KATANA = register(new SwordItem(ToolMaterials.DIAMOND, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.DIAMOND, 3, -2.4F))), "diamond_katana");

  public static void initialize() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
		  .register((itemGroup) -> itemGroup.add(ModItems.NINJA_HELMET));
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
      .register((itemGroup) -> itemGroup.add(ModItems.NINJA_CHESTPLATE));
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
		  .register((itemGroup) -> itemGroup.add(ModItems.NINJA_LEGGINGS));
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
      .register((itemGroup) -> itemGroup.add(ModItems.NINJA_BOOTS));

    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
      .register((itemGroup) -> itemGroup.add(ModItems.WOODEN_KATANA));
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
      .register((itemGroup) -> itemGroup.add(ModItems.STONE_KATANA));
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
      .register((itemGroup) -> itemGroup.add(ModItems.GOLDEN_KATANA));
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
      .register((itemGroup) -> itemGroup.add(ModItems.IRON_KATANA));
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
      .register((itemGroup) -> itemGroup.add(ModItems.DIAMOND_KATANA));
  }

	public static Item register(Item item, String id) {
		// Create the identifier for the item.
		Identifier itemID = Identifier.of(NinjaArmorMod.MOD_ID, id);

		// Register the item.
		Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

		// Return the registered item!
		return registeredItem;
	}
}
