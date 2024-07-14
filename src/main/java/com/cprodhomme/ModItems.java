package com.cprodhomme;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
  public static final Item NINJA_HELMET = register(new ArmorItem(ModArmorMaterials.NINJA, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(5))), "ninja_helmet");
  public static final Item NINJA_CHESTPLATE = register(new ArmorItem(ModArmorMaterials.NINJA, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(5))), "ninja_chestplate");
  public static final Item NINJA_LEGGINGS = register(new ArmorItem(ModArmorMaterials.NINJA, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(5))), "ninja_leggings");
  public static final Item NINJA_BOOTS = register(new ArmorItem(ModArmorMaterials.NINJA, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(5))), "ninja_boots");

  public static void initialize() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
		  .register((itemGroup) -> itemGroup.add(ModItems.NINJA_HELMET));
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
      .register((itemGroup) -> itemGroup.add(ModItems.NINJA_CHESTPLATE));
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
		  .register((itemGroup) -> itemGroup.add(ModItems.NINJA_LEGGINGS));
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
      .register((itemGroup) -> itemGroup.add(ModItems.NINJA_BOOTS));
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
