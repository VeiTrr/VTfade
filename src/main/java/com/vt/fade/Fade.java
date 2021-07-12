package com.vt.fade;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vt.fade.items.FadeItem;


import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Fade implements ModInitializer {
	
	public static final String MODID = "fade";

	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	public static final FadeItem FADE_ITEM = new FadeItem();

	
	@Override
	public void onInitialize() {
		
		Registry.register(Registry.ITEM, new Identifier(MODID, "fade"), FADE_ITEM);


	}
	
}
