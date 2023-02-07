package com.vt.fade;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import com.vt.fade.blocks.FadeBlock;
import com.vt.fade.items.FadeItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;




public class Fade implements ModInitializer {

    public static final String MODID = "fade";

    public static final FadeItem FADE_ITEM = new FadeItem();
    public static final FadeBlock FADE_BLOCK = new FadeBlock();

    public static final Item FADE_BLOCK_ITEM = new BlockItem(FADE_BLOCK, new FabricItemSettings());

    //public static final Item FADE_BLOCK_ITEM = new BlockItem(FADE_BLOCK, new Item.Settings().group(ItemGroup.REDSTONE));

	
	@Override
	public void onInitialize() {
		
		Registry.register(Registries.ITEM, new Identifier(MODID, "fade"), FADE_ITEM);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(FADE_ITEM));
		Registry.register(Registries.BLOCK, new Identifier(MODID, "fade_block"), FADE_BLOCK);
		Registry.register(Registries.ITEM, new Identifier(MODID, "fade_block"), FADE_BLOCK_ITEM);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(FADE_BLOCK_ITEM));



    }

}
