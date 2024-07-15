package com.cprodhomme.ninjaarmormod;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NinjaArmorMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
  public static final Logger LOGGER = LoggerFactory.getLogger("ninja-armor-mod");
	public static final String MOD_ID = "ninja-armor-mod";

	@Override
	public void onInitialize() {
		LOGGER.info("Initialize NinjaArmorMod");
		ModArmorMaterials.initialize();
		ModItems.initialize();
	}
}
