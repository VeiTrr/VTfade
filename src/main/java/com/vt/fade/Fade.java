package com.vt.fade;

import com.vt.fade.blocks.FadeBlock;
import com.vt.fade.items.FadeItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class Fade implements ModInitializer {

    public static final String MODID = "fade";

    public static final FadeItem FADE_ITEM = new FadeItem();

    public static final FadeBlock FADE_BLOCK = new FadeBlock();

    public static final Item FADE_BLOCK_ITEM = new BlockItem(FADE_BLOCK, new Item.Settings().group(ItemGroup.REDSTONE));

	
	@Override
	public void onInitialize() {
		
		Registry.register(Registry.ITEM, new Identifier(MODID, "fade"), FADE_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(MODID, "fade_block"), FADE_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(MODID, "fade_block"), FADE_BLOCK_ITEM);


    }

}
