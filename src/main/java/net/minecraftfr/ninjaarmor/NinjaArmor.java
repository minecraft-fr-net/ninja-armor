package net.minecraftfr.ninjaarmor;

import net.fabricmc.api.ModInitializer;
import net.minecraftfr.ninjaarmor.event.ModEvents;
import net.minecraftfr.ninjaarmor.item.ModArmorMaterials;
import net.minecraftfr.ninjaarmor.item.ModItems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NinjaArmor implements ModInitializer {
  public static final Logger LOGGER = LoggerFactory.getLogger("ninja-armor");
	public static final String MOD_ID = "ninja-armor";

	@Override
	public void onInitialize() {
		LOGGER.info("Initialize ninja-armor");

		ModArmorMaterials.initialize();
		ModItems.initialize();
		ModEvents.initialize();
	}
}
